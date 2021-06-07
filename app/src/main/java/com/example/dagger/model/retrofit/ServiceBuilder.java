package com.example.dagger.model.retrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServiceBuilder {
//    private static final String url = "https://reqres.in/";
    public String url;
    public ServiceBuilder(String url){
        this.url = url;
    }

    @Singleton
    @Provides
    public OkHttpClient.Builder provideClientBuilder(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            return httpClient;
    }
    @Singleton
    @Provides
    public Retrofit provideRetrofit(){
         Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create());

         Retrofit retrofit = builder.build();
        return retrofit;
    }
}
