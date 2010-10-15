@echo off
echo [INFO] Run sonar checking.

cd %~dp0
cd ..

call mvn org.codehaus.sonar:sonar-maven3-plugin:2.3:sonar

cd bin
pause