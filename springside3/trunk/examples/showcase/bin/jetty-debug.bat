@echo off
echo [INFO] Use Maven jetty plugin run the project, with debug option.

cd ..
set MAVEN_OPTS=%MAVEN_OPTS% -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
call mvn jetty:run -Dmaven.test.skip=true
cd bin
pause