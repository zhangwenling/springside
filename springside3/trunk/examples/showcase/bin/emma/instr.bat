java -cp  ..\..\lib\emma-2.0.5312.jar emma instr -m overwrite  -cp  ..\..\webapp\WEB-INF\classes\
copy coverage.em  ..\..\webapp\WEB-INF\classes\coverage.em
copy ..\..\lib\emma-2.0.5312.jar ..\..\webapp\WEB-INF\lib\emma-2.0.5312.jar

pause