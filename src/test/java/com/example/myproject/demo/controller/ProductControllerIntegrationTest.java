// Déclaration du package logique où est rangé notre fichier de test d'intégration
package com.example.myproject.demo.controller;

// Importation de l'annotation JUnit permettant de donner un nom compréhensible au test dans la console
import org.junit.jupiter.api.DisplayName;
// Importation de l'annotation JUnit marquant une méthode comme étant un scénario de test exécutable
import org.junit.jupiter.api.Test;
// Importation de l'annotation Spring permettant d'injecter automatiquement un composant ou un bean
import org.springframework.beans.factory.annotation.Autowired;
// Importation de l'annotation configurant automatiquement l'outil de simulation HTTP MockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// Importation de l'annotation démarrant le serveur et le contexte Spring Boot complet pour le test
import org.springframework.boot.test.context.SpringBootTest;
// Importation de l'utilitaire permettant de forcer les formats de requêtes (ex: APPLICATION_JSON)
import org.springframework.http.MediaType;
// Importation de la classe principale servant à simuler de faux appels HTTP vers nos contrôleurs API
import org.springframework.test.web.servlet.MockMvc;
// Importation statique de la méthode 'post' permettant de simuler l'envoi d'une requête HTTP POST
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// Importation statique pour valider le code de statut HTTP retourné (ex: 201 Created, 400 Bad Request)
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// Importation statique de l'outil permettant de lire et de naviguer à l'intérieur du JSON de réponse
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// Importation statique du matcher Hamcrest vérifiant qu'un élément précis est inclus dans une liste
import static org.hamcrest.Matchers.hasItem;
// Importation statique du matcher Hamcrest vérifiant qu'une valeur n'est pas nulle (ex: un ID généré)
import static org.hamcrest.Matchers.notNullValue;
import org.springframework.transaction.annotation.Transactional; // 💡 1. IMPORT INDISPENSABLE

/**
 * Classe de test d'intégration globale.
 * Elle simule de bout en bout l'envoi de requêtes HTTP à l'API, l'exécution du
 * validateur
 * de schéma JSON, la conversion Jackson polymorphe et l'écriture en base de
 * données.
 */
@SpringBootTest // Demande à Spring de charger tous les services, dépôts et beans de
                // l'application réelle
@AutoConfigureMockMvc // Demande à Spring d'initialiser le simulateur HTTP MockMvc pour nos tests
@Transactional // 💡 2. TOUT SE JOUÉ ICI : Annule automatiquement les écritures SQL à la fin de
               // chaque test !
class ProductControllerIntegrationTest {

    @Autowired // Injecte automatiquement l'instance de simulation HTTP configurée par Spring
    private MockMvc mockMvc; // Déclaration de la variable MockMvc qui servira à appeler nos routes REST

    @Test // Indique au moteur JUnit qu'il s'agit d'un scénario de test à exécuter
    @DisplayName("1. POST valide : Devrait créer le livre avec succès (HTTP 201)") // Nom lisible affiché dans l'IDE
    void shouldCreateBookSuccessfully() throws Exception { // 'throws Exception' est requis par l'infrastructure MockMvc

        // 💡 Création d'une chaîne de caractères représentant un JSON parfait et valide
        // pour la création d'un livre
        String validBookJson = """
                {
                    "productType": "book",
                    "name": "Fondation et Empire",
                    "price": 8.20,
                    "description": "Le second tome du cycle de Fondation.",
                    "isbn": "9782070463640",
                    "author": "Isaac Asimov",
                    "publisher": "Denoël",
                    "numberOfPages": 350
                }
                """;

        // 🚀 Déclenchement de l'appel HTTP virtuel et traitement de la réponse
        mockMvc.perform(post("/api/products") // Démarre une simulation de requête POST sur l'URL /api/products
                .contentType(MediaType.APPLICATION_JSON) // Spécifie dans les en-têtes HTTP que le format envoyé est du
                                                         // JSON
                .content(validBookJson)) // Injecte le texte du JSON valide dans le corps de la requête HTTP

                // 🔍 Phase de vérification des résultats (Assertions)
                .andExpect(status().isCreated()) // Vérifie que l'API renvoie bien le code de succès d'écriture '201
                                                 // Created'
                .andExpect(jsonPath("$.id", notNullValue())) // Navigue dans le JSON reçu, cible 'id' et vérifie qu'il
                                                             // n'est pas nul
                .andExpect(jsonPath("$.name").value("Fondation et Empire")) // Vérifie que le nom renvoyé correspond à
                                                                            // la saisie
                .andExpect(jsonPath("$.isbn").value("9782070463640")); // Vérifie que le code ISBN renvoyé correspond à
                                                                       // la saisie
    }

