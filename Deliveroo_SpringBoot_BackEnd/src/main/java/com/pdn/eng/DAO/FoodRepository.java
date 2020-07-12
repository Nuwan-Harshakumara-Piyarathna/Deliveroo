package com.pdn.eng.DAO;

import com.pdn.eng.Model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FoodRepository extends MongoRepository<Food,String> {

}
