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


       post {
          always {
              // Realizar a análise de cobertura de teste
              // Salvar o resultado da cobertura em uma variável
              sh 'coverage_command' // Comando para gerar a cobertura e salvá-la em um arquivo
              script {
                  def cobertura = sh(script: "grep 'coverage:' coverage_file | awk '{print \$2}'", returnStdout: true).trim()
                  env.COBERTURA = cobertura
              }
          }
       }

    }

    stage('Deploy to Prod') {
        when {
            expression { currentBuild.result == 'SUCCESS' && env.COBERTURA.toFloat() >= 30 }
        }

        echo 'DEU CERTO'
    }
  }

}