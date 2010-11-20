package org.springside.examples.showcase.unit.solr;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.dao.UIDGenerator;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.solr.PostQuery;
import org.springside.examples.showcase.solr.PostSolrDao;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/solr/applicationContext-solr.xml" })
public class PostSolrDaoTest extends SpringContextTestCase {

	@Autowired
	private PostSolrDao postSolrDao;

	@Test
	public void crudPost() throws Exception {

		Post post = new Subject();
		post.setId(new UIDGenerator().generate());
		post.setTitle("test post");
		User user = new User();
		user.setLoginName("calvin");
		post.setUser(user);
		
		postSolrDao.savePost(post);
		
		PostQuery postQuery = new PostQuery();
		postQuery.setKeywords("test");
		List<Post> result = postSolrDao.queryPost(postQuery, 10);
		assertEquals(1,result.size());
		assertEquals("test post",result.get(0).getTitle());
		
	}
}
