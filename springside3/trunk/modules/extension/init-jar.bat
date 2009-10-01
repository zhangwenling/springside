@echo off

echo [INFO] 使用maven根据pom.xml 复制依赖jar到/lib
call mvn dependency:copy-dependencies -DoutputDirectory=lib -Dsilent=true

pause