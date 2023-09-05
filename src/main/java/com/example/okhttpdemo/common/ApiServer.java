package com.example.okhttpdemo.common;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiServer {
    @POST("/simpleWeather/query")
    Call<BaseModel> getWeather();
}
