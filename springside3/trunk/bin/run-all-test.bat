@echo off
echo [INFO] Run all modules unit test, all examples unit/integration/funcional test

cd ..\
call mvn test -Pmodules
call mvn test -Pexamples -Pintegration
cd examples\mini-web
call mvn  integration-test  -Pfunctional
cd ..\mini-service
call mvn eviware:maven-soapui-plugin:test -Psoapui
pause
