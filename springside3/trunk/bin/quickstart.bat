@echo off
echo [INFO] 确保默认JDK版本为JDK5.0.

if exist "..\servers\tomcat\apache-tomcat-6.0.20\" goto begin
echo [ERROR] ..\servers\tomcat\apache-tomcat-6.0.20目录不存在，请下载all-in-one版本
goto end

:begin
set MAVEN_BAT="%cd%\..\tools\maven\apache-maven-2.2.0\bin\mvn.bat"
cd ..\

echo [Step 1] 复制tools/maven/central-repository 到 %userprofile%\.m2\repository
xcopy /s/e/i/h/d "tools\maven\central-repository" "%USERPROFILE%\.m2\repository"

echo [Step 2] 执行servers/derby/start-db.bat 以Standalone形式启动Derby数据库
cd servers\derby
start start-db.bat
cd ..\..\

echo [Step 3] 执行servers/tomcat/apache-tomcat-6.0.20/bin/startup.bat 启动Tomcat服务器
cd  servers\tomcat\apache-tomcat-6.0.20\bin\
start startup.bat
cd ..\..\..\..\

echo [Step 4] 安装SpringSide3 modules 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -Dsilent=true -Pmodules
call %MAVEN_BAT% -o clean install  -Pmodules -Pnotest
 
echo [Step 5] 为三个Example项目初始化依赖Jar包。
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true -Pexamples
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib -DincludeScope=runtime -Dsilent=true -Pexamples

echo [Step 6] 为Mini-Service 初始化数据库，编译、测试、打包、部署到tomcat，并启动IE浏览。
cd examples\mini-service
call %MAVEN_BAT% -o clean package cargo:redeploy -Pinitdb -Pnotest
explorer http://localhost:8080/mini-service

echo [Step 7] 为Mini-Web 初始化数据库，编译、测试、打包、部署到tomcat，并启动IE浏览。
cd ..\mini-web
call %MAVEN_BAT% -o clean package cargo:redeploy -Pinitdb -Pnotest
explorer http://localhost:8080/mini-web

echo [Step 8] 为Showcase 初始化数据库，编译、测试、打包、部署到tomcat，并启动IE浏览。
cd ..\showcase
call %MAVEN_BAT% -o clean package cargo:redeploy -Pinitdb -Pnotest
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
:end
pause


