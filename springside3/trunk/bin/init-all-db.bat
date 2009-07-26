@echo off
echo [INFO] 使用maven sql plugin和dbunit plugin 初始化所有Examples的数据库.

cd ..\
call mvn initialize -Pinitdb -Pexamples

pause
