import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author sunboteng
 */
public class HttpServerImpl {
	private final static int S_PORT = 9911;
	private static String wwwhome = "F:";

	public static void main(String[] args) throws IOException {
		Method method = new Method();
		Requset requset = new Requset();
		Response response = new Response();
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(S_PORT);
		} catch (IOException e) {
			System.out.println("服务器无法启动" + e);
			System.exit(-1);
		}
		while (true) {
			Socket connection = serverSocket.accept();
			ServerThread serverThread = new ServerThread(connection, method,
					requset, response, wwwhome);
			serverThread.start();
		}
	}
}
