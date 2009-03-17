@echo off
echo [INFO] 确保设置PATH系统变量包含maven2.0.9或以上版本的bin目录.

echo [Step 1] 安装SpringSide3 modules 和archetypes到本地Maven仓库.
cd ..\
call mvn dependency:copy-dependencies -DoutputDirectory=lib
call mvn clean install

echo [Step 2] 执行servers/derby/start-db.bat 以Standalone形式启动Derby数据库
cd servers\derby
start start-db.bat
cd ..\..\

echo 暂停5秒等待derby启动
ping -n 6 127.0.0.1>null


echo [Step 3] 请将servers/tomcat/ci-config-sample/conf/tomcat-user.xml 复制到你的tomcat目录，然后手工启动tomcat(端口为8080)，完成后请按任意键继续
pause


echo [Step 4] 初始化Jar包，编译、测试、打包、部署mini-web项目到tomcat，访问路径为http://localhost:8080/mini-web
cd examples\mini-web
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call mvn tomcat:undeploy
call mvn clean compile war:exploded tomcat:exploded -Pinitdb
cd ..\..\

echo [Step 5] 初始化Jar包，编译、测试、打包、部署mini-service项目到tomcat，http://localhost:8080/mini-service
cd examples\mini-service
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call mvn tomcat:undeploy
call mvn clean compile war:exploded tomcat:exploded
cd ..\..\

echo [Step 6] 初始化Jar包，编译、测试、打包、部署showcase项目到tomcat，http://localhost:8080/showcase
cd examples\showcase
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime
call mvn tomcat:undeploy
call mvn clean compile war:exploded tomcat:exploded -Pinitdb
cd ..\..\

echo [Step 7] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
pause