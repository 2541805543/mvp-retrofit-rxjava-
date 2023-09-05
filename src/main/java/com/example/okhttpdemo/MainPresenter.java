package com.example.okhttpdemo;


import com.example.okhttpdemo.common.BasePresenter;
import com.example.okhttpdemo.common.BaseModel;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(IMainView baseView) {
        super(baseView);
    }
}
