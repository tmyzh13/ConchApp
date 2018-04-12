package com.isoftston.issuser.conchapp.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

/**
 * Created by Ryan on 2016/3/9.
 */
public class ChosenImageFile implements Parcelable {
    public boolean chosen;
    public File image;
    public String name;
    public boolean hasName=false;
    public String imageUrl;
    public boolean fromSet=false;

    public ChosenImageFile(boolean chosen, File image) {
        this.chosen = chosen;
        this.image = image;
    }

    public ChosenImageFile(boolean chosen,String image ,boolean fromSet){
        this.chosen=chosen;
        this.imageUrl=image;
        this.fromSet=fromSet;
    }

    public ChosenImageFile(boolean chosen, File image, String name){
        this.chosen=chosen;
        this.image=image;
        this.name=name;
        hasName=true;
    }

    public static ChosenImageFile emptyInstance() {
        return new ChosenImageFile();
    }

    public ChosenImageFile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(chosen ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.image);
        dest.writeString(this.imageUrl);
        dest.writeByte(this.fromSet ? (byte) 1 : (byte) 0);
    }

    protected ChosenImageFile(Parcel in) {
        this.chosen = in.readByte() != 0;
        this.image = (File) in.readSerializable();
        this.imageUrl = in.readString();
        this.fromSet = in.readByte() != 0;
    }

    public static final Creator<ChosenImageFile> CREATOR = new Creator<ChosenImageFile>() {
        public ChosenImageFile createFromParcel(Parcel source) {
            return new ChosenImageFile(source);
        }

        public ChosenImageFile[] newArray(int size) {
            return new ChosenImageFile[size];
        }
    };
}
