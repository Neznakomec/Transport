package com.sdimdev.nnhackaton.data.retrofit;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sdimdev.nnhackaton.data.persistence.entity.mobile.ScanInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiUtils {

	private ApiUtils() {}

	public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";

	public static CoinSendService getAPIService() {
		return NetworkService.getInstance().getRetrofit().create(CoinSendService.class);
	}

	public static void test(ScanInfo info) throws IOException {
		/*HttpURLConnection client = new HttpURLConnection(Uri.parse("http://10.70.8.249:8889/mobiletrack"));
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://www.example.com");

		String json = "{"id":1,"name":"John"}";
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		client.close();*/

		StringBuilder sb = new StringBuilder();

		HttpURLConnection urlConnection=null;
		try {
			URL url = new URL("http://10.70.8.249:8889/mobiletrack");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setUseCaches(false);
			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(10000);
			urlConnection.setRequestProperty("Content-Type","application/json");

			//urlConnection.setRequestProperty("Host", "android.schoolportal.gr");
			urlConnection.setRequestProperty("Authorization", "Basic dXNlcjpwYXNzd29yZA==");

			urlConnection.connect();

			//Create JSONObject here
			String jsonStr = new Gson().toJson(info);
			OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
			out.write(jsonStr.toString());
			out.close();

			int HttpResult =urlConnection.getResponseCode();
			if(HttpResult ==HttpURLConnection.HTTP_OK){
				BufferedReader br = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream(),"utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				System.out.println(""+sb.toString());

			}else{
				System.out.println(urlConnection.getResponseMessage());
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		catch (Exception e) {

			e.printStackTrace();
		}finally{
			if(urlConnection!=null)
				urlConnection.disconnect();
		}
	}
}