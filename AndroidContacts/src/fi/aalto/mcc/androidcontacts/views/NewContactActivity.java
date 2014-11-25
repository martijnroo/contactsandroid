package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

/**
 * This is the activity used to create a new contact
 * @author bgoubin
 *
 */
public class NewContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact_layout);
	}
	
	/**
	 * onclick reaction for creating a contact
	 * @param v
	 */
	public void create(View v){
		Contact contact = new Contact();
		
		// Getting the inputs
		EditText nameInput = (EditText) findViewById(R.id.new_input_name);
		EditText emailInput = (EditText) findViewById(R.id.new_input_phone);
		EditText phoneInput = (EditText) findViewById(R.id.new_input_email);
		
		// Setting the parameters of the contact
		contact.setName(nameInput.getText().toString());
		contact.setPhone(phoneInput.getText().toString());
		contact.setEmail(emailInput.getText().toString());
		
		// Saving the contact
		ContactsAPI.getInstance().add(contact, this);
	}
	
	/**
	 * onclick reaction for going back in the main activity
	 * @param v
	 */
	public void back(View v){
		this.finish();
	}
	
	
}
