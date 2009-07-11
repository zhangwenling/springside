@echo off
echo [INFO] 使用maven sql plugin 初始化所有Examples的数据库.

cd ..\
call mvn initialize -Pinitdb -Pexamples

cd examples\mini-service
call mvn dbunit:operation

cd ..\mini-web
call mvn dbunit:operation

cd ..\showcase
call mvn dbunit:operation

pause
