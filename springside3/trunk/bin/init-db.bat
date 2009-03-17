@echo off
echo [INFO] 使用maven sql plugin 初始化mini-web及showcase数据库.

cd ..\examples\mini-web
call mvn initialize -Pinitdb

cd ..\showcase
call mvn initialize -Pinitdb
pause
