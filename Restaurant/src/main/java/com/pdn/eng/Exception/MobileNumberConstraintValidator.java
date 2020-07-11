package com.pdn.eng.Exception;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileNumberConstraintValidator implements ConstraintValidator<MobileNumberValidate , String>  {

	@Override
	public void initialize(MobileNumberValidate constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String MobileNumber, ConstraintValidatorContext context) {
		
		if(MobileNumber.length()!=12) {
			return false;
		}
		else if(!MobileNumber.substring(0,3).equals("+94")){
			return false;
		}
		else if(!MobileNumber.substring(3,12).matches("[0-9]+")) {
			return false;
		}
		
		return true;
		
	}
	
	

}
