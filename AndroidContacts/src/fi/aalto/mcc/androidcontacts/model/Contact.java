package fi.aalto.mcc.androidcontacts.model;

import java.io.Serializable;


public class Contact implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2306393239322676248L;

	/**
	 * The id of the contact
	 */
	private String id;
	
	/**
	 * the name of the contact
	 */
	private String name;
	
	/**
	 * The mail address of the contact
	 */
	private String email;
	
	/**
	 * The phone number of the contact
	 */
	private String phone;

	
	
	/**
	 * Empty Constructor
	 */
	public Contact() {
	}
	
	/**
	 * Constructor with id
	 * @param id
	 * @param name
	 * @param email
	 * @param phone
	 */
	public Contact(String id, String name, String email, String phone) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	/**
	 * Constructor without
	 * @param name
	 * @param email
	 * @param phone
	 */
	public Contact(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	
	@Override
	public String toString() {
		String ret = "";
		
		ret += name;
		//ret += "id:"+id+" ";
		//ret += "name:"+name+" ";
		//ret += "phone:"+phone+" ";
		//ret += "email:"+email;
		
		return ret;
	}
	
	
	
	
}
