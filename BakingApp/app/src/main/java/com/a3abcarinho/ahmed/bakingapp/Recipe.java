package com.a3abcarinho.ahmed.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class Recipe implements Parcelable {
    public static final String RECIPES_URL =  "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static final String KEY = "RECIPES_KEY";
    private int id;
    private String name;
    private String image;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    public Recipe(){}

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        ingredientList = new ArrayList<>();
        in.readTypedList(ingredientList,Ingredient.CREATOR);
        stepList = new ArrayList<>();
        in.readTypedList(stepList,Step.CREATOR);
    }


    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
            this.name = name;
    }
    public String getName(){
            return name;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return image;
    }
    public void setIngredientList(List<Ingredient> ingredientList){
        this.ingredientList = ingredientList;
    }
    public List<Ingredient> getIngredientList(){
        return ingredientList;
    }
    public void setStepList(List<Step> stepList){
        this.stepList = stepList;
    }
    public List<Step> getStepList(){
        return stepList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeTypedList(ingredientList);
        parcel.writeTypedList(stepList);
    }
}
