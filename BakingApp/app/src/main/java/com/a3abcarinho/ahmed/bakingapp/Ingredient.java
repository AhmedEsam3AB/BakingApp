package com.a3abcarinho.ahmed.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;



public class Ingredient implements Parcelable {
    private int quantity;
    private String measure;
    private String ingredient;
    public Ingredient(){}
    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);

    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    public void setQuantity(int quantity){
        this.quantity = quantity;}

    public int getQuantity(){return quantity;}

    public void setMeasure(String measure){
    this.measure = measure;}
    public String getMeasure(){return measure;}
    public void setIngredient(String ingredient){
        this.ingredient = ingredient;}
    public String getIngredient(){return ingredient;}


}
