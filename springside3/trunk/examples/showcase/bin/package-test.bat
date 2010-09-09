@echo off
echo [INFO] Use maven assembly-plugin package jsw+jetty+webapp zip.

cd %~dp0
cd ..
call mvn clean package -Ppackage-bin -Dmaven.test.skip=true -DpackageId=test
cd bin
pause 