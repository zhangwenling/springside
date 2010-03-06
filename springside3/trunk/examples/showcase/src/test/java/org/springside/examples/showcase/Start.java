package org.springside.examples.showcase;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * 使用Jetty运行调试Web应用, 在Console输入回车停止服务.
 * 
 * @author calvin
 */
public class Start {

	public static final int PORT = 8080;
	public static final String CONTEXT = "/showcase";
	public static final String BASE_URL = "http://localhost:8080/showcase";

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);
		WebAppContext webContext = new WebAppContext("src/main/webapp", CONTEXT);
		webContext.setClassLoader(Thread.currentThread().getContextClassLoader());

		server.setHandler(webContext);
		server.setStopAtShutdown(true);
		server.start();

		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			System.out.println("Server stopping");
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
