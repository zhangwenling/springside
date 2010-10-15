@echo off

cd %~dp0
call mvn org.codehaus.sonar:sonar-maven3-plugin:2.3:sonar

pause