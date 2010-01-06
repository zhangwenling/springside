package org.springside.examples.showcase.functional.webservice.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.ws.client.PasswordCallback;
import org.springside.examples.showcase.ws.server.api.UserWebService;
import org.springside.examples.showcase.ws.server.api.WsConstants;
import org.springside.examples.showcase.ws.server.api.result.GetAllUserResult;
import org.springside.modules.test.spring.SpringContextTestCase;

import com.google.common.collect.Maps;

/**
 * WS-Security 测试.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/webservice/applicationContext-cxf-client.xml" }, inheritLocations = false)
public class SecurityWebServiceTest extends SpringContextTestCase {

	/**
	 * 测试明文密码, 在Spring中用<jaxws:client/>创建的Client.
	 */
	@Test
	public void getAllUserWithPlainPassword() {
		UserWebService userWebService = (UserWebService) applicationContext.getBean("userServiceWithPlainPassword");
		GetAllUserResult result = userWebService.getAllUser();
		assertTrue(result.getUserList().size() > 0);
	}

	/**
	 * 测试Digest密码认证, 使用JAXWS的API自行创建Client.
	 */
	@Test
	public void getAllUserWithDigestPassword() throws MalformedURLException {

		//创建UserWebService
		URL wsdlURL = new URL("http://localhost:8080/showcase/services/UserServiceWithDigestPassword?wsdl");
		QName UserServiceName = new QName(WsConstants.NS, "UserService");
		Service service = Service.create(wsdlURL, UserServiceName);
		UserWebService userWebService = service.getPort(UserWebService.class);

		//定义WSS4JOutInterceptor
		Map<String, Object> outProps = Maps.newHashMap();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, PasswordCallback.class.getName());
		WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProps);

		Client client = ClientProxy.getClient(userWebService);
		Endpoint endPoint = client.getEndpoint();
		endPoint.getOutInterceptors().add(wssOut);

		//调用UserWebService
		GetAllUserResult result = userWebService.getAllUser();
		assertTrue(result.getUserList().size() > 0);
	}
}
