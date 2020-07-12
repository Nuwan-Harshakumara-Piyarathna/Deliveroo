package com.pdn.eng.Controller;


import javax.validation.constraints.NotBlank;

public class LoginObj  {
	
	
	@NotBlank(message = "password is mandatory")
	String password;
	
	@NotBlank(message = "mobile_number is mandatory")
	String mobileNumber;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public LoginObj(String password,String mobileNumber) {
		super();
		this.password = password;
		this.mobileNumber = mobileNumber;
	}
	
	
	
	
	

}
