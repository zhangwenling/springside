@echo off
echo [INFO] 确保设置CXF_HOME系统变量到cxf下载目录.
echo [INFO] 确保设置JAVA_HOME系统变量到JDK5.0以上的JDK目录.
echo [INFO] 确保本地WebService应用已启动.

call "%CXF_HOME%/bin/wsdl2java.bat" -client -b build-client-binding.xml -exsh true http://localhost:8080/${artifactId}/services/UserService?wsdl

echo [INFO] 代码已生成在当前目录下.
pause