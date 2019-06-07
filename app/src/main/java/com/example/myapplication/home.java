package com.example.myapplication;


import android.app.usage.UsageEvents;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import DataModels.DataModel;
import DataModels.FinalModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment {
    RecyclerView recyclerView;
    ArrayList<DataModel> URLs;

    public home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        URLs = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() { // for connection on recycler view
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    check();
                }
                return false;
            }
        });

        call_retrofit();
        return view;
    }


    void check(){
        if(!isNetworkAvailable()){
            Snackbar snackbar = Snackbar.make(recyclerView,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
            snackbar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check();
                }
            });
            snackbar.show();
        }
    }
    void call_retrofit(){
        if(!isNetworkAvailable())
            Toast.makeText(getActivity().getApplicationContext(),"NO CONNECTION",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);

        Call<FinalModel> call1 = api.getData1();
        Call<FinalModel> call2 = api.getData2();
        Call<FinalModel> call3 = api.getData3();
        call1.enqueue(new Callback<FinalModel>() {
            @Override
            public void onResponse(Call<FinalModel> call, Response<FinalModel> response) {
                FinalModel f = response.body();
                for(DataModel d : f.getPhotos().getPhoto()){
                    URLs.add(d);
                }
               // RecyclerAdapter adapter = new RecyclerAdapter(URLs,getContext());
                //recyclerView.setAdapter(adapter);
              }

            @Override
            public void onFailure(Call<FinalModel> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

       call2.enqueue(new Callback<FinalModel>() {
            @Override
            public void onResponse(Call<FinalModel> call, Response<FinalModel> response) {
                FinalModel f = response.body();
                for(DataModel d : f.getPhotos().getPhoto()){
                    URLs.add(d);
                }
                }

            @Override
            public void onFailure(Call<FinalModel> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        call3.enqueue(new Callback<FinalModel>() {
            @Override
            public void onResponse(Call<FinalModel> call, Response<FinalModel> response) {
                FinalModel f = response.body();
                for(DataModel d : f.getPhotos().getPhoto()){
                    URLs.add(d);
                }
                RecyclerAdapter adapter = new RecyclerAdapter(URLs,getContext());
                recyclerView.setAdapter(adapter);
                 }

            @Override
            public void onFailure(Call<FinalModel> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        RecyclerAdapter adapter = new RecyclerAdapter(URLs,getContext());
        recyclerView.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
