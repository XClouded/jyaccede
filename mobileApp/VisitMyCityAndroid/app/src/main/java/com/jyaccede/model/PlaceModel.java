package com.jyaccede.model;

public class PlaceModel {

    public String name;

    public String address;

    public double latitude;

    public double longitude;

    public String remark;

    public int idCategorie;

    public int lvlAccess;

    public PlaceModel() {
    }

    public PlaceModel(String name, String address, double latitude, double longitude, String remark, int idCategorie, int lvlAccess) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.remark = remark;
        this.idCategorie = idCategorie;
        this.lvlAccess = lvlAccess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getLvlAccess() {
        return lvlAccess;
    }

    public void setLvlAccess(int lvlAccess) {
        this.lvlAccess = lvlAccess;
    }
}
