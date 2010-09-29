package org.springside.examples.showcase.unit.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.dao.UIDGenerator;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.examples.showcase.common.entity.Subject;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.solr.PostSolrDao;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/solr/applicationContext-solr.xml" })
public class PostSolrDaoTest extends SpringContextTestCase {

	@Autowired
	private PostSolrDao postSolrDao;

	@Test
	public void savePost() throws IOException, SolrServerException {
		Post post = new Subject();
		post.setId(new UIDGenerator().generate());
		User user = new User();
		user.setLoginName("calvin");
		post.setUser(user);

		postSolrDao.setAutoCommit(true);
		postSolrDao.savePost(post);

	}

}
