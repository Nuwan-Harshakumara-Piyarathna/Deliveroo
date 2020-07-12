package com.pdn.eng.Model;


import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.pdn.eng.Exception.MobileNumberValidate;
import com.pdn.eng.Exception.ValidPassword;

@Document(collection= "users")
public class User {
	
	@Id
	private String id;
	
	@NotBlank(message = "mobileNumber is mandatory")
	@MobileNumberValidate
	private String mobileNumber;
	
	private String role= "user";
	
	@NotBlank(message = "fname is mandatory")
	private String fname;
	
	@NotBlank(message = "lname is mandatory")
	private String lname;
	
	@NotBlank(message = "password is mandatory")
	private String password;
	
	
	
	public User(String mobileNumber, String role, String fname, String lname, String password) {
		super();
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", mobileNumber=" + mobileNumber + ", role=" + role + ", fname=" + fname
				+ ", lname=" + lname + ", password=" + password + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	

}
