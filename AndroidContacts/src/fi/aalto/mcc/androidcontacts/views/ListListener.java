package fi.aalto.mcc.androidcontacts.views;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import fi.aalto.mcc.androidcontacts.model.Contact;

public class ListListener implements OnItemClickListener {

	private Activity activity;
	private ListView listview;
	
	public ListListener(Activity act, ListView lv) {
		this.activity = act;
		this.listview = lv;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Contact c = (Contact) this.listview.getItemAtPosition(position);
		
		Intent intent = new Intent(activity, ContactDisplayActivity.class);
		intent.putExtra("contact", c);
		activity.startActivity(intent);
		
	}

}
