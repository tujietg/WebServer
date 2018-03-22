package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Sun Boteng
 */
public class TestRange {
	public static void main(String[] args) throws Exception {
		// TestRange.txt 内容为:123456789
		String url = "http://localhost:9991/test/TestRange.txt";

		URL ur = new URL(url);
		HttpURLConnection con = (HttpURLConnection) ur.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		// 得到结果：Response Code：206 String: 456789(最后6位)
		// con.setRequestProperty("Range", "bytes=-6");

		// 得到结果：Response Code：206 String: 567
		// con.setRequestProperty("Range", "bytes=4-6");

		// 得到结果：Response Code：206 String: 56789
		// con.setRequestProperty("Range", "bytes=4-");

		// 得到结果：Response Code：416 IOException
		// con.setRequestProperty("Range", "bytes=0-100");

		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
	}
}
