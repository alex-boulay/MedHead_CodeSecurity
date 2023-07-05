def outSpringFile = 'outSpringFile2.txt'
def maxWaitTimeSeconds = 300
def waitIntervalSeconds = 5
def pid = -1

pipeline {
    agent any
    environment {
        PID = ''
    }
    stages {        
        stage('Build') {
            steps {
                echo 'Building'
				bat 'mvn clean install' //-DskipTests=true'
            }
        }
        
        stage('Spring Thread') {
			steps {
                script {
					pid = powershell(script: '''
						# Start process and capture process info
						$processInfo = Start-Process -NoNewWindow -PassThru cmd "/c mvn spring-boot:run >  ${outSpringFile}"
						
						# Return the PID (process ID) for later use
						return $processInfo.Id
						''', returnStdout: true).trim()

                    echo "The PID is ${pid}"
					
					def startTime = currentBuild.startTimeInMillis
					def elapsedTime = 0
					def doneInitializing = false
					
					while (elapsedTime < maxWaitTimeSeconds * 1000 && !doneInitializing) {
						if (fileExists(outSpringFile)) {
							def matchingLine = powershell(returnStdout: true, script: "Get-Content ${outSpringFile} | Select-String -Pattern 'DONE INITIALIZING'").trim()
							doneInitializing = matchingLine ? true : false
							
							echo "Initializing done ? ${doneInitializing}"
						}
						if (!doneInitializing){
							sleep(waitIntervalSeconds)
						}
						elapsedTime = System.currentTimeMillis() - startTime
					}
					if (!doneInitializing) {
						echo "Timed-Out -> Autre instance spring ?"
						error "Application initialization timed out"
					}
					else {
						echo "INIT DONE"
					}
				}
			}
        }
        
        stage('Newman') {
            steps {
                // Test postman en utilisant Newman
                bat 'newman.cmd run ./MedHeadCollect.postman_collection.json --insecure'
            }
        }
        
        stage('Jmeter') {
            steps {
                // Execute les tests de perfomance jmeter
                bat 'jmeter -n -t MedHeadSecurity.jmx'
            }
        }
    }
    
    post {
		always {
			// Stop the Spring application
			bat "Taskkill /F /PID ${pid}"
			//sleep(5)
			//bat "del ${outSpringFile}"
		}
		/*
        success {
			script {
				if (env.BRANCH_NAME == 'dev') {
					bat '''
						git checkout -- jmeter.log
						git checkout main 
						git merge dev
						git push origin main
					'''
				}
			}
		}
		*/
    }
}