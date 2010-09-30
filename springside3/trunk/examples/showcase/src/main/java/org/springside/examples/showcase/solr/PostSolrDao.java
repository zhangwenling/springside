package org.springside.examples.showcase.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springside.examples.showcase.common.entity.Post;

public class PostSolrDao {

	private SolrServer solrServer;

	public void savePost(Post post) throws IOException, SolrServerException {

		Assert.hasText(post.getId());

		PostSolrWrapper postWrapper = new PostSolrWrapper();
		postWrapper.setEntity(post);

		solrServer.addBean(postWrapper);
		solrServer.commit();
	}

	@Autowired
	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = solrServer;
	}

}
