package com.jyaccede.model;

public class CategoryModel {

    public String id;

    public String name;

    public CategoryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryModel() {
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
}
