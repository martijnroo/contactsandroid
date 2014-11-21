package fi.aalto.mcc.androidcontacts.views;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fi.aalto.mcc.androidcontacts.R;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.model.Directory;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

public class MainActivity extends Activity implements Observer{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		// Observer
		Directory.getInstance().addObserver(this);
		
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
		
	}
	
	public void retrieve(View v){
		ContactsAPI.getInstance().retrieve();
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
