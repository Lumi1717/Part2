package com.example.flowerstore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<FlowerModel> parseJson(String json){

        List<FlowerModel> flowerModelList = new ArrayList<>();


        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FlowerModel flower = new FlowerModel();

                flower.setCategory(jsonObject.getString("category"));
                flower.setPrice(jsonObject.getDouble("price"));
                flower.setInstructions(jsonObject.getString("instructions"));
                flower.setPhoto(jsonObject.getString("photo"));
                flower.setName(jsonObject.getString("name"));
                flower.setProductId(jsonObject.getInt("productId"));

                flowerModelList.add(flower);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return flowerModelList;
    }

}
