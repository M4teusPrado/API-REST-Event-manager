pipeline {
  agent any
  stages {
    stage("verify tooling") {
      steps {
        bat '''
          docker version
          docker info
          docker compose version
          curl --version
          '''
      }
    }
    stage('Prune Docker data') {
      steps {
        bat 'docker system prune -a --volumes -f'
      }
    }
    stage('Start container') {
      steps {
        bat 'docker compose up -d --no-color --wait'
        bat 'docker compose ps'
      }
    }
    stage('Run tests against the container') {
      steps {
        bat 'curl http://localhost:8080'
      }
    }

    stage('Build') {
       steps {
          script {
             // Execute os comandos necessários para construir a aplicação no ambiente de desenvolvimento (DEV)
             sh 'mvn clean package' // Comando Maven para compilar e empacotar a aplicação em um arquivo JAR
          }
       }
    }


  }

}