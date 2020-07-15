package com.example.android_test.java.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.android_test.R;
import com.example.android_test.java.model.CompanyModels;
import com.example.android_test.java.viewModel.EmployeesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Communicator {

    private EmployeesViewModel mEmployeesViewModel;
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgress = findViewById(R.id.progress);

        initEmployeesViewModel();

    }

    private void initEmployeesViewModel() {
        mEmployeesViewModel = new ViewModelProvider(this).get(EmployeesViewModel.class);
        mEmployeesViewModel.getAllEmployees().observe(this, new Observer<List<CompanyModels.Employee>>() {
            @Override
            public void onChanged(List<CompanyModels.Employee> employees) {
                if(employees != null){
                    mEmployeesViewModel.setTopLevelManagers(employees);

                }
            }
        });

        mEmployeesViewModel.getAllManagers().observe(this, new Observer<List<CompanyModels.Employee>>() {
            @Override
            public void onChanged(List<CompanyModels.Employee> employees) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, EmployeesFragment.newInstance(null,(ArrayList<CompanyModels.Employee>) employees), "second").commit();
            }
        });

        mEmployeesViewModel.getCurrentManager().observe(this, new Observer<CompanyModels.Employee>() {
            @Override
            public void onChanged(CompanyModels.Employee employee) {
                if(employee != null){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, EmployeesFragment.newInstance(employee, (ArrayList<CompanyModels.Employee>) mEmployeesViewModel.getMangerEmployees().getValue()), "second").commit();
                }
            }
        });

        mEmployeesViewModel.getCurrentEmployee().observe(this, new Observer<CompanyModels.Employee>() {
            @Override
            public void onChanged(CompanyModels.Employee employee) {
                if(employee != null){
                    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, EmployeesFragment.newInstance(employee, new ArrayList<>()), "second").commit();
                }
            }
        });

        mEmployeesViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                mLoadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE );
            }
        });
    }

    @Override
    public EmployeesViewModel getEmployeesViewModel(){
        return mEmployeesViewModel;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        mEmployeesViewModel.resetCurrentEmployee();
        super.onBackPressed();
    }
}
