@echo off
echo [INFO] ʹ��maven jetty plugin ������Ŀ.

cd ..
call mvn jetty:run -Dmaven.test.skip=true

pause