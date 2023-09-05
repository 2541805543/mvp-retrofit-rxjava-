package com.example.okhttpdemo;

import android.os.Bundle;

import com.example.okhttpdemo.common.BaseActivity;
import com.example.okhttpdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding,IMainView,MainPresenter> implements IMainView {

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {
        binding.getButton.setOnClickListener(v -> {

        });
    }

}