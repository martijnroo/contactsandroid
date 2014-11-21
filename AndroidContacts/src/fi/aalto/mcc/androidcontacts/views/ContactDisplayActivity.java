package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;

public class ContactDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_display_layout);
		
		Intent i = getIntent();
		Contact contact = (Contact) i.getSerializableExtra("contact");
		
	}
	
	
	
	
	
	
}
