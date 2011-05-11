/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Fixtures.java 1593 2011-05-11 10:37:12Z calvinxiu $
 */
package org.springside.modules.test.utils;

import static org.easymock.EasyMock.*;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;

public class ShiroTestUtils {

	private static ThreadState threadState;

	/**
	 * 綁定Subject到當前線程.
	 */
	public static void bindSubject(Subject subject) {
		clearSubject();
		threadState = new SubjectThreadState(subject);
		threadState.bind();
	}

	/**
	 * 用EasyMock快速創建一個已認證的, 用戶名為mockUser的用戶.
	 */
	public static void mockSubject() {
		Subject subject = createNiceMock(Subject.class);
		expect(subject.isAuthenticated()).andReturn(true);
		expect(subject.getPrincipal()).andReturn("mockUser");
		replay(subject);

		bindSubject(subject);
	}

	/**
	 * 清除當前線程中的Subject.
	 */
	public static void clearSubject() {
		if (threadState != null) {
			threadState.clear();
			threadState = null;
		}
	}
}
