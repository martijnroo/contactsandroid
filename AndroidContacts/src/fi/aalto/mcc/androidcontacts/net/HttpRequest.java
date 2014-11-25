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

/**
 * This class is used to do the requests used for the needs of the application.
 * Some requests don't need parameters (GET for instance)
 * to support parameters, changes might be required
 */
public class HttpRequest {

	/**
	 * General request
	 * @param url The url to reach
	 * @param method the http request to use
	 * @param params the parameters to transmit (might not be used)
	 * @return the content of the http response
	 */
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


	/**
	 * Performs a Get request
	 * @param url the url to reach
	 * @return the response of the get request
	 */
	private static String doGet(String url){

		HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest req = new HttpGet(url);

		return getResponse(httpclient, req);
	}


	/**
	 * Performs a Post request
	 * @param url the url to reach
	 * @param params the parameters to send, must be given in JSON
	 * @return the response of the Post request
	 */
	private static String doPost(String url, String ... params){
		String ret = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost req = new HttpPost(url);

		try{
			// Adding the parameters
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
		
		// Performing the request
		ret = getResponse(httpclient, req);

		return ret;
	}


	/**
	 * Performs a Delete request
	 * @param url the url to reach
	 * @param params the parameters to send
	 * @return the response of the get request
	 */
	private static String doDelete(String url, String ... params){
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpUriRequest req = new HttpDelete(url+params[0]);
		
		return getResponse(httpclient, req);
	}


	/**
	 * Not used in this application
	 * @param url the url to reach
	 * @param params the parameters to send
	 * @return nothing
	 */
	private static String doPut(String url, String ... params){
		return "";
	}

	/**
	 * Performs the Http request
	 * @param httpclient the http client
	 * @param req the uri request
	 * @return the content of the response
	 */
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
