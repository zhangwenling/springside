@echo off
echo [INFO] 请保证已安装springside archetypes.

if exist generated-project (rmdir /s/q generated-project)
mkdir generated-project
cd generated-project

call mvn archetype:generate -DarchetypeCatalog=local

echo [INFO] 已在%cd%下生成项目.

pause