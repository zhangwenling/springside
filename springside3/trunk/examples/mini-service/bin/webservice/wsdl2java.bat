@echo off

cd %~dp0/../../
call mvn cxf-codegen:wsdl2java

echo [INFO] 代码已生成到target/generated-sources/cxf目录下.
pause