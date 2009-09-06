@echo off
echo [INFO] 使用maven jetty plugin 运行项目在node1节点8080端口.

cd ..
call mvn jetty:run-war -Pnotest -Djetty.port=8080 -Dcluster.nodename=node1

pause