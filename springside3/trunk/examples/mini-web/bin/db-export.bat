@echo off
echo [INFO] 使用dbunit导出测试数据.

cd ..
call mvn db:export
pause