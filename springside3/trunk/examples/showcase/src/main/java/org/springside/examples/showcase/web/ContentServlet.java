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
	private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取得文件.
		String filePath = getFilePath(request);
		File file = new File(filePath);

		//设置Response Header.
		String fileMimeType = getFileMimeType(filePath);
		int fileLength = (int) file.length();
		response.setContentType(fileMimeType);
		response.setContentLength(fileLength);

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

	private String getFileMimeType(String filePath) {
		return getServletContext().getMimeType(filePath);
	}

	private String getFilePath(HttpServletRequest request) {
		String filePath = request.getParameter(PARAMETER_FILE);
		return getServletContext().getRealPath(filePath);
	}
}
