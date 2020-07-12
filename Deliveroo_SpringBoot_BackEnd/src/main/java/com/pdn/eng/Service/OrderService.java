package com.pdn.eng.Service;

import com.pdn.eng.DAO.OrderDAO;
import com.pdn.eng.Model.Food;
import com.pdn.eng.Model.Order;
import com.pdn.eng.Model.OrderUpdatePayLoad;
import com.pdn.eng.Model.Order_list;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private FoodService foodServiceInOrder;

    public  Collection<Order> getOrders() {
        return orderDAO.getOrders();
    }

    public Order addOrder(Order order) {
        List<Order_list> list = order.getList();

        //calculate total price
        float total_price[] = {0};
        for (int i = 0; i < list.size(); i++) {
            String food_id = list.get(i).getFood_id();
            int quantity = list.get(i).getQuantity();
            Food food = foodServiceInOrder.findFoodById(food_id);
            if(food != null) {
            	
            	total_price[0] += (float) quantity * food.getPrice();
            	
            }
        }

        //set total price
        order.setTotal_price(total_price[0]);

        //set date
        LocalDateTime currentDate = LocalDateTime.now();
        order.setDate(currentDate.toLocalDate());

        //set time
        LocalDateTime currentTime = LocalDateTime.now();
        order.setTime(currentTime.toLocalTime());

        return orderDAO.addOrder(order);
    }

    public Optional<Order> getOrderById(String id) {
        return orderDAO.getOrderById(id);
    }

    public Optional<Order> deleteOrderById(String id) {
        return orderDAO.deleteOrderById(id);
    }

    public Optional<Order> updateOrderById(String id, OrderUpdatePayLoad orderUpdatePayLoad) {

        //set date
        LocalDateTime currentDate = LocalDateTime.now();
        orderUpdatePayLoad.setDate(currentDate.toLocalDate());

        //set time
        LocalDateTime currentTime = LocalDateTime.now();
        orderUpdatePayLoad.setTime(currentTime.toLocalTime());

        return orderDAO.updateOrderById(id, orderUpdatePayLoad);
    }

    public Collection<Order> getOrderByUser(String user) {
        return orderDAO.getOrderByUser(user);
    }

    public void deleteAll() {
        orderDAO.deleteAll();
    }
}
