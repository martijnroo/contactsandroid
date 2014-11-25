package fi.aalto.mcc.androidcontacts.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.model.Directory;

public class ContactsAPI {

	private String url = "http://cloudguest114.niksula.hut.fi:8080/contacts/";
	
	public void retrieve(){
		
		new Thread(){
			public void run(){
				
				String resp = HttpRequest.request(url, Method.GET);

				try {
					JSONArray array = new JSONArray(resp);
					Contact[] contacts = new Contact[array.length()];
					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						
						String id 		= obj.getString("_id");
						String name 	= obj.getString("name");
						String phone 	= obj.getString("phone");
						String email 	= obj.getString("email");
						
						Contact c = new Contact(id, name, email, phone);
						contacts[i] = c;
					}
					
					Directory.getInstance().setContacts(contacts);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		
	}
	
	
	public void add(final Contact c, final Activity act){
		
		new Thread(){
			@Override
			public void run() {
				
				try {
					
					JSONObject params = new JSONObject();
					params.put("name", c.getName());
					params.put("phone", c.getPhone());
					params.put("email", c.getEmail());
					
					HttpRequest.request(url, Method.POST, params.toString());
					
					if(act != null){
						act.finish();
					}
					retrieve();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();
		
	}
	
	public void remove(final Contact c, final Activity act){
		
		new Thread(){
			public void run(){
				HttpRequest.request(url, Method.DELETE, c.getId());
				Directory.getInstance().removeContact(c);
				retrieve();
				act.finish();
			}
		}.start();
		
	}
	

	public void removeAll() {
		new Thread(){
			public void run(){
				HttpRequest.request(url, Method.DELETE, "");
				retrieve();
			}
		}.start();
	}
	
	
	/**
	 * static Singleton instance
	 */
	private static ContactsAPI instance;

	/**
	 * Private constructor for singleton
	 */
	private ContactsAPI() {
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static ContactsAPI getInstance() {
		if (instance == null) {
			instance = new ContactsAPI();
		}
		return instance;
	}

	
}
