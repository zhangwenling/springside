package org.springside.examples.miniservice.rs.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

/**
 * 设置JSON格式为NATURAL的Provider.
 * 
 * @author calvin
 *
 */
@Provider
@Component
@SuppressWarnings("unchecked")
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	/** 需要被natural格式输出的类. */
	private Class[] types = { RoleDTO.class, UserDTO.class };

	private JAXBContext context;
	private final Set<Class> typeSet;

	public JAXBContextResolver() {
		try {
			this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		this.typeSet = new HashSet(Arrays.asList(types));
	}

	public JAXBContext getContext(Class<?> objectType) {
		return (typeSet.contains(objectType)) ? context : null;
	}
}