    @Test // Indique au moteur JUnit qu'il s'agit d'un deuxième scénario de test autonome
    @DisplayName("2. POST invalide : Devrait être rejeté par le JSON Schema pour prix négatif (HTTP 400)") // Nom
                                                                                                           // affiché
    void shouldRejectWhenPriceIsNegative() throws Exception {

        // 💡 Création d'un JSON comportant une infraction volontaire : la valeur du
        // prix est négative ("price": -5.00)
        String invalidBookJson = """
                {
                    "productType": "book",
                    "name": "Livre Invalide",
                    "price": -5.00,
                    "description": "description invalide.",
                    "isbn": "9782070463641",
                    "author": "Auteur Test",
                    "publisher": "Éditeur Test",
                    "numberOfPages": 100
                }
                """;

        // 🚀 Déclenchement de l'appel HTTP virtuel avec les données erronées
        mockMvc.perform(post("/api/products") // Envoie la requête POST sur la même route d'API
                .contentType(MediaType.APPLICATION_JSON) // Spécifie l'en-tête de contenu standard application/json
                .content(invalidBookJson)) // Injecte le JSON erroné dans le corps de la requête HTTP

                // 🔍 Phase de vérification du rejet (Assertions)
                .andExpect(status().isBadRequest()) // S'assure que le validateur bloque le flux et retourne un code
                                                    // '400 Bad Request'
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // Vérifie que le champ de statut de notre erreur
                                                                      // vaut 'BAD_REQUEST'

                // 💡 Vérifie que le tableau 'errors' contient le message précis généré par le
                // validateur de schéma networknt
                .andExpect(jsonPath("$.errors", hasItem("$.price: doit avoir une valeur minimale de 0")));
    }

    @Test // Indique au moteur JUnit qu'il s'agit d'un troisième scénario de test autonome
    @DisplayName("3. POST invalide : Devrait être rejeté pour format ISBN incorrect (HTTP 400)") // Nom affiché
    void shouldRejectWhenIsbnIsMalformed() throws Exception {

        // 💡 Création d'un JSON comportant une infraction à la Regex : l'ISBN contient
        // des lettres ("abc") au lieu de chiffres purs
        String invalidIsbnJson = """
                {
                    "productType": "book",
                    "name": "Livre Code Erroné",
                    "price": 10.00,
                    "description": "mauvais isbn",
                    "isbn": "978207abc3641",
                    "author": "Auteur Test",
                    "publisher": "Éditeur Test",
                    "numberOfPages": 100
                }
                """;

        // 🚀 Déclenchement de l'appel HTTP virtuel
        mockMvc.perform(post("/api/products") // Démarre la simulation de la requête POST
                .contentType(MediaType.APPLICATION_JSON) // Ajoute l'en-tête HTTP obligatoire pour le format JSON
                .content(invalidIsbnJson)) // Injecte le JSON à l'ISBN erroné dans le corps de la requête

                // 🔍 Phase de vérification du rejet par expression régulière
                .andExpect(status().isBadRequest()) // S'assure que la requête est rejetée avec un code d'erreur '400
                                                    // Bad Request'
                .andExpect(jsonPath("$.status").value("BAD_REQUEST")) // Vérifie la clé de statut dans la réponse de
                                                                      // notre contrôleur

                // 💡 Vérifie que le tableau 'errors' contient le message stipulant le
                // non-respect du pattern de l'expression régulière
                .andExpect(jsonPath("$.errors",
                        hasItem("$.isbn: ne correspond pas au modèle d'expression régulière ^\\d{10,13}$")));
    }
}