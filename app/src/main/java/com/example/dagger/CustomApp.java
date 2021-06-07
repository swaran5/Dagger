package com.example.dagger;

import android.app.Application;

import com.example.dagger.model.retrofit.ServiceBuilder;

public class CustomApp extends Application {
    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                                .serviceBuilder(new ServiceBuilder("https://reqres.in/")).build();
    }

    public NetworkComponent getNetworkComponent(){
        return networkComponent;
    }
}
