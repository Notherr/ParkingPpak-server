pipeline {

    environment {
        registry = 'hyungjungu/parking_ppak_server'
        registryCredential = 'dockerhub'
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
                sh 'docker build .'
                echo 'tag with git commit'
                sh 'export VERSION=$(git rev-parse HEAD)'
                sh 'docker tag parkingppak:latest parking_ppak_server:$VERSION'
                sh 'echo $VERSION > .build_id/commit_id.log'
            }
        }

        stage('Docker push'){
            steps{
                echo 'Pushing docker image ...'
                sh 'docker push parkingacr.azureacr.io/parkingppak:$(VERSION)'
            }
        }

    }
}