#!/bin/sh
echo "[INFO] 使用maven sql plugin 初始化数据库."

cd ..
mvn initialize -Pinitdb