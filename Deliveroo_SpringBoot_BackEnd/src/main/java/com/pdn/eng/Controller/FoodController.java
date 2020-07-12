package com.pdn.eng.Controller;

import com.pdn.eng.Model.Food;
import com.pdn.eng.Service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

//admin routes
@RestController
@RequestMapping("admin/foods")
public class FoodController {
	
	

	@Autowired
    FoodService foodService;
	

	//add a food to the database
    @PostMapping("/add")
    public String addFoodItem(@Valid @RequestBody Food foodItem){
    	
        if(foodService.createFood(foodItem) != null){

            return "food item created";

        }
        else{

            return "food item creation failed";
        }
    }

    //delete a food item
    @GetMapping("/delete/{id}")
    public String deleteFoodItem(@PathVariable String id){

        return foodService.deleteFood(id);

    }
    
    //delete all foods
    @GetMapping("/delete/all")
    public String deleteAll() {
    	
    	return foodService.deleteAllFoods();
    }
    

    //update a food item
    @PostMapping("/update/{id}")
    public Food updateFoodItem(@PathVariable String id,@Valid @RequestBody Food food){
    	
    	//Food food = new Food(data.get("foodName"),data.get("category"),Float.parseFloat(data.get("price")),data.get("foodImage"));
    	food.setFoodId(id);

        return foodService.updateFood(food);

    }

  
   
}
