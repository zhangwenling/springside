1.catalina.bat/sh:  JAVA_OPTS add -XX:MaxPermSize=96m
2.context.xml: <Context> node add antiJARLocking="true" antiResourceLocking="true" property to avoid jar loking when redeploy
3.tomcat-users.xml: add default tomcat user for cargo remote deploy.
4.server.xml:add useBodyEncodingForURI="true" setting.