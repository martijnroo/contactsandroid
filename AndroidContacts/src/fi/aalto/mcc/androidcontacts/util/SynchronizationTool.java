package fi.aalto.mcc.androidcontacts.util;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

public class SynchronizationTool {

	public static void syncFromPhone(Activity act){


		// Getting all contacts from the phone
		ArrayList<Contact> list = getContactsFromPhone(act);

		// Removing all distant contacts
		ContactsAPI.getInstance().removeAll();

		// Sending all contacts
		for(Contact c : list.toArray(new Contact[0])){
			ContactsAPI.getInstance().add(c, null);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
		}

		// Retrieving
		ContactsAPI.getInstance().retrieve();
	}


	public static void syncToPhone(){

	}


	private static ArrayList<Contact> getContactsFromPhone(Activity act) {
		ArrayList<Contact> ret = new ArrayList<Contact>();

		Cursor cursor = act.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		int idField		= cursor.getColumnIndex(ContactsContract.Contacts._ID);
		int nameField 	= cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

		while(cursor.moveToNext()){

			String id 		= cursor.getString(idField);
			String name 	= cursor.getString(nameField);
			String phone 	= getField(act, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.DATA, id);
			String email 	= getField(act, ContactsContract.CommonDataKinds.Email.CONTENT_URI, ContactsContract.CommonDataKinds.Email.DATA, id);

			ret.add(new Contact(id, name, email, phone));

		}

		return ret;
	}

	private static String getField(Activity act, Uri uri, String fieldStr, String contactId){

		String ret = "";

		Cursor c = act.getContentResolver().query(uri, null, "contact_id=?", new String[]{contactId}, null);
		if(c.moveToFirst()){
			ret = c.getString(c.getColumnIndex(fieldStr)); 
		}
		c.close();

		return ret;
	}


}
