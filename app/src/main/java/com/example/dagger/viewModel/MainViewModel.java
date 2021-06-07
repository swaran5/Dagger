package com.example.dagger.viewModel;

import android.app.Application;
import android.content.Context;
import android.telecom.StatusHints;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dagger.CustomApp;
import com.example.dagger.model.helper.MyAdapter;
import com.example.dagger.model.retrofit.Data;
import com.example.dagger.model.retrofit.EndPoints;
import com.example.dagger.model.retrofit.Root;
import com.example.dagger.model.retrofit.ServiceBuilder;
import com.example.dagger.view.MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainViewModel extends ViewModel {
    @Inject
    EndPoints endPoints;
    public int totalpage;
    public MutableLiveData<List<Data>> Users = new MutableLiveData<>();

    public void init(Application application) {
        ((CustomApp)application).getNetworkComponent().inject(MainViewModel.this);
    }

    public MutableLiveData<List<Data>> getUsers(String page){

            Call<Root> call = endPoints.getUsers(page);
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
