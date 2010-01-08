package org.springside.modules.security.springsecurity;

import java.util.Vector;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.ws.security.WSConstants;
import org.apache.ws.security.WSSecurityEngineResult;
import org.apache.ws.security.WSUsernameTokenPrincipal;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.apache.ws.security.handler.WSHandlerResult;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;

public class WSS4JSpringSecurityInInterceptor extends WSS4JInInterceptor {
	
	UserDetailsService userDetailsService;
	
	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void handleMessage(SoapMessage message) {

		super.handleMessage(message);

		String userName = getUserNameFromSecurityResult(message);
		
		UserDetails userDetails = findUserDetails(userName);

		buildSpringSecurityContext(userDetails);
	}

	@SuppressWarnings("unchecked")
	private String getUserNameFromSecurityResult(SoapMessage message) {
		Vector<WSHandlerResult> results = (Vector<WSHandlerResult>) message
				.getContextualProperty(WSHandlerConstants.RECV_RESULTS);
		if (results != null && !results.isEmpty()) {
			for (WSHandlerResult result : results) {
				for (WSSecurityEngineResult securityResult : (Vector<WSSecurityEngineResult>) result.getResults()) {
					int action = (Integer) securityResult.get(WSSecurityEngineResult.TAG_ACTION);
					if ((action & WSConstants.UT) > 0) {
						WSUsernameTokenPrincipal token = (WSUsernameTokenPrincipal) securityResult
								.get(WSSecurityEngineResult.TAG_PRINCIPAL);
						return token.getName();
					}
				}
			}
		}
		return null;
	}
	
	private UserDetails findUserDetails(String userName){
		return userDetailsService.loadUserByUsername(userName);
	}
	
	private void buildSpringSecurityContext(UserDetails userDetails) {

		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails
				.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
