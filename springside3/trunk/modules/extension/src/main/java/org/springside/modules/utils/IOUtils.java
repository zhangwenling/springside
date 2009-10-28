package org.springside.modules.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
/**
 * @see org.apache.commons.io.IOUtils
 * 
 * @author calvin
 *
 */
public class IOUtils {
	private static final int DEFAULT_BUFFER_SIZE = 8 * 1024;

	public static long copy(InputStream input, OutputStream output) throws IOException {
		return copy(input, output, DEFAULT_BUFFER_SIZE);
	}

	public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buffer = new byte[bufferSize];
		long totalCount = 0;
		int bytesRead = 0;
		while (-1 != (bytesRead = input.read(buffer))) {
			output.write(buffer, 0, bytesRead);
			totalCount += bytesRead;
		}
		return totalCount;
	}

	public static void close(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

	public static void close(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}

}
