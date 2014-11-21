package fi.aalto.mcc.androidcontacts.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	
	public void add(Contact c){
		
	}
	
	public void remove(Contact c){
		
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
