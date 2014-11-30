package fi.aalto.mcc.androidcontacts.util;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import fi.aalto.mcc.androidcontacts.model.Contact;
import fi.aalto.mcc.androidcontacts.model.Directory;
import fi.aalto.mcc.androidcontacts.net.ContactsAPI;

/**
 * This class if used to synchronize the contacts from the phone and from the REST API
 * @author bgoubin
 *
 */
public class SynchronizationTool {

	/**
	 * Synchronizes the contacts from the phone to the REST API
	 * @param act the activity that requested the synchronization
	 */
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

	/**
	 * Synchronizes the contacts from the REST API to the phone
	 * @param act the activity that requested the synchronization
	 */
	public static void syncToPhone(Activity act){

		// Removing all phones contacts
		removeContactsFromPhone(act);

		// Saving local contacts to phone contacts
		Contact[] contacts = Directory.getInstance().getAllContacts();
		for(Contact c : contacts){
			saveContactToPhone(act, c);
		}

	}

	/**
	 * Gets all the contacts from the phone
	 * @param act the activity that requested the operation
	 * @return the Contact list
	 */
	public static ArrayList<Contact> getContactsFromPhone(Activity act) {
		ArrayList<Contact> ret = new ArrayList<Contact>();

		// Getting the database content
		Cursor cursor = act.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		// the needed fields
		int idField		= cursor.getColumnIndex(ContactsContract.Contacts._ID);
		int nameField 	= cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

		// reading the database
		while(cursor.moveToNext()){

			// Parameters of the contact
			String id 		= cursor.getString(idField);
			String name 	= cursor.getString(nameField);
			String phone 	= getField(act, ContactsContract.CommonDataKinds.Phone.CONTENT_URI, ContactsContract.CommonDataKinds.Phone.DATA, id);
			String email 	= getField(act, ContactsContract.CommonDataKinds.Email.CONTENT_URI, ContactsContract.CommonDataKinds.Email.DATA, id);

			// Saving the contact
			ret.add(new Contact(id, name, email, phone));

		}
		// Closing the database
		cursor.close();

		return ret;
	}

	/**
	 * Gets a field in the database that is corresponding to a contact
	 * @param act the activity that requested the operation
	 * @param uri the database
	 * @param fieldStr the searched field
	 * @param contactId the id of the contact
	 * @return the value of the found field
	 */
	private static String getField(Activity act, Uri uri, String fieldStr, String contactId){

		String ret = "";
		// Getting the database
		Cursor c = act.getContentResolver().query(uri, null, "contact_id=?", new String[]{contactId}, null);
		if(c.moveToFirst()){
			// Getting the value
			ret = c.getString(c.getColumnIndex(fieldStr)); 
		}
		// closing the database
		c.close();

		return ret;
	}

	/**
	 * Removes all the contacts from the phone
	 * @param act the activity that requested the operation
	 */
	private static void removeContactsFromPhone(Activity act){

		// Getting the database
		Cursor cursor = act.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		while(cursor.moveToNext()){
			// Getting the key of the contact
			String key = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
			// Getting the corresponding URI
			Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, key);
			// Deleting the contact
			act.getContentResolver().delete(uri, null, null);
		}

	}

	/**
	 * Saves a contact in the phone
	 * @param act the activity that requested the operation
	 * @param contact the contact to save
	 */
	public static void saveContactToPhone(Activity act, Contact contact){
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		// Raw contact
		ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());
		// Name
		if (contact.getName() != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE,ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.getName())
					.build());
		}
		// Phone
		if (contact.getPhone() != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.getPhone())
					.build());
		}
		// Email
		if (contact.getEmail() != null) {
			ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA, contact.getEmail())
					.build());
		}

		// Saving the contact
		try {
			act.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
