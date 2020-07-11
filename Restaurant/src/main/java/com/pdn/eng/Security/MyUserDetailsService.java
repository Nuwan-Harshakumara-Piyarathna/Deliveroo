package com.pdn.eng.Security;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pdn.eng.DAO.UserRepo;
import com.pdn.eng.Model.User;



@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String mobileNumber) throws UsernameNotFoundException {
		 User user = userRepository.findByMobileNumber(mobileNumber);
	        return new MyUserDetailPrinciple(user);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}

