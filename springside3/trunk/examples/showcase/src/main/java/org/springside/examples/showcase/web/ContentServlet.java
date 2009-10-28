package org.springside.examples.showcase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springside.modules.web.WebUtils;

public class ContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAMETER_FILE = "file";
	private static final String PARAMETER_DOWNLOAD = "download";

	private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取得文件.
		String filePath = getFilePath(request);
		File file = new File(filePath);

		//设置Response Header.
		int fileLength = (int) file.length();
		response.setContentLength(fileLength);
		
		String fileMimeType = getFileMimeType(filePath);
		response.setContentType(fileMimeType);
		
		//如果是下载请求,设置下载Header
		if (request.getParameter(PARAMETER_DOWNLOAD) != null){
			WebUtils.setDownloadableHeader(response, file.getName());
		}

		//取得Input/Output Stream.
		ServletOutputStream output = response.getOutputStream();
		FileInputStream input = new FileInputStream(file);

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



	/**
	 * 取得文件MimeType, 无法取得时默认为"application/octet-stream".
	 */
	private String getFileMimeType(String filePath) {
		String mimeType = getServletContext().getMimeType(filePath);
		if (mimeType == null)
			mimeType = DEFAULT_MIME_TYPE;
		return mimeType;
	}

	/**
	 * 取得Web应用内文件的绝对存储路径.
	 */
	private String getFilePath(HttpServletRequest request) {
		String filePath = request.getParameter(PARAMETER_FILE);
		return getServletContext().getRealPath(filePath);
	}
}
