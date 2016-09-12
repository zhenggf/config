package cn.orgid.funny.config.client.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;



public class HttpClientComponent {
	
	public static Header Header = new BasicHeader(HttpHeaders.CONTENT_TYPE,
			ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8).toString());
	
	
	
	public static <T> T executePost(RequestParameters parameters, String url,ResponseHandler<T> responseHandler){
		try{
		
		HttpUriRequest httpUriRequest = parameters.build(url);
		return LocalHttpClient.execute(httpUriRequest, responseHandler);
		
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static class RequestParameters{
		
		private Map<String, String> paraMap =new HashMap<String, String>();
		
		public void add(String n,String v){
			
			
			paraMap.put(n, v);
			
		}
		
		public HttpUriRequest build(String url){
			
			HttpUriRequest r=null;
			RequestBuilder b = RequestBuilder.post()
					.setHeader(Header).
					setUri(url);
			
			try {
				List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
				Set<String> ks = paraMap.keySet();
				for (String k : ks) {
					formParams.add(new BasicNameValuePair(k, paraMap.get(k)));
				}
				HttpEntity entity = new UrlEncodedFormEntity(formParams , "UTF-8");
				b.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r=b.build();
			return r;
			
		}
		
		
	}
	
	
	public static void main(String[] args) {
		
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				//.setHeader(JsonHeader)
				.setUri("/cgi-bin/menu/create")
				.addParameter("access_token", "sss")
				//.setEntity(new StringEntity("{'id':'ss'}",Charset.forName("utf-8")))
				.build();
		
		System.out.println(httpUriRequest);
		
		
		
	}
	
	
}
