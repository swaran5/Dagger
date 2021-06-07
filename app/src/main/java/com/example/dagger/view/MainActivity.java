package com.example.dagger.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.dagger.model.retrofit.Data;
import com.example.dagger.viewModel.MainViewModel;
import com.example.dagger.model.helper.MyAdapter;
import com.example.dagger.R;
import com.example.dagger.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyAdapter myAdapter;
    public LinearLayoutManager manager;
    MainViewModel mainViewModel;
    boolean isScrolling = false;
    public List<Data> User = new ArrayList<>();
    int page = 1, currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

            mainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);
        binding.setIsLoading(true);
        mainViewModel.getUsers(String.valueOf(page));

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



    }
}