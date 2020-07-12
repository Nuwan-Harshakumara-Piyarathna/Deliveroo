package com.nofirst.deliveroo;

public class Food {

    private String foodId;
    private  String foodName;
    private  String category;
    private  double price;
    private  String foodImage;

    public Food(String foodId, String foodName, String category, double price, String foodImage) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.foodImage = foodImage;
    }

    public String getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFoodImage() {
        return foodImage;
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

