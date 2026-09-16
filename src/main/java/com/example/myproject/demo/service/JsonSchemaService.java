// Déclaration du package de destination pour ranger notre classe utilitaire
package com.example.myproject.demo.service;

// Importation de l'objet de nœuds Jackson permettant de manipuler le JSON sous forme d'arbre en mémoire
import com.fasterxml.jackson.databind.JsonNode;
// Importation du moteur principal de Jackson servant à lire, écrire et formater les objets JSON
import com.fasterxml.jackson.databind.ObjectMapper;
// Importation des options prédéfinies de structure pour le générateur de schéma (ici format standard épuré)
import com.github.victools.jsonschema.generator.OptionPreset;
// Importation du moteur principal de génération qui extrait les schémas JSON à partir de classes Java
import com.github.victools.jsonschema.generator.SchemaGenerator;
// Importation de l'outil permettant de construire pas à pas les configurations du générateur de schéma
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
// Importation des versions standards supportées de la norme JSON Schema (Draft 7, Draft 2019-09...)
import com.github.victools.jsonschema.generator.SchemaVersion;
// Importation du greffon officiel permettant de traduire automatiquement les annotations de validation en contraintes JSON
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;

// Importation de l'annotation permettant d'injecter une valeur configurée dans application.properties
import org.springframework.beans.factory.annotation.Value;
// Importation de l'annotation Spring marquant cette classe comme un composant de logique métier (Service)
import org.springframework.stereotype.Service;

// Importation de l'exception levée en cas de panne d'écriture ou de lecture sur le disque dur
import java.io.IOException;
// Importation de l'utilitaire d'écriture rapide de fichiers de la version moderne de Java
import java.nio.file.Files;
// Importation de l'interface représentant un chemin abstrait de fichier ou de dossier dans le système
import java.nio.file.Path;
// Importation de la classe utilitaire servant à convertir des chaînes de texte en objets Path navigables
import java.nio.file.Paths;

/**
 * Service Spring responsable de la génération automatique de schémas JSON
 * à partir de vos classes DTO Request (ex: BookRequest.class).
 */
@Service // Indique à Spring d'instancier automatiquement cette classe au démarrage de
         // l'application
public class JsonSchemaService {

        // Instance du générateur configurée sans le champ ID, réservée aux requêtes
        // HTTP POST (Création)
        private final SchemaGenerator creationGenerator;

        // Instance du générateur configurée avec le champ ID obligatoire, réservée aux
        // requêtes HTTP PUT (Modification)
        private final SchemaGenerator mutationGenerator;

        // Instance Jackson permettant de formater proprement le texte final produit
        private final ObjectMapper mapper;

        // Chemin d'accès vers le dossier physique de stockage sur votre disque dur
        private final Path outputDirectory;

        /**
         * Constructeur unique prenant en charge l'injection de dépendances de Spring.
         * 
         * @param outputDirectory Injecte la valeur de la clé
         *                        json-schema.output-directory définie dans
         *                        application.properties.
         *                        Si absente, utilise par défaut le dossier
         *                        "target/generated-schemas".
         */
        public JsonSchemaService(
                        @Value("${json-schema.output-directory:target/generated-schemas}") String outputDirectory) {

                // Initialisation du convertisseur d'objets Jackson
                this.mapper = new ObjectMapper();
                // Conversion de la chaîne de caractères du dossier en un objet de chemin
                // d'accès (Path)
                this.outputDirectory = Paths.get(outputDirectory);

                /*
                 * ---------------------------------------------------------------------
                 * 1. CONFIGURATION DU GÉNÉRATEUR DE MODIFICATION / LECTURE (PUT)
                 * ---------------------------------------------------------------------
                 */
                // Appel de notre méthode privée pour initialiser le builder de configuration
                // avec les règles partagées
                SchemaGeneratorConfigBuilder mutationBuilder = createBaseConfigBuilder();

                // RÈGLE MODIFICATION : On cible les attributs et on ajoute une condition
                // d'obligation si le champ s'appelle "id"
                mutationBuilder.forFields().withRequiredCheck(field -> field.getName().equals("id"));

                // Assemblage de la configuration finale et instanciation du générateur dédié
                // aux mises à jour
                this.mutationGenerator = new SchemaGenerator(mutationBuilder.build());

                /*
                 * ---------------------------------------------------------------------
                 * 2. CONFIGURATION DU GÉNÉRATEUR DE CRÉATION (POST)
                 * ---------------------------------------------------------------------
                 */
                // Initialisation d'un nouveau builder indépendant contenant le socle de règles
                // communes
                SchemaGeneratorConfigBuilder creationBuilder = createBaseConfigBuilder();

                // RÈGLE CRÉATION : On demande explicitement au générateur de sauter et masquer
                // le champ nommé "id"
                creationBuilder.forFields().withIgnoreCheck(field -> field.getName().equals("id"));

                // Assemblage de la configuration finale et instanciation du générateur dédié
                // aux créations
                this.creationGenerator = new SchemaGenerator(creationBuilder.build());
        }

