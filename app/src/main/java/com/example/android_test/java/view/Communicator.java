package com.example.android_test.java.view;

import android.content.Context;

import com.example.android_test.java.viewModel.EmployeesViewModel;

public interface Communicator {
    EmployeesViewModel getEmployeesViewModel();
    Context getContext();
}
