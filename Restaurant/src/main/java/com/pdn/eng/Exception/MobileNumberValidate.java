package com.pdn.eng.Exception;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
//logic impliment file
@Constraint(validatedBy = MobileNumberConstraintValidator.class)
//define where to apply
@Target( { ElementType.METHOD, ElementType.FIELD } )  
//time of validate 
@Retention(RetentionPolicy.RUNTIME) 
public @interface MobileNumberValidate {
	
	String message() default "Invalid Mobile Number";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 

}
