package com.a3abcarinho.ahmed.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;


public class Step implements Parcelable {
    public static String KEY = "STEPS_KEY";
    private int id;
    private String description;
    private String shortDescription;
    private String videoUrl;
    private String thumbnailUrl;
public Step(){}
    protected Step(Parcel in) {
    id = in.readInt();
    description = in.readString();
    shortDescription = in.readString();
    videoUrl = in.readString();
    thumbnailUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }
    public String getShortDescription(){
        return shortDescription;
    }
    public void setVideoUrl(String videoUrl){
        this.videoUrl = videoUrl;
    }
    public String getVideoUrl(){
        return videoUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl = thumbnailUrl;
    }
    public String getThumbnailUrl(){
        return thumbnailUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(description);
        parcel.writeString(shortDescription);
        parcel.writeString(videoUrl);
        parcel.writeString(thumbnailUrl);
    }
}
