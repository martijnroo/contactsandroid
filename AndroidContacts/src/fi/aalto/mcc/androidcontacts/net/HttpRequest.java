package fi.aalto.mcc.androidcontacts.net;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

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

		HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest req = new HttpGet(url);

		return getResponse(httpclient, req);
	}



	private static String doPost(String url, String ... params){
		String ret = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost req = new HttpPost(url);

		try{

			JSONObject json = new JSONObject(params[0]);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();

			Iterator<String> keys = json.keys();
			while(keys.hasNext()){
				String key = keys.next();
				String val = json.getString(key);
				pairs.add(new BasicNameValuePair(key, val));
			}
			req.setEntity(new UrlEncodedFormEntity(pairs));

		} catch(JSONException e){
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ret = getResponse(httpclient, req);

		return ret;
	}



	private static String doDelete(String url, String ... params){
		HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest req = new HttpDelete(url+params[0]);
		return getResponse(httpclient, req);
	}



	private static String doPut(String url, String ... params){
		return "";
	}

	private static String getResponse(HttpClient httpclient, HttpUriRequest req){

		try {
			HttpResponse response = httpclient.execute(req);
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				return out.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}


}
