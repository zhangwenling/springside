@echo off
echo [INFO] 安装SpringSide的所有modules和archetypes.
cd ..\
call mvn clean install  -Pmodules
pause
