podTemplate(containers: [
    containerTemplate(
        name: 'gradle',
        image: 'gradle:6.3-jdk14',
        command: 'sleep',
        args: '30d'
        ),
])   {

    node(POD_LABEL) {
        stage('Run pipeline against a gradle project') {
           git branch: 'main', url: 'https://github.com/sportstube28/week6.git' 
            container('gradle') {
                stage('Build a gradle project') {
                    sh '''
                    chmod +x gradlew
                    ./gradlew test
                    '''
                    }
        stage("Code coverage") {
          echo "My Code coverage branch is: ${env.BRANCH_NAME} branch"
          if ( env.BRANCH_NAME == "main") 
           try {
                   sh '''
                   pwd
                   ./gradlew jacocoTestCoverageVerification
                   ./gradlew jacocoTestReport
                   '''
            } catch (Exception E) {
                echo 'Failure detected'
            }
            publishHTML (target: [
              reportDir: 'Chapter08/sample1/build/reports/jacoco/test/html',
              reportFiles: 'index.html',
              reportName: "JaCoCo Report"
            ])
        stage("jacoco checkstyle") {
          echo "My Code coverage branch is: ${env.BRANCH_NAME} branch"
          if ( env.BRANCH_NAME == "feature") 
           try {
                   sh '''
                   pwd
                   ./gradlew jacocoTestCoverageVerification
                   ./gradlew checkstyleMain
                   '''
            } catch (Exception E) {
                echo 'Failure detected'
            }
            publishHTML (target: [
              reportDir: 'Chapter08/sample1/build/reports/jacoco/test/html',
              reportFiles: 'index.html',
              reportName: "JaCoCocheckstyle Report"
            ])
        }
     }
   }
  }
}
}
