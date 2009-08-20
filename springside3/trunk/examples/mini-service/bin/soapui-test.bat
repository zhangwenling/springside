@echo off
echo [INFO] 使用soapUi maven plugin 执行自动化测试.

cd ..
call mvn integration-test -Psoapui
pause