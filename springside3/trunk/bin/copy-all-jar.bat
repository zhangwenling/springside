@echo off
echo [INFO] 从maven仓库复制jar到所有项目目录.

cd ..\
call mvn -o dependency:copy-dependencies -DoutputDirectory=lib -Pmodules
call mvn -o dependency:copy-dependencies -DoutputDirectory=lib -DexcludeScope=runtime -Dsilent=true -Pexamples
call mvn -o dependency:copy-dependencies -DoutputDirectory=webapp/WEB-INF/lib -DincludeScope=runtime -Dsilent=true -Pexamples

pause
