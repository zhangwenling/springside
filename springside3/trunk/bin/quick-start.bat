@echo off
echo [INFO] 确保默认JDK版本为JDK5.0及以上版本.

if exist "..\tools\maven\apache-maven-2.2.1\" goto begin
echo [ERROR] ..\tools\maven\apache-maven-2.2.1目录不存在，请下载all-in-one版本
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

echo [Step 3] 安装SpringSide3 modules 和archetypes到 本地Maven仓库,生成Eclipse项目文件.
call %MAVEN_BAT% -o eclipse:clean eclipse:eclpse
call %MAVEN_BAT% -o clean install -Dmaven.test.skip=true

echo [Step 4] 为Mini-Service 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\mini-service
call %MAVEN_BAT% -o eclipse:clean eclipse:eclpse
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
start "Mini-Service" %MAVEN_BAT% -o -Djetty.port=8080 jetty:run
cd ..\..\

echo [Step 5] 为Mini-Web 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\mini-web
call %MAVEN_BAT% -o eclipse:clean eclipse:eclpse
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
start "Mini-Web" %MAVEN_BAT% -o -Djetty.port=8084 jetty:run
cd ..\..\

echo [Step 6] 为Showcase 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\showcase
call %MAVEN_BAT% -o eclipse:clean eclipse:eclpse
call %MAVEN_BAT% -o clean package -Pinitdb -Dmaven.test.skip=true
start "Showcase" %MAVEN_BAT% -o -Djetty.port=9091 jetty:run
cd ..\..\

echo [INFO] SpringSide3.0 快速启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8080/mini-service
echo [INFO] http://localhost:8084/mini-web
echo [INFO] http://localhost:8088/showcase

:end
pause


