package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dto.ActiviteRetourDTO;


public class BoredService {

	private static int PRICE_MAX= 10000;
	private static final String URL_API = "https://www.boredapi.com/api/activity";
	public ActiviteRetourDTO iDontWant2BeBoredAnymore(String... params) throws UnsupportedEncodingException, IOException {
		URL url = null;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = null;

		if(params.length==0) {
			url = new URL(URL_API);
			 request = new HttpGet(URL_API);
		}else {
			url = new URL(buildRequest(URL_API, params));
			request = new HttpGet(buildRequest(URL_API, params));
		}
		ActiviteRetourDTO dto = new ActiviteRetourDTO();
		
		
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(stream));
                    String line;
                    String fichierJson = "";
                    while ((line = reader.readLine()) != null) {
                    		
                        System.out.println(line);
                        fichierJson = fichierJson+line;
                    }
                    try {
						JSONObject js = (JSONObject) new JSONParser().parse(fichierJson);
						
						if (js.get("error")==null){
							
							dto.setActiviteLabel(String.valueOf(js.get("activity")));
							dto.setAccessibility((Long)js.get("accessibility"));
							dto.setType(String.valueOf(js.get("type")));
							dto.setParticipants((Long)js.get("participants"));
							dto.setPrice((Long)js.get("price"));
						}else {
							dto.setError((String)js.get("error"));
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return dto;
	}
	
	private String buildRequest(String url, String...params) {
		url = url +"?";
		if(!params[0].isBlank()||!params[0].isEmpty()) {
			url = url +"type=" + params[0]+"&";
		}
		if(!params[1].isBlank()||!params[1].isEmpty()) {
			
			url = url +"participants=" + params[1]+"&";
		}
		if(!params[2].isBlank()||!params[2].isEmpty()) {
			url = url +"maxprice=" + priceRatio(params[2]);
		}
		
		
		return url;
	}
	
	private String priceRatio(String price) {
		int prix = Integer.valueOf(price);
		
		prix = prix/PRICE_MAX;
		
		return String.valueOf(prix);
	}
}
