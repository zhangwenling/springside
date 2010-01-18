@echo off
echo [INFO] 使用maven eclipse plugin 下载所需jar并生成Eclipse项目文件.

cd ..
call mvn eclipse:eclipse
pause