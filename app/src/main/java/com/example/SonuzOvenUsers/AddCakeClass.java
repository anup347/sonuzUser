package com.example.SonuzOvenUsers;

import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AddCakeClass
{
    public String cakeName;
    public String cakeDescription;
    public String cakePrice;
    public String cakePicture;
    public String  currentDateTime;

    public  AddCakeClass(){
        //Default constructor
    }


    

    public AddCakeClass(String cakeName, String cakeDescription, String cakePrice, String cakePicture, String currentDateTime) {
        this.cakeName = cakeName;
        this.cakeDescription = cakeDescription;
        this.cakePrice = cakePrice;
        this.cakePicture = cakePicture;
        this.currentDateTime = currentDateTime;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String  currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getCakeName() {
        return cakeName;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public String getCakeDescription() {
        return cakeDescription;
    }

    public void setCakeDescription(String cakeDescription) {
        this.cakeDescription = cakeDescription;
    }

    public String getCakePrice() {
        return cakePrice;
    }

    public void setCakePrice(String cakePrice) {
        this.cakePrice = cakePrice;
    }

    public String getCakePicture() {
        return cakePicture;
    }

    public void setCakePicture(String cakePicture) {
        this.cakePicture = cakePicture;
    }
}
