@echo off
echo [INFO] 确保默认JDK版本为JDK6.0.

if exist "..\tools\maven" goto begin
echo [INFO] tools\maven目录不存在，请下载all-in-one版本
goto end

:begin
set MAVEN_BAT="%cd%\..\tools\maven\apache-maven-2.1.0\bin\mvn.bat"
cd ..\

echo [Step 1] 执行tools/maven/nexus/nexus-webapp/bin/jsw/windows-x86-32/Nexus.bat,启动Nexus Maven私服
start tools\maven\nexus-1.3.1\nexus-webapp-1.3.1\bin\jsw\windows-x86-32\Nexus.bat

echo [Step 2] 执行servers/derby/start-db.bat 以Standalone形式启动Derby数据库
cd servers\derby
start start-db.bat
cd ..\..\

echo [Step 3] 执行servers/tomcat/apache-tomcat-6.0.18/bin/startup.bat 启动Tomcat服务器
cd  servers\tomcat\apache-tomcat-6.0.18\bin\
start startup.bat
cd ..\..\..\..\

echo [INFO] 暂停若干秒等待nexus启动.
ping -n 21 127.0.0.1>null

echo [Step 4] 安装SpringSide3 modules 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib -Pmodules
call %MAVEN_BAT% clean install  -Pmodules
 
echo [Step 5] 初始化Jar包，编译、测试、打包、部署三个Examles项目到tomcat
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Pexamples
call %MAVEN_BAT% dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib  -DincludeScope=runtime -Pexamples
call %MAVEN_BAT% tomcat:undeploy -Pexamples
call %MAVEN_BAT% clean compile war:exploded tomcat:exploded -Pinitdb -Pnotest -Pexamples

echo [Step 6] 启动IE浏览上述三个项目 .
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
:end
pause


