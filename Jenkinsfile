pipeline {

    environment {
        VERSION = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
        registry = "parkingacr.azurecr.io/parkingppak"
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
                sh 'docker build -t ${registry} .'
                echo 'tag with git commit'
                echo 'VERSION is ${VERSION}'
                sh 'docker tag ${registry}:latest ${registry}:${VERSION}'
                sh 'echo $VERSION > .build_id/commit_id.log'
            }
        }

        stage('Docker push'){
            steps{
                echo 'Pushing docker image ...'
                sh 'docker push ${registry}:${VERSION}'
            }
        }

    }
}