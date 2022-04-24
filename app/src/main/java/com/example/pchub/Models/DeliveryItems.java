package com.example.pchub.Models;

public class DeliveryItems {

    String id;
    String name;
    String price;
    String qty;
    String userName;
    String userNIC;
    String userContact;
    String userAddress;

    public DeliveryItems() {
    }

    public DeliveryItems(String id, String name, String price, String qty, String userName, String userNIC, String userContact, String userAddress) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.userName = userName;
        this.userNIC = userNIC;
        this.userContact = userContact;
        this.userAddress = userAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNIC() {
        return userNIC;
    }

    public void setUserNIC(String userNIC) {
        this.userNIC = userNIC;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
