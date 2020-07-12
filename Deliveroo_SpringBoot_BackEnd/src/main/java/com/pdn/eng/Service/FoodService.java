package com.pdn.eng.Service;


import com.pdn.eng.DAO.FoodRepository;
import com.pdn.eng.Model.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
	
	@Autowired
    private FoodRepository foodRepository;

    //create a food item
    public Food createFood(Food food){

       return foodRepository.save(food);

    }

    //get all foods
    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }

    //find a food by id
    public Food findFoodById(String id){
        Optional<Food> optionalFood = foodRepository.findById(id);

        if(optionalFood.isPresent()){
            return optionalFood.get();
        }
        else{
            return  null;
        }
    }

    //delete a food
    public String deleteFood(String id){
        foodRepository.deleteById(id);
        return "Food item deleted";
    }
    
    //delete all foods
    public String deleteAllFoods() {
    	
    	foodRepository.deleteAll();
    	
    	return "all foods deleted";
    }

    //update a food item
    public Food updateFood(Food food){

        Optional<Food> optionalFood = foodRepository.findById(food.getFoodId());

        if(optionalFood.isPresent()){
            Food myFood = optionalFood.get();
            myFood.setFoodName(food.getFoodName());
            myFood.setCategory(food.getCategory());
            myFood.setPrice(food.getPrice());
            myFood.setFoodImage(food.getFoodImage());

            return foodRepository.save(food);
        }
        else{
            return  null;
        }
    }

    //get all food items by category
    public List<Food> getFoodByCategory(String category){

        List<Food> allFoods = foodRepository.findAll();
        List<Food> foodCategory = new ArrayList<>();

        for(Food food : allFoods){

            if(food.getCategory().equals(category)){

                foodCategory.add(food);
            }

        }

        return foodCategory;
    }
   
}
