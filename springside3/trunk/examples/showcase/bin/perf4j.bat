@echo off
echo [INFO] Generate Perf4j Report

java -jar ../webapp/WEB-INF/lib/perf4j-0.9.11.jar c:/perf4j.log>perf4j-data.txt
java -jar ../webapp/WEB-INF/lib/perf4j-0.9.11.jar c:/perf4j.log --graph perfj4-graph.htm