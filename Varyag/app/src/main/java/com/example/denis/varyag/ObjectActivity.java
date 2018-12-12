package com.example.denis.varyag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.io.Console;

public class ObjectActivity extends AppCompatActivity {

    CarouselView carouselView;
    int[] sampleImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        Bundle arguments = getIntent().getExtras();
        Toast.makeText(ObjectActivity.this, arguments.toString(), Toast.LENGTH_SHORT).show();
        sampleImages = arguments.getIntArray("img_array");

//        Long t = getIntent().getExtras().getLong("test_val");
//        Toast.makeText(ObjectActivity.this, arguments.toString(), Toast.LENGTH_SHORT).show();

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ObjectActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        Button btnPrev = findViewById(R.id.buttonPrev);
        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
