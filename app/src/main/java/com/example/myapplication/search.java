package com.example.myapplication;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
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
public class search extends Fragment {
    RecyclerView search_recyclerView;
    ArrayList<DataModel> search_URLs;
    SearchView searchView;


    public search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        search_URLs = new ArrayList<>();
        search_recyclerView = view.findViewById(R.id.search_recycler_view);
        searchView = view.findViewById(R.id.search_view);
        search_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        search_recyclerView.setHasFixedSize(true);

        searchView.setQueryHint("Search for Photos");

        search_recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    check_recylcer();
                }
                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(isNetworkAvailable())
                    call_retrofit(query);
                else check_search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    void check_recylcer(){
        if(!isNetworkAvailable()){
            Snackbar snackbar = Snackbar.make(search_recyclerView,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
            snackbar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_recylcer();
                }
            });
            snackbar.show();
        }
    }

    void check_search(final String keyword){
        if(!isNetworkAvailable()){
            Snackbar snackbar = Snackbar.make(search_recyclerView,"NO INTERNET CONNECTION",Snackbar.LENGTH_LONG);
            snackbar.setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    check_search(keyword);
                }
            });
            snackbar.show();
        } else call_retrofit(keyword);
    }

    void call_retrofit(String keyword){
        search_URLs.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        Api api = retrofit.create(Api.class);

        Call<FinalModel> call = api.getSearchdata(keyword);
        FinalModel f;

        call.enqueue(new Callback<FinalModel>() {
            @Override
            public void onResponse(Call<FinalModel> call, Response<FinalModel> response) {
                FinalModel f = response.body();
                for(DataModel d : f.getPhotos().getPhoto()){
                    search_URLs.add(d);
                }
                RecyclerAdapter adapter = new RecyclerAdapter(search_URLs,getContext());
                search_recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<FinalModel> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
