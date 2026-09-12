package com.example.myproject.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


/*
config

bash : java -version
openjdk version "21.0.12" 2026-07-21
OpenJDK Runtime Environment (build 21.0.12+8-1-24.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 21.0.12+8-1-24.04-Ubuntu, mixed mode, sharing)

MAVEN
bash : mvn -version
Apache Maven 3.8.7
Maven home: /usr/share/maven
Java version: 21.0.12, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-136-generic", arch: "amd64", family: "unix"

Commande			Utilité
mvn -version		Afficher la version de Maven et Java
mvn clean			Supprimer le dossier target/
mvn compile			Compiler le code principal
mvn test			Compiler et exécuter les tests
mvn package			Compiler, tester et créer le .jar
mvn verify			Exécuter les vérifications du projet
mvn install			Construire et installer le .jar dans le dépôt Maven local
mvn clean package	Nettoyer puis construire le .jar 
					d'après la configuration de Maven installé sous Ubuntu
mvn spring-boot:run	Lancer directement l'application Spring Boot


Maven Wrapper
bash : ./mvnw -version
Apache Maven 3.9.16 (2bdd9fddda4b155ebf8000e807eb73fd829a51d5)
Maven home: /home/master/.m2/wrapper/dists/apache-maven-3.9.16/56ba1f9f
Java version: 21.0.12, vendor: Ubuntu, runtime: /usr/lib/jvm/java-21-openjdk-amd64
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "6.8.0-136-generic", arch: "amd64", family: "unix"
Sélectionne 


Commande
./mvnw clean package	Nettoyer puis construire le .jar 
						d'après la configuration stockée dans .mvn/wrapper/maven-wrapper.properties


Git
git init 	Créer le dépôt Git local
git status 	Afficher le statut du depot local

git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/boujam/Product.git
git push -u origin main



FIGER LE CODE

master@MS7C56:/media/master/DATASSD/developpement/github/ollama-vscode$ git status
Sur la branche main
Votre branche est à jour avec 'origin/main'.

Modifications qui ne seront pas validées :
  (utilisez "git add <fichier>..." pour mettre à jour ce qui sera validé)
  (utilisez "git restore <fichier>..." pour annuler les modifications dans le répertoire de travail)
        modifié :         package-lock.json

aucune modification n'a été ajoutée à la validation (utilisez "git add" ou "git commit -a")
master@MS7C56:/media/master/DATASSD/developpement/github/ollama-vscode$ git rev-parse HEAD
56ea9f3cfced1c390d9777cc77f0418ba4237bb7
master@MS7C56:/media/master/DATASSD/developpement/github/ollama-vscode$ sha256sum ollama.vsix
66a4f749d37f3a766141ac20a5af73e766f0f616733db9c4bc69b8daa646486d  ollama.vsix




ia

bash : 
ollama list 2>/dev/null

result :
codeqwen:latest    df352abf55b1    4.2 GB    3 months ago    
qwen3.5:latest     6488c96fa5fa    6.6 GB    3 months ago    
gemma4:latest      c6eb396dbd59    9.6 GB    3 months ago  

api : 
http://localhost:11434/api/tags

response :
{
  "models": [
    {
      "name": "codeqwen:latest",
      "model": "codeqwen:latest",
      "modified_at": "2026-06-08T00:25:31.781414921+02:00",
      "size": 4179438533,
      "digest": "df352abf55b115af149186bbe66af38888ece52bc3121324fedd96f6ded54f01",
      "details": {
        "parent_model": "",
        "format": "gguf",
        "family": "qwen2",
        "families": [
          "qwen2"
        ],
        "parameter_size": "7.3B",
        "quantization_level": "Q4_0",
        "context_length": 65536,
        "embedding_length": 4096
      },
      "capabilities": [
        "completion"
      ]
    },
    {
      "name": "qwen3.5:latest",
      "model": "qwen3.5:latest",
      "modified_at": "2026-06-07T16:49:00.234169655+02:00",
      "size": 6594474711,
      "digest": "6488c96fa5faab64bb65cbd30d4289e20e6130ef535a93ef9a49f42eda893ea7",
      "details": {
        "parent_model": "",
        "format": "gguf",
        "family": "qwen35",
        "families": [
          "qwen35"
        ],
        "parameter_size": "9.7B",
        "quantization_level": "Q4_K_M",
        "context_length": 262144,
        "embedding_length": 4096
      },
      "capabilities": [
        "vision",
        "completion",
        "tools",
        "thinking"
      ]
    },
    {
      "name": "gemma4:latest",
      "model": "gemma4:latest",
      "modified_at": "2026-06-06T20:33:16.486503211+02:00",
      "size": 9608350718,
      "digest": "c6eb396dbd5992bbe3f5cdb947e8bbc0ee413d7c17e2beaae69f5d569cf982eb",
      "details": {
        "parent_model": "",
        "format": "gguf",
        "family": "gemma4",
        "families": [
          "gemma4"
        ],
        "parameter_size": "8.0B",
        "quantization_level": "Q4_K_M"
      },
      "capabilities": [
        "completion",
        "tools",
        "thinking"
      ]
    }
  ]
}


*/
}
