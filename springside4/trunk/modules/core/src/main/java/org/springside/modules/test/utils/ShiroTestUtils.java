package org.springside.modules.test.utils;

import static org.easymock.EasyMock.*;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;

public class ShiroTestUtils {

	private static ThreadState threadState;

	public static void bindSubject(Subject subject) {
		clearSubject();
		threadState = new SubjectThreadState(subject);
		threadState.bind();
	}

	public static void mockSubject() {
		Subject subject = createNiceMock(Subject.class);
		expect(subject.isAuthenticated()).andReturn(true);
		expect(subject.getPrincipal()).andReturn("mockUser");
		replay(subject);

		bindSubject(subject);
	}

	public static void clearSubject() {
		if (threadState != null) {
			threadState.clear();
			threadState = null;
		}
	}
}
