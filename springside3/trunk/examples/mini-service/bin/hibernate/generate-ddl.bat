@echo off
echo [INFO] Generate database schema from entity defined to %cd%\generated dir.
echo [INFO] Make sure maven's ant task jar in ant's class path.
call ant generate.ddl
echo [INFO] Artifact will generate  in %cd%\generated dir.
pause