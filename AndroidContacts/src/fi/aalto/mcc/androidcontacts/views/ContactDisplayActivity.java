package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

public class ContactDisplayActivity extends Activity {

	private Contact contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_display_layout);
		
		Intent i = getIntent();
		this.contact = (Contact) i.getSerializableExtra("contact");
		
		setText(R.id.text_id, contact.getId());
		setText(R.id.text_name, contact.getName());
		setText(R.id.text_phone, contact.getPhone());
		setText(R.id.text_email, contact.getEmail());
		
	}
	
	private void setText(int id, String txt){
		((TextView) findViewById(id)).setText(txt);
	}
	
	
	public void remove(View v){
		ContactsAPI.getInstance().remove(this.contact, this);
	}
	
	public void back(View v){
		this.finish();
	}
	
	
}
