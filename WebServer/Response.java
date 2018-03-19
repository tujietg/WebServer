import java.io.PrintStream;
import java.util.Date;
/**
 * @author sunboteng
 */
public class Response {

	public static void responseRange(PrintStream printStrem, String path,
			Method method, int begin, int end, int length) {
		printStrem.print("HTTP/1.1 206 Partial Content\r\n" + "Content-Type: "
				+ method.guessContentType(path) + "\r\n" + "Date: "
				+ new Date() + "\r\n" + "Content-Range: " + "bytes " + begin
				+ "-" + end + "/" + length + "\r\n"
				+ "Server: HTTPServer 1.0\r\n\r\n");
	}

	public static void responseHtml(PrintStream printStrem, String path,
			Method method) {
		printStrem.print("HTTP/1.1 200 OK\r\n" + "Content-Type: "
				+ method.guessContentType(path) + "\r\n" + "Date: "
				+ new Date() + "\r\n" + "Server: HTTPServer 1.0\r\n\r\n");
	}

	public static void errorResponse(PrintStream printStrem, String path,
			Method method) {
		printStrem.println("HTTP/1.1 404 not found\r\n" + "Content-Type: "
				+ method.guessContentType(path) + "\r\n" + "Date: "
				+ new Date() + "\r\n" + "Server: HTTPServer 1.0\r\n\r\n");
		printStrem.println();
		printStrem.println("<h1> 404 not found </h1>");
	}
}
