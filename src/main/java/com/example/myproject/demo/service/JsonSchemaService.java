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

@Service
public class JsonSchemaService {

        private final SchemaGenerator generator;
        private final ObjectMapper mapper;
        private final Path outputDirectory;

        public JsonSchemaService(
                        @Value("${json-schema.output-directory:target/generated-schemas}") 
                        String outputDirectory) {

                /*
                 * Configuration du générateur JSON Schema
                 */
                SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
                                SchemaVersion.DRAFT_2019_09,
                                OptionPreset.PLAIN_JSON);

                /*
                 * ---------------------------------------------------------
                 * 1. Jakarta Validation
                 * ---------------------------------------------------------
                 *
                 * Permet notamment de récupérer :
                 *
                 * @NotNull
                 * 
                 * @NotBlank
                 * 
                 * @Size
                 * 
                 * @Min
                 * 
                 * @Max
                 * 
                 * @DecimalMin
                 * 
                 * @DecimalMax
                 * 
                 * @Pattern
                 * ...
                 */
                configBuilder.with(new JakartaValidationModule());

                /*
                 * ---------------------------------------------------------
                 * 2. Champs obligatoires
                 * ---------------------------------------------------------
                 *
                 * Un champ est obligatoire si :
                 *
                 * @Column(nullable = false)
                 *
                 * Le module Jakarta Validation s'occupe déjà de @NotNull.
                 */
                configBuilder.forFields()
                                .withRequiredCheck(field -> {
                                        Column column = field.getAnnotationConsideringFieldAndGetter(
                                                        Column.class);

                                        return column != null && !column.nullable();
                                });

                /*
                 * ---------------------------------------------------------
                 * 3. Ignorer les identifiants JPA
                 * ---------------------------------------------------------
                 *
                 * Les propriétés annotées @Id ne seront pas présentes
                 * dans le JSON Schema.
                 */
                configBuilder.forFields()
                                .withIgnoreCheck(field -> field.getAnnotationConsideringFieldAndGetter(
                                                Id.class) != null);

                /*
                 * ---------------------------------------------------------
                 * 4. Configuration finale
                 * ---------------------------------------------------------
                 */
                SchemaGeneratorConfig config = configBuilder.build();

                this.generator = new SchemaGenerator(config);
                this.mapper = new ObjectMapper();
                this.outputDirectory = Paths.get(outputDirectory);
        }

        /**
         * Génère le JSON Schema d'une classe et le sauvegarde.
         *
         * @param clazz classe Java à analyser
         * @return JSON Schema sous forme de String
         */
        public String generateAndSaveSchema(Class<?> clazz) {

                if (clazz == null) {
                        throw new IllegalArgumentException(
                                        "La classe ne peut pas être null");
                }

                try {
                        /*
                         * Génération du schema
                         */
                        JsonNode jsonSchema = generator.generateSchema(clazz);

                        /*
                         * Conversion en JSON lisible
                         */
                        String schemaString = mapper.writerWithDefaultPrettyPrinter()
                                        .writeValueAsString(jsonSchema);

                        /*
                         * Création du répertoire de sortie
                         */
                        Files.createDirectories(outputDirectory);

                        /*
                         * Nom du fichier
                         *
                         * Exemple :
                         * User.class -> User-schema.json
                         */
                        Path targetFile = outputDirectory.resolve(
                                        clazz.getSimpleName() + "-schema.json");

                        /*
                         * Écriture du fichier
                         */
                        Files.writeString(targetFile, schemaString);

                        System.out.println(
                                        "✅ Schéma JSON généré : "
                                                        + targetFile.toAbsolutePath());

                        return schemaString;

                } catch (IOException e) {

                        System.err.println(
                                        "❌ Erreur lors de l'écriture du schéma pour "
                                                        + clazz.getName());

                        throw new RuntimeException(
                                        "Impossible de sauvegarder le JSON Schema",
                                        e);
                }
        }
}