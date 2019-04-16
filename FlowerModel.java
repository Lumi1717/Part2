package com.example.flowerstore;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class FlowerModel implements Parcelable {
    private String category;
    private double price;
    private String instructions;
    private String photo;
    private String name;
    private int productId;
    private Bitmap bitmap;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return getName();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.category);
        dest.writeDouble(this.price);
        dest.writeString(this.instructions);
        dest.writeString(this.photo);
        dest.writeString(this.name);
        dest.writeInt(this.productId);
        dest.writeParcelable(this.bitmap, flags);
    }

    public FlowerModel() {
    }

    protected FlowerModel(Parcel in) {
        this.category = in.readString();
        this.price = in.readDouble();
        this.instructions = in.readString();
        this.photo = in.readString();
        this.name = in.readString();
        this.productId = in.readInt();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<FlowerModel> CREATOR = new Parcelable.Creator<FlowerModel>() {
        @Override
        public FlowerModel createFromParcel(Parcel source) {
            return new FlowerModel(source);
        }

        @Override
        public FlowerModel[] newArray(int size) {
            return new FlowerModel[size];
        }
    };
}
