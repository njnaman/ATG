package com.example.myapplication;

import DataModels.FinalModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL="https://api.flickr.com/services/rest/";

    @GET("?method=flickr.photos.getRecent&per_page=20&page=1&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<FinalModel> getData1();
    @GET("?method=flickr.photos.getRecent&per_page=20&page=2&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<FinalModel> getData2();
    @GET("?method=flickr.photos.getRecent&per_page=20&page=3&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<FinalModel> getData3();

    @GET("?method=flickr.photos.search&api_key=6f102c62f41998d151e5a1b48713cf13&format=json&nojsoncallback=1&extras=url_s")
    Call<FinalModel> getSearchdata(@Query("text") String keyword);


}
