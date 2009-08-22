package org.springside.examples.showcase.integration.common;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.ReplyDao;
import org.springside.examples.showcase.common.dao.SubjectDao;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.Reply;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.examples.showcase.common.entity.User;
import org.springside.modules.test.spring.SpringTxTestCase;

public class PostDaoTest extends SpringTxTestCase {

	@Autowired
	private SubjectDao subjectDao;
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private UserDao userDao;

	@Test
	public void getSubjectDetail() {
		Subject subject = subjectDao.getDetailWithReply(1L);
		assertEquals(1, subject.getReplys().size());
		assertEquals("Hello World!!", subject.getContent());
	}

	@Test
	public void newSubject() {
		Subject subject = new Subject();
		subject.setTitle("Good Night");
		subject.setContent("Good Night!!");
		subject.setModifyTime(new Date());

		User user = userDao.get(1L);
		subject.setUser(user);

		subjectDao.save(subject);
		flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Night!!", subject.getContent());
	}

	@Test
	public void editSubject() {
		Subject subject = subjectDao.getDetail(1L);
		subject.setTitle("Good Afternoon");
		subject.setContent("Good Afternoon!!!");
		subject.setModifyTime(new Date());

		subjectDao.save(subject);
		flush();
		subject = subjectDao.getDetail(subject.getId());
		assertEquals("Good Afternoon!!!", subject.getContent());
	}

	@Test
	public void deleteSubject() {
		subjectDao.delete(1L);
		flush();
		Subject subject = subjectDao.findUniqueBy("id", 1L);
		assertNull(subject);
	}

	@Test
	public void newReply() {
		Reply reply = new Reply();
		reply.setTitle("GoodAfternoon");
		reply.setContent("Good Afternoon!!!");
		reply.setModifyTime(new Date());

		User user = userDao.get(1L);
		reply.setUser(user);

		Subject subject = subjectDao.get(1L);
		reply.setSubject(subject);

		replyDao.save(reply);
		flush();
	}

	@Test
	public void editReply() {
		Reply reply = replyDao.getDetail(2L);
		reply.setTitle("GoodEvening");
		reply.setContent("Good Evening!!!");

		replyDao.save(reply);
		flush();
	}

	@Test
	public void deleteReply() {
		replyDao.delete(2L);
	}
}
