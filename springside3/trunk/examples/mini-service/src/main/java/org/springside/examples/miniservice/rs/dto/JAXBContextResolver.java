package org.springside.examples.miniservice.rs.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;


import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

@Provider
@SuppressWarnings("unchecked")
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	private JAXBContext context;

	private final Set<Class> typeSet;

	private Class[] types = { RoleDTO.class, UserDTO.class };

    public JAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
		this.typeSet = new HashSet(Arrays.asList(types));
	}

	public JAXBContext getContext(Class<?> objectType) {
		return (typeSet.contains(objectType)) ? context : null;
	}
}
