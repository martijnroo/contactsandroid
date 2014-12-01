package fi.aalto.mcc.androidcontacts.views;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.model.Directory;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;
import fi.aalto.mcc.androidcontacts.util.SynchronizationTool;

/**
 * Main Activity, used to list the contacts, retrieve them, and requesting the synchronizations
 * @author bgoubin
 *
 */
public class SelectorActivity extends Activity implements Observer{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_selector_layout);
		
		// Observer
		Directory.getInstance().addObserver(this);
		
		// Retrieve contacts
		retrieve(null);
		
		// Generating view
		update(null, null);
	}
	
	
	/**
	 * Sets the list of contacts with the contacts in the Directory
	 */
	private void setList(){
		// Getting the elements
		ListView lv = (ListView) findViewById(R.id.listview);
		
		// Getting the contacts
//		Contact[] values = Directory.getInstance().getAllContacts();
		ArrayList<Contact> values = SynchronizationTool.getContactsFromPhone(this);
		
		// Setting the list
		ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		lv.setAdapter(adapter);
		
		// Setting the listener of the list
		lv.setOnItemClickListener(new SelectorListListener(this, lv));
		
	}
	
	
	/**
	 * onclick reaction to go to the creation of a contact
	 * @param v
	 */
	public void add(View v){
		Intent intent = new Intent(this, NewContactActivity.class);
		this.startActivity(intent);
	}
	
	/**
	 * onclick reaction to retrieve the contacts
	 * @param v
	 */
	public void retrieve(View v){
		ContactsAPI.getInstance().retrieve();
	}
	
	/**
	 * onclick reaction to synchronize the contacts from to phone to the REST API
	 * @param v
	 */
	public void syncFrom(View v){
		SynchronizationTool.syncFromPhone(this);
	}
	
	/**
	 * onclick reaction to synchronize the contacts from the REST API to the phone
	 * @param v
	 */
	public void syncTo(View v){
		SynchronizationTool.syncToPhone(this);
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		runOnUiThread(new Runnable() {
		     public void run() {
		    	 // updating the list
		    	 setList();
		    }
		});
	}
	
	
	
	
	
}
