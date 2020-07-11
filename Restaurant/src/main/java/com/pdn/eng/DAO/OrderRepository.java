package com.pdn.eng.DAO;

import com.pdn.eng.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
    List<Order> findByUser(String user);

    void deleteAll();
}
