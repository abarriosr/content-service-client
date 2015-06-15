package com.acquia.contentservices;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.adamcin.httpsig.api.Authorization;
import net.adamcin.httpsig.api.RequestContent;
import net.adamcin.httpsig.api.Signer;
import net.adamcin.httpsig.hmac.HmacKey;
import net.adamcin.httpsig.hmac.HmacKeyId;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.acquia.http.HMACHttpRequestInterceptor;

public class ContentServices {
	
	private String origin;
	private String baseUrl = "http://plexus-dolceficante-dev-640995125.us-east-1.elb.amazonaws.com:3000";

	private String api;
	private String secret;
	
	private final static String DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss z";
	private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";	
	private final static String HMAC_SHA256_ALGORITHM = "HmacSHA256";

	private final static String SECRET = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
	private final static String USERNAME = "AAAAAAAAAAAAAAAAAAAA";	
	
	public static void main(String args[]){
		//JSONObject obj = new JSONObject();
		String api = "AAAAAAAAAAAAAAAAAAAA";
		String secret = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
		String origin = "11111111-0000-0000-0000-000000000000";	
		Map<String,String> config = new HashMap<String,String>();
		config.put("base_url", "http://plexus-dolceficante-dev-640995125.us-east-1.elb.amazonaws.com:3000");
		
		ContentServices client = new ContentServices(api, secret, origin, config);

		try {
			origin = client.register("My Client Site 1");
		} catch (Exception e){
			e.printStackTrace();
		}

	
	}
	
	public String register(String siteName) throws Exception {
		String json = "{\"name:" + "\"" + siteName + "\"}";
		callService("POST", "/register", json);
		return "";
	}

	public ContentServices(String api, String secret, String origin, Map<String,String> config){
		this.origin = origin;
		this.api = api;
		this.secret = secret;
	}
	
	public void callService(String method, String path, String json) throws Exception {
		HMACHttpRequestInterceptor authorizationInterceptor = new HMACHttpRequestInterceptor("Acquia", api, secret, "SHA256");
		authorizationInterceptor.setCustomHeaders(new String[] { "X-Acquia-Plexus-Client-Id","X-Authorization-Timestamp" } );

		// Added the authorization header interceptor as the last interceptor for the 
		// HttpClient
		CloseableHttpClient httpClient = HttpClientBuilder.create().addInterceptorLast( authorizationInterceptor ).build();

		//String httpRequestUrl = ""; // todo: the request url
		//HttpGet httpGet = new HttpGet(httpRequestUrl);
		HttpPost post = new HttpPost(baseUrl + path);
		String contentType = "application/json";
		StringEntity data = new StringEntity(json,contentType,"UTF-8");
		post.setEntity(data);		
		post.addHeader("X-Acquia-Plexus-Client-Id", origin);

		String dateValue = String.valueOf(new Date().getTime());
		post.addHeader("Date", dateValue);
		post.addHeader("X-Authorization-Timestamp", dateValue);

		// The authorization header 'Acquia 1:0Qub9svYlxjAr8OO7N0/3u0sohs=' 
		// will be added during the execute call, before the request is sent
		HttpResponse httpResponse = httpClient.execute(post); 
		System.out.println("client response:" + httpResponse.getStatusLine().getStatusCode());	

		// todo: normal processing of response		
	}
	

	
}
