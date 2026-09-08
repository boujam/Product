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




*/
}
