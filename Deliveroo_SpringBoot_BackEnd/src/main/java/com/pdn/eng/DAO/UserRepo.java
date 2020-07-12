package com.pdn.eng.DAO;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pdn.eng.Model.User;


@Repository
public interface UserRepo extends MongoRepository <User,String> {
	
	public User findByMobileNumber(String mobileNumber);
}

