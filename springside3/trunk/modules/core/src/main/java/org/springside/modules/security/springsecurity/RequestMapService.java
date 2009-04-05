package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;

/**
 * RequestMap生成接口,用户可自行实现,从数据库等处查询URL及URL-授权关系.
 * 
 * @author calvin
 */
public interface RequestMapService {

	/**
	 * 返回字符串形式的RequestMap.
	 * Map中key为url, value为','分隔的授权列表.
	 */
	LinkedHashMap<String, String> getRequestMap() throws Exception;
}
