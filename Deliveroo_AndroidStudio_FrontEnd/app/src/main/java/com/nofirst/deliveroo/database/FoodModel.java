package com.nofirst.deliveroo.database;

public class FoodModel {
    String food_id;
    String food_name;
    double food_price;
    int quantity;

    public FoodModel(String food_id, String food_name, double food_price, int quantity) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_price = food_price;
        this.quantity = quantity;
    }

    public FoodModel() {
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getFood_price() {
        return food_price;
    }

    public void setFood_price(double food_price) {
        this.food_price = food_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "food_id='" + food_id + '\'' +
                ", food_name='" + food_name + '\'' +
                ", food_price=" + food_price +
                ", quantity=" + quantity +
                '}';
    }
}
