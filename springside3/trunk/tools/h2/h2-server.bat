@echo off
call mvn exec:java -Pserver
cd bin
pause