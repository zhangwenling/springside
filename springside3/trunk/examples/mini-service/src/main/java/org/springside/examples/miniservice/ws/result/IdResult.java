package org.springside.examples.miniservice.ws.result;

import javax.xml.bind.annotation.XmlType;

import org.springside.examples.miniservice.WsConstants;
/**
 * 创建某个对象返回的 通用IdResult
 * 
 * @author badqiu
 *
 */
@XmlType(name = "IdResult", namespace = WsConstants.NS)
public class IdResult extends WSResult{
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
