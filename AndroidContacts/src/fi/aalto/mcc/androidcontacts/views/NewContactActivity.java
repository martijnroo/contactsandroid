package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

public class NewContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_contact_layout);
		
		
	}
	
	
	public void create(View v){
		Contact contact = new Contact();
		
		EditText nameInput = (EditText) findViewById(R.id.new_input_name);
		EditText emailInput = (EditText) findViewById(R.id.new_input_phone);
		EditText phoneInput = (EditText) findViewById(R.id.new_input_email);
		
		contact.setName(nameInput.getText().toString());
		contact.setPhone(phoneInput.getText().toString());
		contact.setEmail(emailInput.getText().toString());
		
		ContactsAPI.getInstance().add(contact, this);
	}
	
	public void back(View v){
		this.finish();
	}
	
	
}
