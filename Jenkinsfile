pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo '======================================'
                echo 'Récupération du projet depuis GitHub'
                echo '======================================'

                checkout scm
            }
        }

        stage('Environment') {
            steps {
                echo '======================================'
                echo 'Vérification de l’environnement'
                echo '======================================'

                sh '''
                    echo "Java :"
                    java -version

                    echo ""
                    echo "Git :"
                    git --version

                    echo ""
                    echo "Maven Wrapper :"
                    chmod +x mvnw
                    ./mvnw -version
                '''
            }
        }

        stage('Clean') {
            steps {
                echo '======================================'
                echo 'Nettoyage Maven'
                echo '======================================'

                sh './mvnw clean'
            }
        }

        // Pas de tests actuellement dans le projet
        // La phase Test sera ajoutée lorsque les tests seront disponibles.

        /* stage('Test') {
            steps {
                echo '======================================'
                echo 'Exécution des tests'
                echo '======================================'

                sh './mvnw test'
            }

            post {
                always {
                    echo 'Publication des rapports JUnit...'

                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: false
                    )
                }
            } 
        } */

        stage('Package') {
            steps {
                echo '======================================'
                echo 'Construction du JAR Spring Boot'
                echo '======================================'

                sh './mvnw package -DskipTests'

                echo 'JAR généré :'

                sh '''
                    find target \
                        -maxdepth 1 \
                        -type f \
                        -name "*.jar" \
                        -not -name "*.original" \
                        -exec ls -lh {} \\;
                '''
            }
        }

        stage('Archive') {
            steps {
                echo '======================================'
                echo 'Archivage du JAR dans Jenkins'
                echo '======================================'

                archiveArtifacts(
                    artifacts: 'target/*.jar',
                    fingerprint: true
                )
            }
        }

        stage('Docker Build') {
            steps {
                echo '======================================'
                echo "Construction de l'image Docker"
                echo '======================================'

                sh '''
                    docker build -t mon-service:latest .
                '''
            }
        }

        stage('Deploy') {
            steps {
                echo '======================================'
                echo 'Déploiement du service'
                echo '======================================'

                sh '''
                    docker stop mon-service || true
                    docker rm mon-service || true
                    
                    # Application accessible depuis http://192.168.128.103:8081
                    docker run -d \
                        --name mon-service \
                        --restart unless-stopped \
                        -p 8081:8080 \
                        mon-service:latest
                '''
            }
        }

    }

    post {

        success {
            echo '''
            ======================================
            BUILD TERMINÉ AVEC SUCCÈS
            ======================================
            '''
        }

        failure {
            echo '''
            ======================================
            BUILD EN ÉCHEC
            ======================================
            '''
        }

        always {
            echo 'Fin du pipeline.'
        }
    }
}
