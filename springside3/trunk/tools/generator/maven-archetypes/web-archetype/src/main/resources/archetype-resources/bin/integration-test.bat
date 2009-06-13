@echo off
echo [INFO] 使用maven集成测试.
echo [INFO] 请确保数据库已启动.

cd ..
call mvn integration-test  -Pintegration
cd bin
pause