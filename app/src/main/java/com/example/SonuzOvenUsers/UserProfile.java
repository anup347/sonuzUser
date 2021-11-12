package com.example.SonuzOvenUsers;

public class UserProfile {
    public String name;
    public String phonenumber;
    public String userType;
    public String email;

    public  UserProfile(){

    }


    public UserProfile(String name, String phonenumber, String userType, String email) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.userType = userType;
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
