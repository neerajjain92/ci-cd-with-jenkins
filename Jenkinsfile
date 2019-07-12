pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                withMaven(maven: 'maven_3_5_4') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([[$class          : 'UsernamePasswordMultiBinding',
                                  credentialsId   : 'PIVOTAL_CLOUD_FOUNDRY',
                                  usernameVariable: 'USERNAME',
                                  passwordVariable: 'PASSWORD']]) {

                    sh 'cf login -a http://api.run.pivotal.io -u $USERNAME -p $PASSWORD'
                    sh 'cf push'
                }
            }
        }
    }
}
