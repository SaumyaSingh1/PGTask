package com.saumya.pgtask.Manager;

public class TenantDataModel {
    private String Name;
    private String Phone;
    private String RentAmount;
    private String RoomNo;

    public TenantDataModel() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRentAmount() {
        return RentAmount;
    }

    public void setRentAmount(String rentAmount) {
        RentAmount = rentAmount;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public TenantDataModel(String name, String phone, String rentAmount, String roomNo) {
        Name = name;
        Phone = phone;
        RentAmount = rentAmount;
        RoomNo = roomNo;
    }
}
