package com.example.myproject.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service Spring responsable de la génération automatique de schémas JSON 
 * à partir de classes ou entités Java.
 * 
 * Il génère deux schémas distincts par entité :
 * 1. Un schéma pour la CRÉATION (excluant l'ID généré automatiquement).
 * 2. Un schéma pour la MODIFICATION/LECTURE (incluant l'ID).
 */
@Service
public class JsonSchemaService {

        // Générateur configuré pour omettre la propriété @Id (Utile pour les requêtes POST)
        private final SchemaGenerator creationGenerator;

        // Générateur configuré pour conserver la propriété @Id (Utile pour les requêtes PUT/PATCH)
        private final SchemaGenerator mutationGenerator;

        // Outil Jackson servant à formater l'arbre de nœuds JSON en chaîne de caractères propre
        private final ObjectMapper mapper;

        // Chemin d'accès du dossier physique où seront sauvegardés les schémas générés
        private final Path outputDirectory;

        /**
         * Constructeur du service avec injection de la configuration.
         * 
         * @param outputDirectory Le dossier cible configuré dans application.properties.
         *                        Valeur par défaut si non spécifiée : target/generated-schemas
         */
        public JsonSchemaService(
                        @Value("${json-schema.output-directory:target/generated-schemas}") 
                        String outputDirectory) {

                // Initialisation des utilitaires de base
                this.mapper = new ObjectMapper();
                this.outputDirectory = Paths.get(outputDirectory);

                /*
                 * ---------------------------------------------------------------------
                 * 1. CONFIGURATION DE BASE PARTAGÉE
                 * ---------------------------------------------------------------------
                 * On extrait les règles communes aux deux générateurs (Validation, Required)
                 * afin d'éviter la duplication de code de configuration.
                 */
                SchemaGeneratorConfigBuilder baseBuilder = createBaseConfigBuilder();

                /*
                 * ---------------------------------------------------------------------
                 * 2. INITIALISATION DU GÉNÉRATEUR DE MODIFICATION (Avec ID)
                 * ---------------------------------------------------------------------
                 * Ce générateur prend la configuration standard brute. L'ID JPA n'étant
                 * pas explicitement exclu, il sera inclus naturellement dans le schéma JSON.
                 */
                SchemaGeneratorConfig mutationConfig = baseBuilder.build();
                this.mutationGenerator = new SchemaGenerator(mutationConfig);

                /*
                 * ---------------------------------------------------------------------
                 * 3. INITIALISATION DU GÉNÉRATEUR DE CRÉATION (Sans ID)
                 * ---------------------------------------------------------------------
                 * On instancie un nouveau constructeur de configuration de base pour l'isoler,
                 * puis on lui greffe la règle d'exclusion de l'identifiant technique @Id.
                 */
                SchemaGeneratorConfigBuilder creationBuilder = createBaseConfigBuilder();
                creationBuilder.forFields()
                                .withIgnoreCheck(field -> field.getAnnotationConsideringFieldAndGetter(
                                                Id.class) != null);
                
                SchemaGeneratorConfig creationConfig = creationBuilder.build();
                this.creationGenerator = new SchemaGenerator(creationConfig);
        }

        /**
         * Centralise la configuration commune (Draft du schéma, Validation et colonnes requises).
         * 
         * @return Un builder pré-configuré prêt à être spécialisé ou assemblé.
         */
        private SchemaGeneratorConfigBuilder createBaseConfigBuilder() {
                // Définit la version de la norme JSON Schema à appliquer (ici Draft 2019-09)
                SchemaGeneratorConfigBuilder builder = new SchemaGeneratorConfigBuilder(
                                SchemaVersion.DRAFT_2019_09,
                                OptionPreset.PLAIN_JSON);

                /*
                 * Activation du module Jakarta Validation.
                 * Ce module traduit automatiquement les annotations Java en contraintes JSON Schema :
                 * - @Min(0)         ->  "minimum": 0
                 * - @Size(max=50)   ->  "maxLength": 50
                 * - @NotNull        ->  Ajoute le champ dans le tableau "required"
                 */
                builder.with(new JakartaValidationModule());

                /*
                 * Cartographie des règles de base de données JPA vers le schéma JSON.
                 * Si un champ de l'entité possède l'annotation @Column(nullable = false),
                 * il est considéré comme obligatoire et est inscrit dans le tableau "required" du JSON.
                 */
                builder.forFields()
                                .withRequiredCheck(field -> {
                                        Column column = field.getAnnotationConsideringFieldAndGetter(Column.class);
                                        // Le champ est requis uniquement si l'annotation existe ET que nullable est faux
                                        return column != null && !column.nullable();
                                });
                return builder;
        }

        /**
         * Point d'entrée public unique permettant de générer la paire de schémas 
         * (création et modification) associés à une même entité Java.
         *
         * @param clazz La classe Java (ex: VideoGame.class, Book.class) à analyser.
         */
        public void generateAndSaveAllSchemas(Class<?> clazz) {
                // Sécurité pour empêcher les plantages dus à un paramètre invalide
                if (clazz == null) {
                        throw new IllegalArgumentException("La classe ne peut pas être null");
                }

                // Génère et écrit le fichier pour l'opération de création (Suffixe : -create-schema.json)
                saveSchemaFile(clazz, creationGenerator, "-create-schema.json");

                // Génère et écrit le fichier pour l'opération de modification (Suffixe : -update-schema.json)
                saveSchemaFile(clazz, mutationGenerator, "-update-schema.json");
        }

        /**
         * Sous-méthode utilitaire gérant la cinématique technique d'extraction, 
         * de conversion textuelle et d'écriture physique sur le disque.
         * 
         * @param clazz           La classe Java analysée.
         * @param targetGenerator L'instance du générateur à solliciter (création ou modification).
         * @param fileSuffix      Le suffixe de nommage du fichier cible.
         */
        private void saveSchemaFile(Class<?> clazz, SchemaGenerator targetGenerator, String fileSuffix) {
                try {
                        // 1. Extraction de la structure de la classe sous forme d'arbre de nœuds Jackson JsonNode
                        JsonNode jsonSchema = targetGenerator.generateSchema(clazz);
                        
                        // 2. Sérialisation de l'arbre en texte brut formaté de façon lisible (Pretty Print)
                        String schemaString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);

                        // 3. Vérification de l'arborescence sur le disque (crée les dossiers parents s'ils manquent)
                        Files.createDirectories(outputDirectory);
                        
                        // 4. Résolution du chemin complet final (ex: src/main/java/.../VideoGame-create-schema.json)
                        Path targetFile = outputDirectory.resolve(clazz.getSimpleName() + fileSuffix);
                        
                        // 5. Écriture physique des caractères sur le disque
                        Files.writeString(targetFile, schemaString);

                        // 6. Notification informative dans la console de développement
                        System.out.println("✅ Schéma JSON enregistré : " + targetFile.toAbsolutePath());
                        
                } catch (IOException e) {
                        // Traitement spécifique des erreurs d'E/S (disque plein, droits insuffisants...)
                        System.err.println("❌ Erreur lors de l'écriture du schéma pour " + clazz.getName());
                        throw new RuntimeException("Impossible de sauvegarder le fichier JSON Schema", e);
                }
        }
}
