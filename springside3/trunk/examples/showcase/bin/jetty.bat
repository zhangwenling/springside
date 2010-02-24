@echo off
echo [INFO] Use Maven jetty plugin run the project.

cd ..
call mvn jetty:run -Dmaven.test.skip=true