@echo off
echo [INFO] Use maven sql-plugin create schema, maven dbunit-plugin import default data.

cd %~dp0
cd ..
call mvn sql:execute dbunit:operation
cd bin
pause