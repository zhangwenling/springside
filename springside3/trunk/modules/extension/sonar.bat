@echo off
echo [INFO] Run sonar 2.3 checking with maven 3.0.

cd %~dp0
call mvn org.codehaus.sonar:sonar-maven3-plugin:2.3:sonar

pause