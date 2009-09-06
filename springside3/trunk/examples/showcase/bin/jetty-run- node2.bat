@echo off
echo [INFO] 使用maven jetty plugin 运行项目.

cd ..
call mvn jetty:run-war -Pnotest -Pcluster -Djetty.port=8081 -Dcluster.nodename=node2

pause