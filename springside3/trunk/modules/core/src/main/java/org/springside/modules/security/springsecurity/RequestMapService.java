package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;

/**
 * RequestMap生成接口,用户可自行实现,从数据库或其它地方查询URL-授权关系定义.
 * 
 * @author calvin
 */
public interface RequestMapService {

	/**
	 * 获取RequestMap.
	 * 
	 * Map中的key为URL, value为以能访问该URL的以','分隔的授权列表.
	 */
	LinkedHashMap<String, String> getRequestMap() throws Exception;
}