        /**
         * Centralise la configuration commune (Draft du schéma et Validation Jakarta).
         * 
         * @return Un configurateur de base prêt à être cloné ou spécialisé.
         */
        private SchemaGeneratorConfigBuilder createBaseConfigBuilder() {
                // Instanciation du builder configuré avec la norme moderne Draft 2019-09 et un
                // modèle d'affichage brut
                SchemaGeneratorConfigBuilder builder = new SchemaGeneratorConfigBuilder(
                                SchemaVersion.DRAFT_2019_09,
                                OptionPreset.PLAIN_JSON);

                /*
                 * Activation du module Jakarta Validation sans option bloquante.
                 * Ce module traduit nativement les contraintes (@Min(0) -> minimum: 0, @Size ->
                 * maxLength, etc.)
                 */
                JakartaValidationModule module = new JakartaValidationModule(
                                JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS);
                builder.with(module);

                /*
                 * 💡 RÈGLE MANUELLE UNIVERSELLE :
                 * On configure un vérificateur d'obligation sur l'ensemble des champs analysés.
                 * Si l'attribut porte l'annotation @NotNull ou l'annotation @NotBlank (au
                 * niveau du champ ou de son getter),
                 * la fonction renvoie "true" et l'attribut est inscrit d'office dans la liste
                 * "required" du schéma JSON.
                 */
                builder.forFields()
                                .withRequiredCheck(field -> field.getAnnotationConsideringFieldAndGetter(
                                                jakarta.validation.constraints.NotNull.class) != null ||
                                                field.getAnnotationConsideringFieldAndGetter(
                                                                jakarta.validation.constraints.NotBlank.class) != null);

                // Renvoie le configurateur ainsi préparé
                return builder;
        }

        /**
         * Point d'entrée public pour générer les paires de fichiers à partir de vos DTO
         * Request.
         *
         * @param clazz La classe DTO à analyser (ex: BookRequest.class)
         */
        public void generateAndSaveAllSchemas(Class<?> clazz) {
                // Validation de sécurité : Empêche un plantage si le développeur fournit un
                // paramètre inexistant ou nul
                if (clazz == null) {
                        throw new IllegalArgumentException("La classe ne peut pas être null");
                }

                // Déclenche la génération et la sauvegarde physique du schéma pour les
                // créations (POST)
                saveSchemaFile(clazz, creationGenerator, "-create-schema.json");
                // Déclenche la génération et la sauvegarde physique du schéma pour les
                // modifications (PUT)
                saveSchemaFile(clazz, mutationGenerator, "-update-schema.json");
        }

        /**
         * Logique technique d'écriture physique du fichier texte JSON sur l'espace de
         * stockage.
         */
        private void saveSchemaFile(Class<?> clazz, SchemaGenerator targetGenerator, String fileSuffix) {
                try {
                        // 1. Demande au générateur fourni d'extraire la structure de la classe sous
                        // forme d'arbre de nœuds Jackson
                        JsonNode jsonSchema = targetGenerator.generateSchema(clazz);

                        // 2. Traduction de l'arbre de nœuds en texte brut configuré avec des retours à
                        // la ligne lisibles (Pretty Print)
                        String schemaString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);

                        // 3. SÉCURITÉ : Si le dossier configuré dans application.properties n'existe
                        // pas, on le crée
                        if (!Files.exists(outputDirectory)) {
                                Files.createDirectories(outputDirectory);
                        }

                        // 4. Résolution de l'adresse finale du fichier (ex: le dossier + "BookRequest"
                        // + "-create-schema.json")
                        Path targetFile = outputDirectory.resolve(clazz.getSimpleName() + fileSuffix);
                        Files.writeString(targetFile, schemaString);

                        // 5. Impression d'un compte rendu de succès explicite au sein des logs de votre
                        // terminal Spring Boot
                        System.out.println("✅ Schéma JSON DTO enregistré : " + targetFile.toAbsolutePath());
                } catch (IOException e) {// Interception d'un problème matériel (disque plein, dossier verrouillé par le
                                         // système de fichiers...)
                        System.err.println("❌ Erreur lors de l'écriture du schéma pour " + clazz.getName());
                        // 6. Encapsulation et relance de l'erreur sous forme de RuntimeException
                        // pour notifier l'application
                        throw new RuntimeException("Impossible de sauvegarder le JSON Schema", e);
                }
        }
}