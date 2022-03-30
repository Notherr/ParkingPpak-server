pipeline {

    environment {
        registry = 'hyungjungu/parking_ppak_server'
        registryCredential = 'dockerhub'
        buildNumber = "build_$BUILD_NUMBER"
    }

    agent any

    stages {

        stage('Clean'){
            steps{
                echo 'Executing clean ...'
                sh './gradlew clean'
            }
        }
        stage('Build'){
            steps{
                echo 'Building ...'
                sh './gradlew assemble'
            }
        }
        
        stage('Docker build'){
            steps{
                echo 'Building docker image...'
                sh 'docker-compose build'
                sh 'docker tag parkingppak:latest parking_ppak_server:latest'
            }
        }

        stage('Docker push'){
            steps{
                echo 'Pushing docker image ...'
                //Todo
            }
        }

    }
}