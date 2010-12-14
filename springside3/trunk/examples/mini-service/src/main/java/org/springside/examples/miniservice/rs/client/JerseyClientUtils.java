package org.springside.examples.miniservice.rs.client;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyClientUtils {

	/**
	 * 创建JerseyClient, 设定JSON字符串使用Jackson解析.
	 */
	public static WebResource createClient(String baseUrl) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getClasses().add(JacksonJsonProvider.class);
		Client jerseyClient = Client.create(cc);
		return jerseyClient.resource(baseUrl);
	}
}
