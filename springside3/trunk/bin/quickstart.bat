@echo off
echo [INFO] 确保默认JDK版本为JDK6.0.
echo [INFO] 确保设置PATH系统变量包含maven2.0.9或以上版本的bin目录.

echo [Step 1] 请将servers/tomcat/ci-config-sample/conf/tomcat-user.xml 复制到你的tomcat目录，然后手工启动tomcat(端口为8080)，完成后请按任意键继续
pause

echo [Step 2] 执行servers/derby/start-db.bat 以Standalone形式启动Derby数据库
cd ..\servers\derby
start start-db.bat
cd ..\..\

echo [Step 3] 安装SpringSide3 modules 和archetypes到本地Maven仓库.
call mvn dependency:copy-dependencies -DoutputDirectory=lib -Pmodules
call mvn clean install -Pmodules

echo [Step 4] 初始化依赖Jar包，初始化数据库，编译、打包、部署三个Examles项目到tomcat
call mvn dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Pexamples
call mvn dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Pexamples
call mvn tomcat:undeploy -Pexamples
call mvn clean compile war:exploded tomcat:exploded -Pinitdb  -Pnotest -Pexamples

echo [Step 5] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
pause