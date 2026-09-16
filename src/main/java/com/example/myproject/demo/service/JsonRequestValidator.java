package com.example.myproject.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JsonRequestValidator {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
    private final Path schemaDirectory;

    // 💡 Récupère dynamiquement le même dossier de schémas que le JsonSchemaService
    public JsonRequestValidator(
            @Value("${json-schema.output-directory:target/generated-schemas}") String schemaDirectory) {
        this.schemaDirectory = Paths.get(schemaDirectory);
    }

    /**
     * Valide le corps JSON d'une requête par rapport au schéma d'un DTO cible.
     * 
     * @param rawJson      Le contenu texte brut reçu dans la requête HTTP.
     * @param targetClass  La classe DTO Request visée (ex: BookRequest.class, VideoGameRequest.class).
     * @param isCreation   true pour charger le schéma de création, false pour la modification.
     * @return Une liste de chaînes contenant les messages d'erreurs (vide si valide).
     */
    public List<String> validate(String rawJson, Class<?> targetClass, boolean isCreation) {
        try {
            String fileSuffix = isCreation ? "-create-schema.json" : "-update-schema.json";
            
            // 💡 Cible maintenant correctement le nom du DTO (ex: BookRequest-create-schema.json)
            Path schemaPath = schemaDirectory.resolve(targetClass.getSimpleName() + fileSuffix);
            
            if (!Files.exists(schemaPath)) {
                throw new IllegalArgumentException("Le fichier de spécification JSON Schema est introuvable : " 
                        + schemaPath.toAbsolutePath());
            }

            String schemaContent = Files.readString(schemaPath);
            JsonSchema schema = schemaFactory.getSchema(schemaContent);
            JsonNode jsonNode = objectMapper.readTree(rawJson);
            Set<ValidationMessage> errors = schema.validate(jsonNode);

            return errors.stream()
                    .map(ValidationMessage::getMessage)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return List.of("Structure syntaxique de la requête invalide ou erreur de schéma : " + e.getMessage());
        }
    }
}