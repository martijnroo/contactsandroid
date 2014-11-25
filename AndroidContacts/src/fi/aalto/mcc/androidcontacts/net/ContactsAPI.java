package fi.aalto.mcc.androidcontacts.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.model.Directory;

/**
 * This class is used to deal with the API
 * @author bgoubin
 *
 */
public class ContactsAPI {

	/**
	 * The url to reach the API
	 */
	private String url = "http://cloudguest114.niksula.hut.fi:8080/contacts/";

	/**
	 * Retrieves the contacts on the REST API
	 */
	public void retrieve(){

		// Can't be running in the UI Thread
		new Thread(){
			public void run(){

				// Getting the contacts
				String resp = HttpRequest.request(url, Method.GET);

				// Reading the response
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

					// Saving the contacts
					Directory.getInstance().setContacts(contacts);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}.start();

	}

	/**
	 * Adds a contact in the REST API
	 * @param c the contact to add
	 * @param act the activity launching the request
	 */
	public void add(final Contact c, final Activity act){

		// Can't be running in the UI Thread
		new Thread(){
			@Override
			public void run() {

				try {
					// Creating the parameters as JSON
					JSONObject params = new JSONObject();
					params.put("name", c.getName());
					params.put("phone", c.getPhone());
					params.put("email", c.getEmail());

					// Performing the request
					HttpRequest.request(url, Method.POST, params.toString());

					// If the request was performed by an activity or by the synchronization tool
					if(act != null){
						act.finish();
					}

					// Getting the new contacts
					retrieve();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}.start();

	}

	/**
	 * Removes a contact from the REST API
	 * @param c the contact to remove
	 * @param act the activity that performed the request
	 */
	public void remove(final Contact c, final Activity act){

		// Cannot be running in the UI Thread
		new Thread(){
			public void run(){
				// HTTP Delete Request
				HttpRequest.request(url, Method.DELETE, c.getId());
				// Removing local contact
				Directory.getInstance().removeContact(c);
				// Getting the new list of contacts
				retrieve();
				// Closing the activity (ContactDisplayActivity)
				act.finish();
			}
		}.start();

	}

	/**
	 * Removes all contacts in the REST API
	 */
	public void removeAll() {
		new Thread(){
			public void run(){
				// HTTP Request
				HttpRequest.request(url, Method.DELETE, "");
				// Getting the contacts
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
