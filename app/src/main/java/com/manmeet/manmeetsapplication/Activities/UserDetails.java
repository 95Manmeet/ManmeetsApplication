package com.manmeet.manmeetsapplication.Activities;

public class UserDetails {

    private String userId;
    private String userName;
    private String Quantity;
    private String Allergy;
    private String Phone;
    private String Location;


    public UserDetails(){

    }

    public UserDetails(String userId, String userName, String quantity, String allergy, String phone, String location) {
        this.userId = userId;
        this.userName = userName;
        Quantity = quantity;
        Allergy = allergy;
        Phone = phone;
        Location = location;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getAllergy() {
        return Allergy;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLocation() {
        return Location;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setAllergy(String allergy) {
        Allergy = allergy;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
