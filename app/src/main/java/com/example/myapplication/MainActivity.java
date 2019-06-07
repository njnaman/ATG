package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    final Fragment fragment1 = new home();
    final Fragment fragment2 = new search();
    Fragment active = fragment1;
    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_bar);

        fm.beginTransaction().add(R.id.frame, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.frame,fragment1, "1").commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selected=null;
                switch (menuItem.getItemId())
                {
                    case R.id.home :
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active=fragment1;
                        break;
                    case R.id.search :
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active=fragment2;
                        break;

                }
                return true;
            }
        });
    }
}
