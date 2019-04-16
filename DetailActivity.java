package com.example.flowerstore;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

//import static com.example.flowerstore.MainActivity.FLOWER_NAME;

public class DetailActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 200;
    public static final String FLOWER_NAME = "FLOWER_NAME";
    private String FlowerTitle = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FlowerModel flowerModel = getIntent().getParcelableExtra(MainActivity.FLOWER_OBJECT1);
        FlowerTitle = flowerModel.getName();
        TextView tvFlowerName = findViewById(R.id.dFlowerTitle);
        tvFlowerName.setText((flowerModel.getName()) + "[" + (flowerModel.getCategory()) + "]");

        TextView tvFlowerINST = findViewById(R.id.dFlowerInfo);
        tvFlowerINST.setText(flowerModel.getInstructions());

        TextView tvFlowerPrice = findViewById(R.id.dFlowerCost);
        tvFlowerPrice.setText(flowerModel.getPrice()+"");

        ImageView ivFlowerImage  = findViewById(R.id.dFlowerImage);
        ivFlowerImage.setImageBitmap(flowerModel.getBitmap());

        tvFlowerName.setText(flowerModel.getName());
        tvFlowerINST.setText(flowerModel.getInstructions());
        tvFlowerPrice.setText(String.format("%s",flowerModel.getPrice()));

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void BuyButtonNow(View view) {
        Intent intent = getIntent().putExtra(FLOWER_NAME,FlowerTitle);
        setResult(RESULT_CODE,intent);

        finish();
    }
}
