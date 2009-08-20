@echo off
echo [INFO] 使用soapUi maven plugin 执行自动化测试.
echo [INFO] 请确保Tomcat 6已启动并已在conf/tomcat-users.xml中设置admin用户.

cd ..
call mvn eviware:maven-soapui-plugin:test -Psoapui
pause