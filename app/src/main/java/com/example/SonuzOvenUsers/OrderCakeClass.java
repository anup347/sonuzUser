package com.example.SonuzOvenUsers;

public class OrderCakeClass {

    public String oCakeName;
    public String oCakeDescription;
    public String oCakePrice;
    public String oCakePic;
    public String oCakeNotes;
    public String oCakeStatus;
    public String  ocurrentDateTime;
    public String oCakeUserId;

    public OrderCakeClass(){

    }

    public OrderCakeClass(String oCakeName, String oCakeDescription, String oCakePrice, String oCakePic, String oCakeNotes, String oCakeStatus, String ocurrentDateTime, String oCakeUserId) {
        this.oCakeName = oCakeName;
        this.oCakeDescription = oCakeDescription;
        this.oCakePrice = oCakePrice;
        this.oCakePic = oCakePic;
        this.oCakeNotes = oCakeNotes;
        this.oCakeStatus = oCakeStatus;
        this.ocurrentDateTime = ocurrentDateTime;
        this.oCakeUserId = oCakeUserId;
    }

    public String getoCakeName() {
        return oCakeName;
    }

    public void setoCakeName(String oCakeName) {
        this.oCakeName = oCakeName;
    }

    public String getoCakeDescription() {
        return oCakeDescription;
    }

    public void setoCakeDescription(String oCakeDescription) {
        this.oCakeDescription = oCakeDescription;
    }

    public String getoCakePrice() {
        return oCakePrice;
    }

    public void setoCakePrice(String oCakePrice) {
        this.oCakePrice = oCakePrice;
    }

    public String getoCakePic() {
        return oCakePic;
    }

    public void setoCakePic(String oCakePic) {
        this.oCakePic = oCakePic;
    }

    public String getoCakeNotes() {
        return oCakeNotes;
    }

    public void setoCakeNotes(String oCakeNotes) {
        this.oCakeNotes = oCakeNotes;
    }

    public String getoCakeStatus() {
        return oCakeStatus;
    }

    public void setoCakeStatus(String oCakeStatus) {
        this.oCakeStatus = oCakeStatus;
    }

    public String getOcurrentDateTime() {
        return ocurrentDateTime;
    }

    public void setOcurrentDateTime(String ocurrentDateTime) {
        this.ocurrentDateTime = ocurrentDateTime;
    }

    public String getoCakeUserId() {
        return oCakeUserId;
    }

    public void setoCakeUserId(String oCakeUserId) {
        this.oCakeUserId = oCakeUserId;
    }
}
