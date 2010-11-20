package org.springside.examples.showcase.unit.solr;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.examples.showcase.data.PostData;
import org.springside.examples.showcase.solr.PostQueryBuilder;
import org.springside.examples.showcase.solr.PostQueryBuilder.TimeScope;
import org.springside.examples.showcase.solr.PostSolrDao;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/solr/applicationContext-solr.xml" })
public class PostSolrDaoTest extends SpringContextTestCase {

	@Autowired
	private PostSolrDao postSolrDao;

	@Test
	public void crudPost() throws Exception {

		Post post = PostData.getDefaultPost();

		postSolrDao.savePost(post);

		PostQueryBuilder postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setKeywords("test");
		List<Post> result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(1, result.size());
		assertEquals(post.getTitle(), result.get(0).getTitle());
		
		postSolrDao.deletePost(postQueryBuilder.buildQueryString());
		result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(0, result.size());
	}
	
	@Test
	public void queryPost() throws IOException, SolrServerException{
		Post post = PostData.getDefaultPost();
		DateTime twoMonthBefore = new DateTime().minusMonths(2);
		post.setModifyTime(twoMonthBefore.toDate());
		postSolrDao.savePost(post);
	
		//查询标题
		PostQueryBuilder postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setKeywords("test");
		List<Post> result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(1, result.size());
		
		//查询标题与内容
		postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setKeywords("content");
		postQueryBuilder.setIncludeContent(true);
		result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(1, result.size());
	
		
		//查询时间范围
		postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setTimeScope(TimeScope.ONE_MONTH);
		result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(0, result.size());
		
		postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setTimeScope(TimeScope.ONE_YEAR);
		result = postSolrDao.queryPost(postQueryBuilder.buildQueryString(), 10);
		assertEquals(1, result.size());
		
	}
	
	@After
	public void clean() throws SolrServerException, IOException{
		postSolrDao.deletePost("*:*");
	}
}
