package org.springside.examples.showcase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springside.modules.web.WebUtils;

/**
 * 本地静态内容展示与下载的Servlet.
 * 
 * 使用Ehcache静态内容, 并对内容进行缓存控制及gzip压缩。
 * 
 * @author calvin
 */
public class ContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Cache contentCache;

	private String[] gzipMimeTypes = { "text/html", "application/xhtml+xml", "text/css", "text/javascript" };
	private int gzipMiniLength = 512;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求内容的元数据.
		String path = request.getParameter("path");
		Content content = getContentFromCache(path);

		//判断客户端的缓存文件有否修改过, 如无修改则设置返回码为304,直接返回.
		if (!WebUtils.checkIfModifiedSince(request, response, content.lastModified)
				|| !WebUtils.checkIfNoneMatchEtag(request, response, content.etag)) {
			return;
		}

		//设置MIME类型
		response.setContentType(content.mimeType);

		//设置Etag/过期时间
		WebUtils.setExpiresHeader(response, WebUtils.ONE_YEAR_SECONDS);
		WebUtils.setLastModifiedHeader(response, content.lastModified);
		WebUtils.setEtag(response, content.etag);

		//如果是下载请求,设置下载Header
		if (request.getParameter("download") != null) {
			WebUtils.setDownloadableHeader(response, content.fileName);
		}

		//发送文件内容, 对有需要的内容进行压缩传输.
		OutputStream output;
		if (WebUtils.checkAccetptGzip(request) && content.needGzip) {
			//不设置content-length, 使用http1.1 trunked编码.
			//使用压缩传输的outputstream.
			output = WebUtils.buildGzipOutputStream(response);
		} else {
			//为http1.0客户端设置content-length.
			response.setContentLength(content.length);
			//使用普通outputstream.
			output = response.getOutputStream();
		}

		FileInputStream input = new FileInputStream(content.file);
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

	@Override
	public void init(ServletConfig config) throws ServletException {
		contentCache = (Cache) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(
				"contentCache");
	}

	/**
	* 从缓存中获取Content基本信息.
	*/
	private Content getContentFromCache(String path) {
		Element element = contentCache.get(path);
		if (element == null) {
			Content content = createContent(path);
			element = new Element(content.path, content);
			contentCache.put(element);
		}
		return (Content) element.getObjectValue();
	}

	/**
	 * 创建Content基本信息.
	 */
	private Content createContent(String path) {
		Content content = new Content();

		String realFilePath = getServletContext().getRealPath(path);
		File file = new File(realFilePath);
		content.path = path;
		content.file = file;

		content.fileName = file.getName();
		content.lastModified = file.lastModified();
		content.length = (int) file.length();
		content.etag = "W/\"" + file.lastModified() + "\"";

		String mimeType = getServletContext().getMimeType(realFilePath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		content.mimeType = mimeType;

		if (content.length >= gzipMiniLength && ArrayUtils.contains(gzipMimeTypes, content.mimeType)) {
			content.needGzip = true;
		} else {
			content.needGzip = false;
		}

		return content;
	}

	/**
	 * 定义Content的基本信息.
	 */
	private static class Content {
		String path;
		File file;
		String fileName;
		int length;
		String mimeType;
		long lastModified;
		String etag;
		boolean needGzip;
	}
}
