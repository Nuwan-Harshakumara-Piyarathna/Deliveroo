package com.pdn.eng.Controller;

import com.pdn.eng.Model.Order;
import com.pdn.eng.Model.OrderUpdatePayLoad;

import com.pdn.eng.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public Collection<Order> getOrders(){
        return orderService.getOrders();
    }

    @GetMapping(value = "/find/{id}")
    public Optional<Order> getOrderById(@PathVariable("id") String id){
        return orderService.getOrderById(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    public Optional<Order> deleteOrderById(@PathVariable("id") String id){
        return orderService.deleteOrderById(id);
    }

    @PutMapping(value = "/update/{id}")
    public Optional<Order> updateOrderById(@PathVariable("id") String id,@Valid @RequestBody OrderUpdatePayLoad orderUpdatePayLoad) {
        return orderService.updateOrderById(id,orderUpdatePayLoad);
    }

    @GetMapping("/find")
    public Collection<Order> getOrderByUser(@RequestParam(name = "user")String user){
        return orderService.getOrderByUser(user);
    }

    @DeleteMapping(value = "/delete/all")
    public void deleteAllOrders(){
        orderService.deleteAll();
    }
}
