@echo off
echo [INFO] [INFO] 使用soapUi maven plugin 执行自动化测试.

cd ..
call mvn eviware:maven-soapui-plugin:test -Psoapui
pause