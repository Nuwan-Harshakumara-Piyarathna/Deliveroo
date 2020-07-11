package com.pdn.eng.Model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderUpdatePayLoad {

    @NotBlank(message = "  * user name cannot be blank.")
    private String user;

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

    private LocalTime time;
    private LocalDate date;
    private ArrayList<Order_list> list;

    public String getUser() {
        return user;
    }

    public String getPay_method() {
        return pay_method;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<Order_list> getList() {
        return list;
    }


}
