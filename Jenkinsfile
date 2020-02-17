pipeline {
    agent any 
    stages {
        stage('Build') { 
            steps {
                // 
                echo "Building dev branch..."
                sh 'gradle clean'
            }
        }
        stage('Test') { 
            steps {
                //
                echo "Testing..." 
                sh 'gradle test'
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