pipeline {
    agent any 
    stages {
        stage('Build') { 
            steps {
                // 
                echo "Building dev branch..."
                sh './gradlew clean'
            }
        }
        stage('Test') { 
            steps {
                //
                echo "Testing..." 
                sh './gradlew test'
            }
        }
        stage('Deploy') { 
            steps {
                // 
                echo "Deploying..."
            }
        }
    }
}