@echo off
echo [INFO] 确保默认JDK版本为JDK5.0及以上版本.

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

echo [Step 3] 安装SpringSide3 modules 和archetypes到 本地Maven仓库.
call %MAVEN_BAT% -o clean install  -Pmodules -Dmaven.test.skip=true

echo [Step 4] 为Mini-Service 初始化数据库、编译、打包.
cd examples\mini-service
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
cd ..\..\

echo [Step 5] 为Mini-Web 初始化数据库、编译、打包.
cd examples\mini-web
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
cd ..\..\

echo [Step 6] 为Showcase 初始化数据库、编译、打包.
cd examples\showcase
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
cd ..\..\

echo [Step 7] 部署3个示例项目到tomcat，启动tomcat.
call %MAVEN_BAT% cargo:deploy

echo [Step 8] 为所有项目初始化依赖Jar包到lib及webapp/WEB-INF/lib目录,方便浏览
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -Dsilent=true -Pmodules
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true -Pexamples
call %MAVEN_BAT% -o dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib -DincludeScope=runtime -Dsilent=true -Pexamples

echo [INFO] SpringSide3.0 快速启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8080/mini-service
echo [INFO] http://localhost:8080/mini-web
echo [INFO] http://localhost:8080/showcase

start http://localhost:8080/mini-service
start http://localhost:8080/mini-web
start http://localhost:8080/showcase

:end
pause


