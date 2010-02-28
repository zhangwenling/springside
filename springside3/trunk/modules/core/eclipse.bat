@echo off
echo [INFO] Use maven eclipse plugin download jar and generate eclipse project files.
echo [INFO] Add "-Declipse.workspace=<path-to-eclipse-workspace>"

call mvn eclipse:clean eclipse:eclipse

pause