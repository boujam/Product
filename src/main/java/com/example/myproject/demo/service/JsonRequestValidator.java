package com.example.myproject.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Composant Spring chargé de confronter une chaîne JSON brute issue d'une requête HTTP
 * par rapport aux fichiers de spécification JSON Schema enregistrés sur le disque.
 */
@Component
public class JsonRequestValidator {

    // Outil Jackson servant à parser la chaîne de caractères brute de la requête HTTP en arbre de nœuds
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Usine de schémas initialisée pour interpréter la norme moderne Draft 2019-09 générée par votre service
    private final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

    /**
     * Valide le corps JSON d'une requête par rapport au schéma d'une entité cible.
     * 
     * @param rawJson      Le contenu texte brut (le payload) reçu dans la requête HTTP.
     * @param targetClass  La classe d'entité Java visée (ex: Book.class, VideoGame.class).
     * @param isCreation   Indique si l'opération est une création (POST) ou une modification (PUT/PATCH).
     *                     Permet de charger la déclinaison de schéma appropriée (sans ou avec ID).
     * @return Une liste de chaînes contenant les messages d'erreurs (liste vide si le JSON est 100% conforme).
     */
    public List<String> validate(String rawJson, Class<?> targetClass, boolean isCreation) {
        try {
            // 1. Détermination du suffixe du fichier selon l'action du contrôleur
            String fileSuffix = isCreation ? "-create-schema.json" : "-update-schema.json";
            
            // 2. Résolution du chemin d'accès vers le package où votre JsonSchemaService écrit les schémas
            Path schemaPath = Paths.get("src", "main", "java", "com", "example", "myproject", "demo", "schema", 
                                        targetClass.getSimpleName() + fileSuffix);
            
            // Sécurité : Si le développeur a oublié de générer les schémas au préalable
            if (!Files.exists(schemaPath)) {
                throw new IllegalArgumentException("Le fichier de spécification JSON Schema requis est introuvable à l'emplacement : " 
                        + schemaPath.toAbsolutePath());
            }

            // 3. Chargement et compilation du schéma JSON par la bibliothèque NetworkNT
            String schemaContent = Files.readString(schemaPath);
            JsonSchema schema = schemaFactory.getSchema(schemaContent);

            // 4. Conversion du texte de la requête HTTP vers un arbre de données analysable par le validateur
            JsonNode jsonNode = objectMapper.readTree(rawJson);

            // 5. Exécution de la validation stricte de conformité (mots-clés type, minimum, required, etc.)
            Set<ValidationMessage> errors = schema.validate(jsonNode);

            // 6. Extraction et transformation des objets d'erreurs internes en messages de texte simples et lisibles
            return errors.stream()
                    .map(ValidationMessage::getMessage)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            // Interception des pannes structurelles (comme une accolade manquante rendant le JSON globalement illisible)
            return List.of("Structure syntaxique de la requête invalide : " + e.getMessage());
        }
    }
}
