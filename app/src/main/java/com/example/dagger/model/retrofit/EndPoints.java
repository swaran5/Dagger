package com.example.dagger.model.retrofit;

import com.example.dagger.model.retrofit.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EndPoints {
    @GET("api/users")
    Call<Root> getUsers(
            @Query("page") String page
    );
}
