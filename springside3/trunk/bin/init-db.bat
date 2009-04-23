@echo off
echo [INFO] 使用maven sql plugin 初始化mini-service、mini-web及showcase数据库.

cd ..\examples\mini-service
call mvn initialize -Pinitdb

cd ..\mini-web
call mvn initialize -Pinitdb

cd ..\showcase
call mvn initialize -Pinitdb
pause
