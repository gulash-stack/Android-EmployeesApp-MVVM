package com.example.android_test.java.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/")
    Call<CompanyModels.Company> listEmployees();

    String BASE_URL = "https://monday-mobile-test.herokuapp.com";
}
