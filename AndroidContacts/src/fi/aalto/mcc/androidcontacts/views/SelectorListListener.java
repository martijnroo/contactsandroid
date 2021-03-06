package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

/**
 * Listener used to display one contact and its informations
 * @author bgoubin
 *
 */
public class SelectorListListener implements OnItemClickListener {

	/**
	 * The activity containing the ListView
	 */
	private Activity activity;
	/**
	 * The ListView listened
	 */
	private ListView listview;
	
	/**
	 * Constructor
	 * @param act containint activity
	 * @param lv listened ListView
	 */
	public SelectorListListener(Activity act, ListView lv) {
		this.activity = act;
		this.listview = lv;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		// Getting the contact
		Contact c = (Contact) this.listview.getItemAtPosition(position);
		
		// New activity (ContactDisplayActivity�, the intent containint the contact to display
//		Intent intent = new Intent(activity, ContactDisplayActivity.class);
//		intent.putExtra("contact", c);
//		activity.startActivity(intent);
		

		ContactsAPI.getInstance().add(c, null);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
	
		// Retrieving
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
	}

}
