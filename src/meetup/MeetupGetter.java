package meetup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
 
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
 
 
 
public class MeetupGetter {
 
 
	private String city = "";
	private String topic = "";
 
	public String getCityList(String pathCode,String key) throws Exception{
		String responseString = "";
 
		URI request = new URIBuilder()			
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath(pathCode)
			.setParameter("country", "rs")
			.setParameter("key", key)
			.build();
 
		HttpGet get = new HttpGet(request);		
		//System.out.println("Get request : "+get.toString());
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		responseString = EntityUtils.toString(response.getEntity());
 
		return responseString;
	}
	
	public String getEventsInCity(String pathCode,String key) throws Exception{
		String responseString = "";
 
		URI request = new URIBuilder()			
			.setScheme("http")
			.setHost("api.meetup.com")
			.setPath(pathCode)
			.setParameter("country", "rs")
			.setParameter("city", city)
			.setParameter("key", key)
			.build();
 
		HttpGet get = new HttpGet(request);			
		//System.out.println("Get request : "+get.toString());
 
		CloseableHttpClient client = HttpClients.createDefault();
		CloseableHttpResponse response = client.execute(get);
		responseString = EntityUtils.toString(response.getEntity());
 
		return responseString;
	}
 
	public String getApiKey(String key_path){
		String key = "";
 
		try{
			BufferedReader reader = new BufferedReader(new FileReader(key_path));	
			key = reader.readLine().toString();									
			reader.close();
		}
		catch(Exception e){System.out.println(e.toString());}
 
		return key;															
	}
	
	

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
 
}
