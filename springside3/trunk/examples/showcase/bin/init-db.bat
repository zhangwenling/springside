@echo off
echo [INFO] [INFO] 使用maven sql plugin 初始化数据库.

cd ..
call mvn initialize -Pinitdb
rem call mvn dbunit:operation
pause