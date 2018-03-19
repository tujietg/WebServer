import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;

class ServerThread extends Thread {
	private Socket connection = null;
	private BufferedReader in = null;
	private OutputStream out = null;
	private PrintStream printStrem = null;
	private Method method = null;
	private Requset requset = null;
	private Response response = null;
	private String wwwhome = null;
	private String[] requsetMethod = null;
	private String[] allRequset = null;
	private String url = null;
	private ArrayList<Socket> list = null;

	public ServerThread(Socket connection, Method method, Requset requset,
			Response response, String wwwhome) {
		this.connection = connection;
		this.method = method;
		this.requset = requset;
		this.response = response;
		this.wwwhome = wwwhome;

		try {
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			OutputStream outputStream = connection.getOutputStream();
			out = new BufferedOutputStream(outputStream);
			printStrem = new PrintStream(out);
			requsetMethod = requset.getmethod(connection, in);
			url = requsetMethod[1];

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void run() {
		// 解决/favicon.ico所带来的影响
		if (!url.contains("/favicon.ico")) {
			String path = wwwhome + url;
			System.out.println(path);
			if (requsetMethod[0].equals("GET")) {
				if (!(method.isfile(path))) {
					method.dirfile(printStrem, path);
				} else {
					try {
						allRequset = requset.getAllRequest(connection, in);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					int i = requset.getIndex(allRequset);
					if (i != -1) {
						int len = method.sumFile(path);
						int[] rangeSize = method.range(allRequset[i], path);
						int begin = rangeSize[0];
						int end = rangeSize[1];
						int length = end - begin + 1;
						File f = new File(path);
						InputStream file;
						try {
							file = new FileInputStream(f);
							response.responseRange(printStrem, path, method,
									begin, end, length);
							try {
								RandomAccessFile re = new RandomAccessFile(f,
										"r");
								method.sendRangeFile(out,re, begin, end,
										length);
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					} else {
						try {
							File f = new File(path);
							InputStream file = new FileInputStream(f);
							response.responseHtml(printStrem, path, method);
							method.sendFile(file, out);
						} catch (FileNotFoundException e) {
							response.errorResponse(printStrem, path, method);
						}

					}

				}
			}
			try {
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
