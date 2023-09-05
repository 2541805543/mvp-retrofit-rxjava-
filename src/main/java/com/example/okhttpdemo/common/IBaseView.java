package com.example.okhttpdemo.common;

import android.content.Context;

public interface IBaseView {
    Context getContext();
    void showLoading();
    void hideLoading();

    void showError(String msg);

    void onErrorCode(BaseModel model);

    void showLoadingFileDialog();

    void hideLoadingFileDialog();

    void onProgress(long totalSize, long downSize);
}
