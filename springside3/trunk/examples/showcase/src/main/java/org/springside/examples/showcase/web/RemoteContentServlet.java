package org.springside.examples.showcase.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int TOTAL_CONNECTIONS = 50;
	private static Logger logger = LoggerFactory.getLogger(RemoteContentServlet.class);
	private HttpClient httpClient = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		HttpGet httpget = new HttpGet(url);
		HttpContext context = new BasicHttpContext();
		try {
			HttpResponse remoteResponse = httpClient.execute(httpget, context);
			HttpEntity entity = remoteResponse.getEntity();
			if (entity != null) {
				InputStream input = entity.getContent();
				OutputStream output = response.getOutputStream();

				//设置Header
				response.setContentType(entity.getContentType().getValue());
				if(entity.getContentLength()>0){
					response.setContentLength((int)entity.getContentLength());
				}
				
				try {
					//基于byte数组读取文件并直接写入OutputStream, 数组默认大小为4k.
					IOUtils.copy(input, output);
					output.flush();
				} finally {
					//保证Input/Output Stream的关闭.
					IOUtils.closeQuietly(input);
					IOUtils.closeQuietly(output);
				}
			}
		} catch (Exception e) {
			logger.error("fetch remote content" + url + "  error", e);
			httpget.abort();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// Create and initialize HTTP parameters
		HttpParams params = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(params, TOTAL_CONNECTIONS);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		// Create and initialize scheme registry 
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
		httpClient = new DefaultHttpClient(cm, params);
	}

	@Override
	public void destroy() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
}
