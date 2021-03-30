import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://www.marinetraffic.com/en/ais/get_info_window_json?asset_type=ship&id=5630138");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Host", "www.marinetraffic.com");
		con.setRequestProperty("user-agent", "Chrome/88.0.4324.150");
		int responseCode = con.getResponseCode();
//		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // succe
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

//			System.out.println(response.toString());
			JSONObject obj = new JSONObject(response.toString());
			String name = obj.getJSONObject("values").getString("shipname");
			String lat = obj.getJSONObject("values").getString("ship_lat");
			String lon = obj.getJSONObject("values").getString("ship_lon");
			System.out.printf("%s (%s,%s)\n", name, lat, lon);
		} else {
			System.out.println(con.getResponseMessage());
		}
	}

}
