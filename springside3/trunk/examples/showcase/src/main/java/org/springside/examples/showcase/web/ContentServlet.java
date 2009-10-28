package org.springside.examples.showcase.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String PARAMETER_FILE = "file";
	private static final String PARAMETER_DOWNLOAD = "download";

	private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;
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

		if (request.getParameter(PARAMETER_DOWNLOAD) != null)
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

		//取得Input/Output Stream.
		ServletOutputStream output = response.getOutputStream();
		FileInputStream input = new FileInputStream(file);

		//直接基于byte数组读取文件并直接写入OutputStream, buffer默认大小为8k.
		try {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead = 0;

			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} finally {
			//保证Input/Output Stream的关闭.
			input.close();
			output.flush();
			output.close();
		}
	}

	/**
	 * 取得文件MimeType, 无法取得时默认为
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
