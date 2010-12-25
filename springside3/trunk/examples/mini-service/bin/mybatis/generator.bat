@echo off

cd %~dp0/../../
call mvn mybatis-generator:generate

echo [INFO] 代码已生成到target/generated-sources/mybatis-generator目录下.
pause