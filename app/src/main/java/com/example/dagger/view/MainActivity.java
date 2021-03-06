package com.example.dagger.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.dagger.CustomApp;
import com.example.dagger.model.retrofit.Data;
import com.example.dagger.model.retrofit.EndPoints;
import com.example.dagger.model.retrofit.Root;
import com.example.dagger.viewModel.MainViewModel;
import com.example.dagger.model.helper.MyAdapter;
import com.example.dagger.R;
import com.example.dagger.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    public LinearLayoutManager manager;
    public MainViewModel mainViewModel;
    ActivityMainBinding binding;
    boolean isScrolling = false;
    int i = 1;
    public List<Data> User = new ArrayList<>();
    int page = 1, currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.init(this.getApplication());
        mainViewModel.getUsers(String.valueOf(page));

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setIsLoading(true);
        manager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(manager);

        myAdapter = new MyAdapter(MainActivity.this, User);
                    binding.recyclerView.setAdapter(myAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = true;
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();
                if(isScrolling && totalItems == scrollOutItems + currentItems){
                        if(page <= mainViewModel.totalpage) {
                            page = ++page;
                            binding.setIsBottomLoading(true);
                            mainViewModel.getUsers(String.valueOf(page));
                        }
                }
            }
        });

        mainViewModel.Users.observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                binding.setIsLoading(false);
                binding.setIsBottomLoading(false);
                User.addAll(data);
                myAdapter.notifyDataSetChanged();
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setIsEnabled(false);
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
            binding.setIsEnabled(true);
    }
}