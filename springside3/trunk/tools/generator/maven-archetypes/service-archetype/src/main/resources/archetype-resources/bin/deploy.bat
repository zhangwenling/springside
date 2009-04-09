@echo off
echo [INFO] 使用maven编译项目，并以目录形式部署到tomcat中.
echo [INFO] 请确保Tomcat 6已启动并已在conf/tomcat-users.xml中设置admin用户.
echo [INFO] 详见 http://wiki.springside.org.cn/display/calvin/maven

cd ..
call mvn tomcat:undeploy
call mvn test war:exploded tomcat:exploded
cd bin
pause