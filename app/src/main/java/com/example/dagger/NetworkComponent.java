package com.example.dagger;

import com.example.dagger.model.retrofit.ServiceBuilder;
import com.example.dagger.view.MainActivity;
import com.example.dagger.viewModel.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ServiceBuilder.class})
public interface NetworkComponent {
    public void inject(MainViewModel mainViewModel);
}
