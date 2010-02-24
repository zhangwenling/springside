@echo off
echo [INFO] Use maven eclipse plugin download jar and generate eclipse project files.
echo [Info] If your eclipse files not in default place, add -Declipse.workspace=<path-to-eclipse-workspace> 

cd ..
call mvn eclipse:clean eclipse:eclipse
cd bin
pause