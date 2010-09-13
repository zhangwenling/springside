@echo off
echo [INFO] Use maven jetty-plugin run the project.

set MAVEN_OPTS=-XX:MaxPermSize=128m

cd %~dp0
cd ..
call mvn jetty:run -Dmaven.test.skip=true
cd bin
pause 