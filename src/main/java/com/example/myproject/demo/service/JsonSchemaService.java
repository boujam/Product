package com.example.myproject.demo.service;

// Nous utilisons l'alternative moderne compatible Jackson 3
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
 
public class JsonSchemaService {

    private final SchemaGenerator generator;
    private final ObjectMapper mapper;

    public JsonSchemaService() {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_2019_09, 
                OptionPreset.PLAIN_JSON
        );
        SchemaGeneratorConfig config = configBuilder.build();
        this.generator = new SchemaGenerator(config);
        this.mapper = new ObjectMapper();
    }

    /**
     * Génère le schéma JSON et l'enregistre dans le dossier src/main/java/com/example/myproject/demo/schema
     */
    public String generateAndSaveSchema(Class<?> clazz) {
        try {
            // 1. Génération du contenu du schéma
            JsonNode jsonSchema = generator.generateSchema(clazz);
            String schemaString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);
            
            // 2. Définition et création automatique du chemin absolu/relatif vers le sous-dossier
            Path targetDirectory = Paths.get("src", "main", "java", "com", "example", "myproject", "demo", "schema");
            Files.createDirectories(targetDirectory); // Crée le dossier s'il n'existe pas
            
            // 3. Spécification du fichier final (ex: VideoGame-schema.json)
            Path targetFile = targetDirectory.resolve(clazz.getSimpleName() + "-schema.json");
            
            // 4. Écriture physique sur le disque
            try (FileWriter writer = new FileWriter(targetFile.toFile())) {
                writer.write(schemaString);
                System.out.println("✅ Schéma JSON enregistré dans le package dédié : " + targetFile.toAbsolutePath());
            }
            
            return schemaString;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la génération/sauvegarde dans le sous-dossier pour " + clazz.getSimpleName());
            e.printStackTrace();
            return null;
        }
    }
}
