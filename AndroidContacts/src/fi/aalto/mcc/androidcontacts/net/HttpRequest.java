package fi.aalto.mcc.androidcontacts.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpRequest {


	public static String request(String url, Method method, String ... params){
		switch (method) {
		case GET:
			return doGet(url);
		case POST:
			return doPost(url, params);
		case DELETE:
			return doDelete(url, params);
		case PUT:
			return doPut(url, params);
		default:
			return "";
		}
	}



	private static String doGet(String url){
		String ret = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpGet(url));
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				ret = out.toString();
				//..more logic
			} else {
				//Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	
	private static String doPost(String url, String ... params){
		System.out.println("ADD");
		return "";
	}
	
	
	
	private static String doDelete(String url, String ... params){
		String ret = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = httpclient.execute(new HttpDelete(url));
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				ret = out.toString();
				//..more logic
			} else {
				//Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	
	
	private static String doPut(String url, String ... params){
		return "";
	}


}
