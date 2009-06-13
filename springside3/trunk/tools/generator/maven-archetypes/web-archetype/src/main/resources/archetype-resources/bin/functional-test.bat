@echo off
echo [INFO] 使用maven运行selenium 功能测试.
echo [INFO] 请确保Tomcat 6已启动并已在conf/tomcat-users.xml中设置admin用户.
echo [INFO] 请确保Selenium Server已启动.

cd ..
call mvn integration-test -Pfunctional
cd bin
pause