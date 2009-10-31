package org.springside.examples.showcase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springside.modules.web.WebUtils;

public class ContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAMETER_PATH = "path";
	private static final String PARAMETER_REDIRECT = "redirect";
	private static final String PARAMETER_DOWNLOAD = "download";

	private static Map<String, Content> contentMap = new ConcurrentHashMap<String, Content>();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter(PARAMETER_PATH);

		if (request.getParameter(PARAMETER_REDIRECT) != null) {
			redirectToImageServer(response, path);
		} else {
			sendFile(request, response, path);
		}
	}

	private void redirectToImageServer(HttpServletResponse response, String path) throws IOException {
		String imageServerUrl = "http://localhost:8080/showcase/";
		response.sendRedirect(imageServerUrl + path);
	}

	private void sendFile(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
		Content content = getContentFromCache(path);

		//判断文件有否修改过,如无修改则设置返回码为304,返回.
		if (!WebUtils.checkIfModified(request, content.lastModified)) {
			WebUtils.setNotModified(response);
			return;
		}

		//设置Response Header.
		response.setContentLength(content.length);
		response.setContentType(content.mimeType);
		WebUtils.setLastModifiedHeader(response, content.lastModified);

		//如果是下载请求,设置下载Header
		if (request.getParameter(PARAMETER_DOWNLOAD) != null) {
			WebUtils.setDownloadableHeader(response, content.fileName);
		}

		//取得Input/Output Stream.
		OutputStream output = WebUtils.getZipOutputStream(request, response);
		FileInputStream input = new FileInputStream(content.file);

		//基于byte数组直接读取文件并直接写入OutputStream, 数组默认大小为4k.
		try {
			IOUtils.copy(input, output);
			output.flush();
		} finally {
			//保证Input/Output Stream的关闭.
			IOUtils.closeQuietly(input);
			IOUtils.closeQuietly(output);
		}
	}

	private Content getContentFromCache(String path) {
		Content content = contentMap.get(path);
		if (content == null) {
			content = createContent(path);
			contentMap.put(path, content);
		}
		return content;
	}

	private Content createContent(String path) {
		Content content = new Content();
		content.path = path;

		String realFilePath = getServletContext().getRealPath(path);
		File file = new File(realFilePath);

		content.file = file;
		content.fileName = file.getName();
		content.length = (int) file.length();
		content.lastModified = new Date(file.lastModified());

		String mimeType = getServletContext().getMimeType(realFilePath);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		content.mimeType = mimeType;

		return content;
	}

	public static class Content {
		File file;
		String path;
		String fileName;
		String mimeType;
		int length;
		Date lastModified;
	}
}
