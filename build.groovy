pipeline {
    agent any
    environment {
        POLARIS_TOKEN = credentials('polaris-token')
    }
 
    stages {
 
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://gitlab.com/bridgenext1/proftpd'
            }
        }
 
        stage('Build') {
            steps {
                echo 'Building GitLab project...'
            }
        }
 
        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }
 
        stage('Security Scan') {
            steps {
                echo 'Running Black Duck Polaris security scan...'
            }
        }
        stage('Polaris Black Duck Security Scan') {
            steps {
                security_scan(
                    product: 'polaris',
                    polaris_server_url: POLARIS_URL,
                    polaris_access_token: POLARIS_TOKEN,
                    polaris_application_name: 'Cop Testing application',
                    polaris_project_name: 'ProFTPd',
                    polaris_branch_name: 'master',
                    polaris_assessment_types: 'SAST,SCA'
                )
            }
        }
    }
 
    post {
        success {
            echo 'Pipeline completed successfully'
        }
 
        failure {
            echo 'Pipeline failed'
        }
    }
}
