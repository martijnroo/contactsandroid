package fi.aalto.mcc.androidcontacts.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;

/**
 * The contact directory
 * @author bgoubin
 *
 */
public class Directory extends Observable{

	/**
	 * The saved contacts retrieved from the REST Api
	 */
	private HashMap<String, Contact> contacts;

	/**
	 * Gets all the contacts (local + saved)
	 * @return
	 */
	public Contact[] getAllContacts(){
		
		ArrayList<Contact> ret = new ArrayList<Contact>();

		for(Entry<String,Contact> c : contacts.entrySet()){
			ret.add(c.getValue());
		}
		return ret.toArray(new Contact[0]);
		
	}
	
	/**
	 * Sets the contacts saved in the REST service
	 * @param contacts
	 */
	public void setContacts(Contact[] contacts){
		this.contacts = new HashMap<String, Contact>();
		
		for(Contact c : contacts){
			this.contacts.put(c.getId(), c);
		}
		
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Removes a contact from the local contacts
	 * @param c the contact to remove
	 */
	public void removeContact(Contact c){
		this.contacts.remove(c.getId());
	}
	
	/**
	 * static Singleton instance
	 */
	private static Directory instance;

	/**
	 * Private constructor for singleton
	 */
	private Directory() {
		this.contacts = new HashMap<String, Contact>();
	}

	/**
	 * Static getter method for retrieving the singleton instance
	 */
	public static Directory getInstance() {
		if (instance == null) {
			instance = new Directory();
		}
		return instance;
	}
	
	
}
