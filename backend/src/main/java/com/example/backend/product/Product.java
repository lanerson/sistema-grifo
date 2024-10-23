package com.example.backend.product;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
class Product {

    private @Id @GeneratedValue Long id;
    private String name;
    private float price;
    private String image;        

    Product() {
    }

    Product(String name, float price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public String getImage(){
        return this.image;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setImage(String image){
        this.image = image;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + this.id + ", name='" + this.name + '\'' + ", price=" + this.price +'}';
    }
}