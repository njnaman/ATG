package com.example.myapplication;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import DataModels.DataModel;

public class image_slider extends AppCompatActivity {
private TextView position,tittle;
ViewPager viewPager;
private int selected_position;
MyViewPagerAdapter myViewPagerAdapter;
ArrayList<DataModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);
        position=findViewById(R.id.count);
        tittle = findViewById(R.id.tittle);
        Bundle bundle = getIntent().getExtras();
        data = (ArrayList<DataModel>) bundle.get("url_list");
        selected_position = (Integer) bundle.get("position");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        myViewPagerAdapter = new MyViewPagerAdapter(this);
       viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        setcurrentitem(selected_position);


    }

    private void setcurrentitem(int position){

        viewPager.setCurrentItem(position);
        displaydata(position);

    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            displaydata(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };

    private void displaydata(int position){
        this.position.setText((position+1)+" of "+data.size());
        if((data.get(position).getTittle())==null)
            tittle.setText("No Tittle Found");
        else
        tittle.setText(data.get(position).getTittle());
    }

    public class MyViewPagerAdapter extends PagerAdapter{
        Context context;

        public MyViewPagerAdapter(Context context) {
            this.context = context;
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==((View)o);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater= (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.swipe_layout,container,false);
            ImageView imageView = view.findViewById(R.id.imagePreview);
            Glide.with(getApplicationContext()).load(data.get(position).getUrl_s()).thumbnail(0.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            container.addView(view);
            return view;
        }

    }
}
