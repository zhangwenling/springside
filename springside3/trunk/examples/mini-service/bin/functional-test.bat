@echo off
echo [INFO] 使用maven运行selenium 功能测试.

cd ..
call mvn integration-test -Pfunctional
cd bin
pause