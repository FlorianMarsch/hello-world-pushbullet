package de.florianmarsch.devcenter.hello_world_pushbullet;
import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class PushBulletApiTest {


	@Test
	public void test() throws ClientProtocolException, IOException, JSONException {
		String api_key = System.getenv("PUSHBULLET_ACCESS_TOKEN");

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope("api.pushbullet.com", 443),
				new UsernamePasswordCredentials(api_key, null));
		CloseableHttpClient client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();

		HttpPost httppost = new HttpPost("https://api.pushbullet.com/v2/pushes");
		httppost.setHeader("Content-Type", "application/json");

		JSONObject message = new JSONObject();
		message.put("type", "file");
		message.put("body", "Hello World :)");
		message.put("channel_tag", System.getenv("PUSHBULLET_CHANNEL_TAG"));

		message.put("file_name", "test.jpg");
		message.put("file_type", "image/jpeg");
		message.put("file_url", "https://www.comunio.de/bundesligaspieler/tradablePhoto.phtml/b/32525.gif");
		
		httppost.setEntity(new StringEntity(message.toString()));

		client.execute(httppost);

	}

}
