#!/bin/sh
echo "[INFO] 以network server模式启动servers/derby中的Derby数据库."

set CLASSPATH=$CLASSPATH:lib/derby.jar:lib/derbynet.jar
java org.apache.derby.drda.NetworkServerControl start