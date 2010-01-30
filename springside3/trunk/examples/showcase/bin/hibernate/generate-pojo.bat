@echo off
echo [INFO] Generate entity from database to %cd%\generated dir.
echo [INFO] Make sure maven's ant task jar in ant's classpath.
call ant generate.pojo
echo [INFO] Artifactss will generate  in %cd%\generated dir.

pause