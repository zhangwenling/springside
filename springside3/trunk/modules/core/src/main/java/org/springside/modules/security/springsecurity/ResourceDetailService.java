/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.security.springsecurity;

import java.util.LinkedHashMap;

/**
 * RequestMap生成接口,由用户自行实现从数据库或其它地方查询URL-授权关系定义.
 * 
 * @author calvin
 */
public interface ResourceDetailService {

	/**
	 * 获取RequestMap.
	 * 
	 * Map中的key为URL, value为能访问该URL的以','分隔的授权列表.
	 */
	LinkedHashMap<String, String> getRequestMap() throws Exception;

}
