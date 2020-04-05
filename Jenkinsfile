pipeline {
    agent any 
    stages {

        stage('Cleanup') {
            steps {
                sh './gradlew --no-daemon clean'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew --no-daemon check'
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Build') {
            steps {
                sh './gradlew --no-daemon build'
            }
        }

        stage('Static Code Analisys') {
            steps {
                sh './gradlew sonarqube'
            }
        }

        stage('Deploy') { 
            steps {
                // TODO
                echo "Deploying..."
            }
        }
    }
}