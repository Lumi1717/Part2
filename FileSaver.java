package com.example.flowerstore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaver {


    public static void saveFile(Context context, FlowerModel flowerMODEL) {

        String fileName = context.getCacheDir() + "/" + flowerMODEL.getPhoto();
        File file = new File(fileName);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            flowerMODEL.getBitmap().compress(Bitmap.CompressFormat.JPEG, 99, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Bitmap getFile(Context context, FlowerModel flowerModel) {
        String filename = context.getCacheDir() + "/" + flowerModel.getPhoto();
        File file = new File(filename);
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            return BitmapFactory.decodeStream(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
