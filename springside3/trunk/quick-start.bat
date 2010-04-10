@echo off
echo [INFO] 确保默认JDK版本为JDK5.0及以上版本.
echo [INFO] 确保Maven的可执行命令在PATH内.
echo [INFO] 确保Ant的可执行命令在PATH内, 并已将maven-ant-task.jar放入Ant的lib中.

rem set OFF_LINE=-o

echo [Step 1] 复制tools/maven/central-repository 到 %userprofile%\.m2\repository
xcopy /s/e/i/h/d/y "tools\maven\central-repository" "%USERPROFILE%\.m2\repository"

echo [Step 2] 启动H2数据库.
cd tools/h2
call mvn %OFF_LINE% initialize -Pstartdb
cd ..\..\

echo [Step 3] 安装SpringSide3 所有modules,examples项目到本地Maven仓库,生成Eclipse项目文件.
call mvn %OFF_LINE% eclipse:clean eclipse:eclipse
call mvn %OFF_LINE% clean install -Dmaven.test.skip=true

echo [Step 4] 为Mini-Service 初始化数据库, 启动Jetty.
cd examples\mini-service
call ant init-db 
start "Mini-Service" mvn %OFF_LINE% -Djetty.port=8080 jetty:run
cd ..\..\

echo [Step 5] 为Mini-Web 初始化数据库, 启动Jetty.
cd examples\mini-web
call ant init-db 
start "Mini-Web" mvn %OFF_LINE% -Djetty.port=8082 jetty:run
cd ..\..\

echo [Step 6] 为Showcase 生成Eclipse项目文件, 编译, 打包, 初始化数据库, 启动Jetty.
cd examples\showcase
call ant init-db
start "Showcase" mvn %OFF_LINE% -Djetty.port=8083 jetty:run
cd ..\..\

echo [INFO] SpringSide3.0 快速启动完毕.
echo [INFO] 可访问以下演示网址:
echo [INFO] http://localhost:8080/mini-service
echo [INFO] http://localhost:8082/mini-web
echo [INFO] http://localhost:8083/showcase

:end
pause