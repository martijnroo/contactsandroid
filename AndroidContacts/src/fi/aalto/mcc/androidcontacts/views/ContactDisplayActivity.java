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
		
		
		TextView tvId = (TextView) findViewById(R.id.text_id);
		TextView tvNa = (TextView) findViewById(R.id.text_name);
		TextView tvPo = (TextView) findViewById(R.id.text_phone);
		TextView tvEm = (TextView) findViewById(R.id.text_email);
		
		tvId.setText(contact.getId());
		tvNa.setText(contact.getName());
		tvPo.setText(contact.getPhone());
		tvEm.setText(contact.getEmail());
		
	}
	
	
	public void remove(View v){
		ContactsAPI.getInstance().remove(this.contact);
	}
	
	
}
