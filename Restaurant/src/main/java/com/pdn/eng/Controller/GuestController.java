package com.pdn.eng.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pdn.eng.Model.Food;
import com.pdn.eng.Service.FoodService;

@RestController
@RequestMapping("foods")
public class GuestController {
	@Autowired
    FoodService foodService;
	
	 //find all food items
    @GetMapping("all")
    public List<Food> getAllFoodItems(){

        return foodService.getAllFoods();

    }
    
    //find a food item by id
    @GetMapping("/find/{id}")
    public  Food findFoodItem(@PathVariable String id){

        return foodService.findFoodById(id);
    }
    
    //find all the food items belongs to a particular category
    @GetMapping("/find")
    public  List<Food> getAllFoodFromCategory(@RequestParam("category") String category){

        return foodService.getFoodByCategory(category);
        
    }
}
