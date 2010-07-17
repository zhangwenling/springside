/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.utils;

/**
 * 线程相关的测试Utils函数.
 * 
 * @author calvin
 */
public class ThreadUtils {

	/**
	 * sleep等待,单位毫秒.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {//NOSONAR
		}
	}
}
