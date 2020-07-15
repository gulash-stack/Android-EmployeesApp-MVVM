package com.example.android_test.java.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.android_test.java.EmployeeApp;
import com.example.android_test.java.model.ApiServiceSingleton;
import com.example.android_test.java.model.CompanyEmployeesResponse;
import com.example.android_test.java.model.CompanyModels;
import com.example.android_test.java.model.FakeInterceptor;
import com.example.android_test.java.model.ResponseObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeesRepository {

    private MutableLiveData<List<CompanyModels.Employee>> mEmployeeList = new MutableLiveData<>();

    public MutableLiveData<List<CompanyModels.Employee>> getEmployeeList() {
        if(mEmployeeList.getValue() == null){

            /** un-mark this function to fake the api callback if the regular response doesn't work */
            //useFakeResponse();

            Call<CompanyModels.Company> employeeCall = ApiServiceSingleton.getInstance().listEmployees();
            employeeCall.enqueue(new Callback<CompanyModels.Company>() {
                @Override
                public void onResponse(Call<CompanyModels.Company> call, Response<CompanyModels.Company> response) {
                    if(response.isSuccessful()){
                        CompanyModels.Company company = response.body();
                        if(company != null){
                            List<CompanyModels.Employee> employees = company.employees;
                            mEmployeeList.setValue(employees);
                        }
                    }
                }

                @Override
                public void onFailure(Call<CompanyModels.Company> call, Throwable t) {

                }
            });
        }
        return mEmployeeList;
    }

    /**
     * The function simulates the response you should get from the server.
     */
    private void useFakeResponse() {
        EmployeeApp.useFakeResponse = true;
        ResponseObject responseObject = new ResponseObject();
        responseObject.setReturnCode(200);
        responseObject.setType(ResponseObject.ResponseType.JSON);
        responseObject.setResponseBodyString(new CompanyEmployeesResponse().getRandomResponse());
        FakeInterceptor.addTest("-mobile-", responseObject);
    }
}
