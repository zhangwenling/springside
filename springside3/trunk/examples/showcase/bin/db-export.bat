@echo off
echo [INFO] Use maven dbunit-plugin export data from local database.

cd %~dp0
cd ..
call mvn dbunit:export
cd bin
pause