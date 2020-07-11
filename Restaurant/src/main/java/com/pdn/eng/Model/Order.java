package com.pdn.eng.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "Order")
public class Order {
    @Id
    private String id;

    @NotBlank(message = "  * user name cannot be blank.")
	public String user;

    @NotBlank(message = "  * pay_method cannot be blank.")
    private String pay_method;

    @DecimalMin(value="-90.0", inclusive=true)
    @DecimalMax(value="90.0", inclusive=true)
    private double latitude;

    @DecimalMin(value="-180.0", inclusive=true)
    @DecimalMax(value="180.0", inclusive=true)
    private double longitude;

    @NotBlank(message = "  * address cannot be blank.")
    private String address;

    @NotBlank(message = "  * city cannot be blank.")
    private String city;

    //below attributes are auto calculated when entering data.no need for validate
    private LocalDate date;
    private LocalTime time;
    private double total_price;
    private List<Order_list> list;

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user= user;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<Order_list> getList() {
        return list;
    }

    public void setList(List<Order_list> list) {
        this.list = list;
    }
}
