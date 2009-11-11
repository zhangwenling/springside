@echo off
echo [INFO] 确保默认JDK版本为JDK5.0.

if exist "..\tools\tomcat\apache-tomcat-6.0.20\" goto begin
echo [ERROR] ..\tools\tomcat\apache-tomcat-6.0.20目录不存在，请下载all-in-one版本
goto end

:begin
set MAVEN_BAT="%cd%\..\tools\maven\apache-maven-2.2.1\bin\mvn.bat"
cd ..\

echo [Step 1] 复制tools/maven/central-repository 到 %userprofile%\.m2\repository
xcopy /s/e/i/h/d/y "tools\maven\central-repository" "%USERPROFILE%\.m2\repository"

echo [Step 2] 执行tools/h2/h2w.bat 启动H2数据库.
cd tools\h2
call h2w.bat
cd ..\..\

echo [Step 3] 执行tools/tomcat/apache-tomcat-6.0.20/bin/startup.bat 启动Tomcat服务器.
cd  tools\tomcat\apache-tomcat-6.0.20\bin\
start startup.bat
cd ..\..\..\..\

echo [Step 4] 为所有项目初始化依赖Jar包到lib及webapp/WEB-INF/lib目录.
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -Dsilent=true -Pmodules
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true -Pexamples
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib -DincludeScope=runtime -Dsilent=true -Pexamples

echo [Step 5] 安装SpringSide3 modules 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% -o clean install  -Pmodules -Dmaven.test.skip=true
 
echo [Step 6] 为Mini-Service 初始化数据库、编译、打包.
cd examples\mini-service
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true

echo [Step 7] 为Mini-Web 初始化数据库、编译、打包.
cd ..\mini-web
call %MAVEN_BAT% -o clean package  -Pinitdb -Dmaven.test.skip=true

echo [Step 8] 为Showcase 初始化数据库、编译、打包.
cd ..\showcase
call %MAVEN_BAT% -o clean package  -Pinitdb -Dmaven.test.skip=true

echo [Step 9] 部署3个示例项目到tomcat，并启动浏览器浏览.
cd ..\..
call  %MAVEN_BAT% -o cargo:redeploy
explorer http://localhost:8080/mini-service
explorer http://localhost:8080/mini-web
explorer http://localhost:8080/showcase

echo [INFO] SpringSide3.0 快速启动完毕.
:end
pause


