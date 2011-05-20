@echo off
echo Please download the yuicompressor from http://yuilibrary.com/downloads/#yuicompressor and extract the jar file here.

java -jar yuicompressor-2.4.6.jar -o ..\src\main\webapp\static\showcase.min.css ..\src\main\webapp\static\showcase.css

pause