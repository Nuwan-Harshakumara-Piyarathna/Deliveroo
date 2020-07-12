package com.pdn.eng.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Document(collection = "Food")
public class Food {
	@Id
    String foodId;
    
    @NotBlank(message = "  * Food name cannot be blank.")
    private String foodName;
    
    @NotBlank(message = "  * category cannot be blank.")
    private String category;
    
    @Indexed(direction = IndexDirection.ASCENDING)
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 4, fraction = 2)
    private float price;
    
    private String foodImage;

    public Food(String foodName, String category, float price, String foodImage) {
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.foodImage = foodImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }


    @Override
    public String toString() {
        return "Food{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", foodImage='" + foodImage + '\'' +
                '}';
    }
}
