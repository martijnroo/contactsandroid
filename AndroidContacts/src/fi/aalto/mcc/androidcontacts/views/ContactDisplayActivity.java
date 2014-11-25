package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

/**
 * Activity used to display one contact and its informations
 * @author bgoubin
 *
 */
public class ContactDisplayActivity extends Activity {

	/**
	 * The contact to display
	 */
	private Contact contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_display_layout);
		
		// Getting the contact
		Intent i = getIntent();
		this.contact = (Contact) i.getSerializableExtra("contact");
		
		// Displaying the informations
		setText(R.id.text_id, contact.getId());
		setText(R.id.text_name, contact.getName());
		setText(R.id.text_phone, contact.getPhone());
		setText(R.id.text_email, contact.getEmail());
		
	}
	
	/**
	 * Sets a value in a TextView
	 * @param id the id of the textview to edit
	 * @param txt the text to insert
	 */
	private void setText(int id, String txt){
		((TextView) findViewById(id)).setText(txt);
	}
	
	/**
	 * onclick reaction to delete a contact from the REST API
	 * @param v
	 */
	public void remove(View v){
		ContactsAPI.getInstance().remove(this.contact, this);
	}
	
	/**
	 * onclick reaction to go back to the main activity
	 * @param v
	 */
	public void back(View v){
		this.finish();
	}
	
	
}
