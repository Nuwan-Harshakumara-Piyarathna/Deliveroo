package com.pdn.eng.DAO;

import com.pdn.eng.Model.Order;
import com.pdn.eng.Model.OrderUpdatePayLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class OrderDAO {

    @Autowired
    private OrderRepository orderRepository;

    public Collection<Order> getOrders(){
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        return orderRepository.insert(order);
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> deleteOrderById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> orderRepository.delete(o));
        return order;
    }

    public Optional<Order> updateOrderById(String id, OrderUpdatePayLoad orderUpdatePayLoad) {
        Optional<Order> order = orderRepository.findById(id);
        order.ifPresent(o -> o.setAddress(orderUpdatePayLoad.getAddress()));
        order.ifPresent(o -> o.setCity(orderUpdatePayLoad.getCity()));
        order.ifPresent(o -> o.setDate(orderUpdatePayLoad.getDate()));//order date not change
        order.ifPresent(o -> o.setLatitude(orderUpdatePayLoad.getLatitude()));
        order.ifPresent(o -> o.setLongitude(orderUpdatePayLoad.getLongitude()));
        order.ifPresent(o -> o.setPay_method(orderUpdatePayLoad.getPay_method()));
        order.ifPresent(o -> o.setUser(orderUpdatePayLoad.getUser()));
        order.ifPresent(o -> o.setTime(orderUpdatePayLoad.getTime()));
        order.ifPresent(o -> o.setList(orderUpdatePayLoad.getList()));
        order.ifPresent(o -> orderRepository.save(o));
        return order;
    }

    public Collection<Order> getOrderByUser(String user) {
        return orderRepository.findByUser(user);
    }

    public void deleteAll() {
        orderRepository.deleteAll();
    }
}
