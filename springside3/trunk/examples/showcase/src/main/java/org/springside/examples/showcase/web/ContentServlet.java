package org.springside.examples.showcase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springside.modules.web.WebUtils;

public class ContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAMETER_PATH = "path";
	private static final String PARAMETER_DOWNLOAD = "download";

	private static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	private Map<String, Content> contentCache = new ConcurrentHashMap<String, Content>();
	private String[] gzipMimeTypes = { "text/html", "application/xhtml+xml", "text/css", "text/javascript" };
	private int gzipMiniLength = 512;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter(PARAMETER_PATH);
		Content content = getContentFromCache(path);

		//判断客户端的缓存文件有否修改过, 如无修改则设置返回码为304,直接返回.
		if (!WebUtils.checkIfModified(request, response, content.lastModified)
				|| !WebUtils.checkIfNoneMatch(request, response, content.etag)) {
			return;
		}

		//设置MIME类型
		response.setContentType(content.mimeType);

		//设置Etag/过期时间
		WebUtils.setExpiresHeader(response, ONE_YEAR_SECONDS);
		WebUtils.setLastModifiedHeader(response, content.lastModified);
		response.setHeader("ETag", content.etag);

		//如果是下载请求,设置下载Header
		if (request.getParameter(PARAMETER_DOWNLOAD) != null) {
			WebUtils.setDownloadableHeader(response, content.fileName);
		}

		//发送文件内容, 对有需要的内容进行压缩传输.
		OutputStream output;
		if (WebUtils.checkAccetptGzip(request) && content.needGzip) {
			//不设置content-length, 使用http1.1 trunked编码.
			//使用压缩传输的outputstream.
			output = WebUtils.getGzipOutputStream(response);
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

	/**
	* 从缓存中获取Content基本信息.
	*/
	private Content getContentFromCache(String path) {
		Content content = contentCache.get(path);
		if (content == null) {
			content = createContent(path);
			contentCache.put(path, content);
		}
		return content;
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
	public static class Content {
		File file;
		String path;
		String fileName;
		int length;
		String mimeType;
		long lastModified;
		String etag;
		boolean needGzip;
	}
}
