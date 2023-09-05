package com.example.okhttpdemo.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseActivity<D extends ViewBinding, V extends IBaseView, T extends BasePresenter<V>> extends AppCompatActivity implements IBaseView{
    protected D binding;
    protected T presenter;
    private ProgressDialog dialog;
    private Toast toast;

    protected abstract T createPresenter();
//    protected LifecycleProvider<Lifecycle.Event> mLifecycleProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        // 隐藏标题栏
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        // 沉浸效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        init();
        initData(savedInstanceState);
        initListener();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void init() {
        // 通过反射绑定dataBinding
        Type superclass = getClass().getGenericSuperclass();
        try {
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (D) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
//            mPresenter = getInstance(this, 2);
//            mPresenter.setLifecycleProvider(mLifecycleProvider);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
        }
        presenter = createPresenter();
//        mPresenter = initPresenter();
//        if (mPresenter != null) {
//            mPresenter.attachView((V) this);
//        }
    }

    private <T> T getInstance(Object o, int i) {
        String error = "";
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (Fragment.InstantiationException e) {
//            e.printStackTrace();
            error = e.toString();
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
            error = e.toString();
        } catch (ClassCastException e) {
//            e.printStackTrace();
            error = e.toString();
        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
            error = e.toString();
        }
        return null;
    }

    /**
     * 初始化数据 进行网络操作等
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 所有的点击事件
     */
    protected abstract void initListener();

    // 设置返回按钮的监听事件
    private long exitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 5000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            } else {

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    /**
     * @param s
     */
    public void showtoast(String s) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void showLoadingDialog() {

        if (dialog == null) {
            dialog = new ProgressDialog(getContext());
        }
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }


    @Override
    public void showError(String msg) {
        showtoast(msg);
    }

    @Override
    public void onErrorCode(BaseModel model) {
    }

    @Override
    public void showLoadingFileDialog() {
//        showFileDialog();
    }

    @Override
    public void hideLoadingFileDialog() {
//        hideFileDialog();
    }

    @Override
    public void onProgress(long totalSize, long downSize) {
        if (dialog != null) {
            dialog.setProgress((int) (downSize * 100 / totalSize));
        }
    }
}