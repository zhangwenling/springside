package org.springside.examples.showcase.unit.solr;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springside.examples.showcase.solr.PostQueryBuilder;
import org.springside.examples.showcase.solr.PostQueryBuilder.TimeScope;

public class PostQueryBuilderTest {

	@Test
	public void testAll() {
		//标题
		PostQueryBuilder postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setKeywords("test");
		assertEquals("title:(test)", postQueryBuilder.buildQueryString());

		//标题与内容
		postQueryBuilder.setKeywords("content");
		postQueryBuilder.setIncludeContent(false);
		assertEquals("title:(content)", postQueryBuilder.buildQueryString());

		postQueryBuilder.setKeywords("content");
		postQueryBuilder.setIncludeContent(true);
		assertEquals("title:(content) OR content:(content)", postQueryBuilder.buildQueryString());

		//用户名
		postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setKeywords("test");
		postQueryBuilder.setAuthorName("calvin");
		assertEquals("title:(test) AND authorName:(calvin)", postQueryBuilder.buildQueryString());

		postQueryBuilder = new PostQueryBuilder();
		postQueryBuilder.setAuthorName("calvin");
		assertEquals("authorName:(calvin)", postQueryBuilder.buildQueryString());

		//时间
		postQueryBuilder = new PostQueryBuilder();

		postQueryBuilder.setTimeScope(TimeScope.ALL);
		assertEquals("", postQueryBuilder.buildQueryString());

		postQueryBuilder.setTimeScope(TimeScope.ONE_MONTH);
		assertEquals("modifyTime:[NOW-1MONTH TO *]", postQueryBuilder.buildQueryString());

		postQueryBuilder.setTimeScope(TimeScope.ONE_YEAR);
		assertEquals("modifyTime:[NOW-1YEAR TO *]", postQueryBuilder.buildQueryString());
	}

}
