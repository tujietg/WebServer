import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Sun Boteng
 */
public class Requset {
	private Socket connection = null;
	private BufferedReader in = null;

	public String[] getmethod(Socket connection, BufferedReader in)
			throws IOException {

		String request = in.readLine();
		while (true) {
			if (request == null || request.equals("")) {
				continue;
			} else {
				String[] requestsplit = request.split(" ");
				return requestsplit;
			}
		}

	}

	// �õ�ȫ���ı�ͷ
	public static String[] getAllRequest(Socket connection, BufferedReader in)
			throws IOException {
		String request = in.readLine();
		String[] allRequest = new String[20];
		int i = 0;
		boolean flag = false;
		while (true) {
			request = in.readLine();
			allRequest[i++] = request;
			if (request.equals("") || request == null) {
				break;
			}
		}
		return allRequest;
	}

	// �õ�index�����ж�(������range����-1)
	public static int getIndex(String[] allRequest) {
		int i;
		for (i = 0; i < allRequest.length; i++) {
			if (allRequest[i].contains("Range:")) {
				return i;
			}
			if (allRequest[i].equals("") || allRequest[i] == null) {
				return -1;
			}
		}
		return -1;
	}

	// �õ�Range���ڵĲ�����
	public static String getRange(String[] allRequest, int i) {
		return allRequest[i];
	}

}