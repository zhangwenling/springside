@echo off
echo [INFO] 确保本地WebService应用已启动.

cd %~dp0
call mvn -f build-client-pom.xml cxf-codegen:wsdl2java

echo [INFO] 代码已生成到target/generated/目录下.
pause