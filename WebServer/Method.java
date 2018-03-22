import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Sun Boteng
 */
public class Method {
	public static void dirfile(PrintStream printStrem, String path) {
		Response response = new Response();
		Method method = new Method();
		File file = new File(path);
		String[] s = file.list();
		printStrem.print("HTTP/1.1 200 OK" + "\r\n" + "Content-Type:text/html"
				+ "\r\n");
		printStrem.println();
		String[] str = path.split(":");
		int i = 0;
		if (s != null && s.length != 0) {

			for (i = 0; i < s.length; i++) {
				printStrem.println("<HTML>");
				if (path.endsWith(":/")) {
					printStrem.println("<a href= " + "/" + s[i] + ">" + s[i]
							+ "</a>");
				} else {
					if (s[i].endsWith(".mp3")) {
						String[] strs = s[i].split(":");
						String name = strs[0];
						String modName = name.replace(".mp3", "");
						String newName1 = modName + ".html";
						printStrem.println("<a href= " + str[1] + "/"
								+ newName1 + ">" + s[i] + "</a>");
					} else {
						printStrem.println("<a href= " + str[1] + "/" + s[i]
								+ ">" + s[i] + "</a>");
					}

				}
				printStrem.println("</HTML>");
			}
		} else {
			method.respomseNullFile(printStrem, path, method);
		}
		printStrem.flush();
		printStrem.close();
	}

	public static String guessContentType(String path) {
		if (path.endsWith(".html") || path.endsWith(".htm")) {
			return "text/html";
		} else if (path.endsWith(".txt") || path.endsWith(".java")) {
			return "text/plain";
		} else if (path.endsWith(".gif")) {
			return "image/gif";
		} else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
			return "image/jpeg";
		} else if (path.endsWith(".mp3")) {
			return "audio/mpeg";
		} else if (path.endsWith(".class"))
			return "application/octet-stream";
		else {
			return "text/plain";
		}

	}

	public static boolean isfile(String path) {
		if (path.endsWith(".html") || path.endsWith(".htm")
				|| path.endsWith(".txt") || path.endsWith(".java")
				|| path.endsWith(".gif") || path.endsWith(".jpg")
				|| path.endsWith(".jpeg") || path.endsWith(".mp3")||path.endsWith(".class")) {
			return true;
		} else {
			return false;
		}
	}

	public static int sumFile(String path) {
		File f = new File(path);
		return (int) f.length();
	}

	// 处理Range
	public static int[] range(String str, String path) {
		int[] berange = new int[2];
		int size = Method.sumFile(path);
		if (str.indexOf(",") != -1) {
			return null;
		}
		String[] parts = str.split("=");
		String[] bytes = parts[1].split("-");
		if (parts[1].endsWith("-")) {
			berange[0] = Integer.parseInt(bytes[0]);
			berange[1] = size - 1;
		} else {
			// bytes=-500 最后500个字节
			if (bytes[0] == null || bytes[0].equals("")) {
				berange[0] = size - Integer.parseInt(bytes[1]);
				berange[1] = size - 1;
			} else {
				berange[0] = Integer.parseInt(bytes[0]);
				berange[1] = Integer.parseInt(bytes[1]);
			}
		}
		return berange;
	}

	public static void sendRangeFile(OutputStream out, RandomAccessFile re,
			int begin, int end, int length) throws IOException {
		re.seek(begin);
		byte[] buffer = new byte[length];
		re.read(buffer);
		out.write(buffer);
	}

	public static void sendFile(InputStream in, OutputStream out) {
		byte[] buffer = new byte[1024];
		try {
			while (in.available() > 0) {
				out.write(buffer, 0, in.read(buffer));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void respomseNullFile(PrintStream printStrem, String path,
			Method method) {
		printStrem.println();
		printStrem.println("<h4> 此文件夾为空，请返回上层目录。 </h4>");
	}
}
