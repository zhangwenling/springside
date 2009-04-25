@echo off
echo [INFO] 从maven仓库复制jar到所有项目目录.

cd ..\
call mvn  integration-test -Pexamples
pause
