package com.example.flowerstore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.flowerstore.DetailActivity.FLOWER_NAME;

public class MainActivity extends AppCompatActivity {


    public static final String FLOWER_NAME = "FLOWER_NAME";
    public static final String FLOWER_INST = "FLOWER_INST";
    public static final String FLOWER_PRICE = "FLOWER_PRICE";
    public static final String FLOWER_OBJECT = "FLOWER_OBJECT";
    public static final String FLOWER_OBJECT1 = "FlowerObject";
    public static final int REQUEST_CODE = 100;
    private Map<String, Bitmap> mMap;

    private static final String URL_JSON = "http://services.hanselandpetal.com/feeds/flowers.json";
    private static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMap = new HashMap<>();

        progressBar = findViewById(R.id.progressbar);

        if (NetManager.isOnline(this)) {
            FlowerDownloader flowerDownloader = new FlowerDownloader();
            flowerDownloader.execute(NetManager.getURL(URL_JSON));
        } else {
            Toast.makeText(this, "You are OFFLINE :(", Toast.LENGTH_LONG).show();
        }
    }




    private class ImageDownloader extends AsyncTask<DataSender,Void,Bitmap>
    {
        DataSender dataSender;

        @Override
        protected Bitmap doInBackground(DataSender... dataSenders) {
            dataSender = dataSenders[0];

            {
                URL url = NetManager.getURL(PHOTOS_BASE_URL+ dataSender.flowerMODEL.getPhoto());
                try {
                    InputStream inputStream = (InputStream) new URL(url.toString()).getContent();
                    return BitmapFactory.decodeStream(inputStream);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            dataSender.imageView.setImageBitmap(bitmap);
            dataSender.flowerMODEL.setBitmap(bitmap);
//            mMap.put(dataSender.flowerMODEL.getPhoto(), bitmap);
            FileSaver.saveFile(MainActivity.this,dataSender.flowerMODEL);

        }
    }


    private class FlowerDownloader extends AsyncTask<URL, Void, List<FlowerModel>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<FlowerModel> doInBackground(URL... urls) {
            List<FlowerModel> flowerModelList = Parser.parseJson(NetManager.fetchData(urls[0]));

//            for (FlowerModel flower: flowerModelList)
//            {
//                URL url = NetManager.getURL(PHOTOS_BASE_URL+flower.getPhoto());
//                try {
//                    InputStream inputStream = (InputStream) new URL(url.toString()).getContent();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    flower.setBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            return flowerModelList;
        }

        @Override
        protected void onPostExecute(List<FlowerModel> flowerModelList) {
            super.onPostExecute(flowerModelList);
            if(flowerModelList != null) {
                //   Toast.makeText(MainActivity.this, flowerModelList, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                updateDisplay(flowerModelList);
            }
            else
            {

            }
        }
    }

    private void updateDisplay(final List<FlowerModel> flowerModelList)
    {
        ListView lstFlowers = findViewById(R.id.lstFlowers);
        // ArrayAdapter<FlowerModel> adapter = new ArrayAdapter<~>(this, android.R.layout.);
        ArrayAdapter<FlowerModel> adapter = new DisplayFlowers(this, 0, flowerModelList);
        lstFlowers.setAdapter(adapter);

        lstFlowers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FlowerModel flowerModel = flowerModelList.get(position);

                Intent intent =  new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra(FLOWER_OBJECT1,flowerModel);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == DetailActivity.RESULT_CODE)
            assert data !=null;
        Toast.makeText(this, "Thank You For Purchasing "+data.getStringExtra(DetailActivity.FLOWER_NAME), Toast.LENGTH_SHORT).show();
    }

    private class DisplayFlowers extends ArrayAdapter<FlowerModel>
    {
        List<FlowerModel> listOfFlowerObjects;


        public DisplayFlowers(Context context, int s, List<FlowerModel> objects) {
            super(context, s, objects);
            this.listOfFlowerObjects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.layout_for_each_flower, parent, false);

            FlowerModel flower = listOfFlowerObjects.get(position);

            TextView tvFlowerName = convertView.findViewById(R.id.tvImageName);
            tvFlowerName.setText(flower.getName());


            ImageView ivFlowerImage = convertView.findViewById(R.id.ivFlowerImage);
            //ivFlowerImage.setImageBitmap(flower.getBitmap());


//
//            if(mMap.containsKey(flower.getPhoto())) {

            Bitmap bitmap = FileSaver.getFile(MainActivity.this, flower);
            flower.setBitmap(bitmap);
            if(bitmap !=null) {
                //ivFlowerImage.setImageBitmap(mMap.get(flower.getPhoto()));
                ivFlowerImage.setImageBitmap(bitmap);
            }

            else {
                DataSender dataSender = new DataSender(ivFlowerImage,flower);
                new ImageDownloader().execute(dataSender);
            }

            return convertView;
        }
    }

    private class DataSender
    {
        ImageView imageView;
        FlowerModel flowerMODEL;

        public DataSender(ImageView imageView, FlowerModel flowerMODEL) {
            this.imageView = imageView;
            this.flowerMODEL = flowerMODEL;
        }
    }

}












