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
	 * The local contacts not saved yet on the REST Api
	 */
	private ArrayList<Contact> added;

	
	/**
	 * Get a contact from the list
	 * @param id
	 * @return
	 */
	public Contact getFromId(String id){
		return this.contacts.get(id);
	}
	
	/**
	 * Gets all the contacts (local + saved)
	 * @return
	 */
	public Contact[] getAllContacts(){
		
		ArrayList<Contact> ret = new ArrayList<Contact>();
		
		for(Contact c : added){
			ret.add(c);
		}
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
	
	public void addLocalContact(Contact c){
		this.added.add(c);
		
		setChanged();
		notifyObservers();
	}
	
	public void emptyLocal(){
		this.added = new ArrayList<Contact>();
		
		setChanged();
		notifyObservers();
	}
	
	
	
	/**
	 * static Singleton instance
	 */
	private static Directory instance;

	/**
	 * Private constructor for singleton
	 */
	private Directory() {
		this.added = new ArrayList<Contact>();
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
