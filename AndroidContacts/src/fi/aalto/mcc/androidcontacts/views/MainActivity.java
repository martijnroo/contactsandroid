package fi.aalto.mcc.androidcontacts.views;

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

public class MainActivity extends Activity implements Observer{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		// Observer
		Directory.getInstance().addObserver(this);
		
		// Retrieve contacts
		retrieve(null);
		
		// Generating view
		update(null, null);
	}
	
	
	
	private void setList(){
		ListView lv = (ListView) findViewById(R.id.listview);
		Contact[] values = Directory.getInstance().getAllContacts();
		
		ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(new ListListener(this, lv));
		
	}
	
	
	
	public void add(View v){
		Intent intent = new Intent(this, NewContactActivity.class);
		this.startActivity(intent);
	}
	
	public void retrieve(View v){
		ContactsAPI.getInstance().retrieve();
	}
	
	
	public void syncFrom(View v){
		SynchronizationTool.syncFromPhone(this);
	}
	
	public void syncTo(View v){
		
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		runOnUiThread(new Runnable() {
		     public void run() {
		    	 setList();
		    }
		});
	}
	
	
	
	
	
}
