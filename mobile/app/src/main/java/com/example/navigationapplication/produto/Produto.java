package com.example.navigationapplication.produto;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.util.Locale;

public class Produto {
    private final String name;
    private final double price;
    private final String image;

    Produto(String name, double price, String image){
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }


    public String getPriceString() {
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        // Converte o float para a String formatada
        return formatoMoeda.format(price);
    }

    public String getImage() {
        return image;
    }
}
