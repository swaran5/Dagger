package com.example.dagger;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    public int totalpage;

    public MutableLiveData<List<Data>> Users = new MutableLiveData<>();
    EndPoints request = ServiceBuilder.createService(EndPoints.class);

    public MutableLiveData<List<Data>> getUsers(String page){

            Call<Root> call = request.getUsers(page);
            call.enqueue(new retrofit2.Callback<Root>() {
                @Override
                public void onResponse(Call<Root> call, Response<Root> response) {

                    Users.postValue(response.body().getData());
                    totalpage = response.body().getTotal_pages();


                }

                @Override
                public void onFailure(Call<Root> call, Throwable t) {

                }
            });

        return Users;}
}
