import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Sun Boteng
 */
public class HttpServerImpl {
	private final static int S_PORT = 9991;
	private static String wwwhome = "F:";

	public static void main(String[] args) throws IOException {

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(S_PORT);
		} catch (IOException e) {
			System.out.println("服务器无法启动" + e);
			System.exit(-1);
		}
		System.out.println("服务器启动.......");
		while (true) {
			Socket connection = serverSocket.accept();
			ServerThread serverThread = new ServerThread(connection, wwwhome);
			serverThread.start();
		}
	}
}
