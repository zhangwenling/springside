@echo off
echo [INFO] Use Snoar (http://sonar.codehaus.org/) to check all code with checkstyle, pmd, findbugs. 
echo [INFO] Set system environment variable MAVEN_OPTS=-Xmx1024m -XX:MaxPermSize=128m
cd ..\

call mvn clean install sonar:sonar -Pmodules -Pexamples 

pause