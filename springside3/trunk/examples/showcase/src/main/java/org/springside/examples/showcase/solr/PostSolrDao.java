package org.springside.examples.showcase.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.showcase.common.entity.Post;
import org.springside.modules.solr.MultiCoreSolrServerFactory;

public class PostSolrDao {

	private MultiCoreSolrServerFactory solrServerFactory;

	private boolean autoCommit;

	public void savePost(Post post) throws IOException, SolrServerException {
		Assert.hasText(post.getId());

		PostSolrWrapper postWrapper = new PostSolrWrapper();
		postWrapper.setEntity(post);

		SolrServer server = solrServerFactory.getServerByHash(post.getId());
		server.addBean(postWrapper);

		if (autoCommit) {
			server.commit();
		}
	}

	public void findPost() {

	}

	@Autowired
	public void setSolrServerFactory(MultiCoreSolrServerFactory solrServerFactory) {
		this.solrServerFactory = solrServerFactory;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

}
