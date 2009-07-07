java -cp  ..\..\lib\emma-2.0.5312.jar emma report -r html -sp ../../src/main/java/ -in coverage.em,coverage.ec -Dreport.sort=+name,+block,+method,+class
pause