package com.example.denis.varyag;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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

        // Получим аргументы Activity
        Bundle arguments = getIntent().getExtras();

        sampleImages = arguments.getIntArray("img_array");
        String define = arguments.getString("define");
        String title = arguments.getString("object_name");

        // LinearLayout
        LinearLayout linearObjectTopLayout = (LinearLayout) findViewById(R.id.linearObjectTopLayout);

        LinearLayout linearObjectContentLayout = new LinearLayout(ObjectActivity.this);
        linearObjectContentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearObjectContentLayout.setOrientation(LinearLayout.VERTICAL);

        // BtnPrev
        Button btnPrev = new Button(ObjectActivity.this);
        btnPrev.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnPrev.setText("Назад");
        btnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // TextTitle
        TextView objectTitle = new TextView(ObjectActivity.this);
        objectTitle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        objectTitle.setText(Html.fromHtml(title));
//        objectTitle.setGravity(Gravity.CENTER_VERTICAL);
        objectTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        // TextDefine
        TextView objectDefine = new TextView(ObjectActivity.this);
        objectDefine.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        objectDefine.setText(Html.fromHtml(define));

        // Carousel
        carouselView = (CarouselView) findViewById(R.id.carouselView);
//        CarouselView carouselView = new CarouselView(ObjectActivity.this);
//        carouselView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 300));
//        carouselView.setFillColor(Color.WHITE);
//        carouselView.setPageColor(Color.BLACK);
//        carouselView.setRadius(6);
//        carouselView.setSlideInterval(3000);
//        carouselView.setStrokeWidth(1);
//        carouselView.setStrokeColor(Color.BLACK);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ObjectActivity.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        linearObjectTopLayout.addView(objectTitle, 0);
        linearObjectTopLayout.addView(btnPrev);
//        linearContentLayout.addView(carouselView);
        linearObjectContentLayout.addView(objectDefine);

//        ScrollView sv = (ScrollView) findViewById(R.id.objectContentScrollView);
//        sv.addView(linearContentLayout);
        linearObjectTopLayout.addView(linearObjectContentLayout);
    }

    // Set img's in carousel
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}
