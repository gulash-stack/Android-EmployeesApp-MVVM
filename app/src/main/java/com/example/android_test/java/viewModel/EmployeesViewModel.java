package com.example.android_test.java.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android_test.java.model.CompanyModels;
import com.example.android_test.java.repository.EmployeesRepository;

import java.util.ArrayList;
import java.util.List;

public class EmployeesViewModel  extends ViewModel {

    private MutableLiveData<List<CompanyModels.Employee>> mAllEmployeeList;

    private MutableLiveData<List<CompanyModels.Employee>> mManagers = new MutableLiveData<>();
    private MutableLiveData<List<CompanyModels.Employee>> mMangerEmployees = new MutableLiveData<>();
    private MutableLiveData<CompanyModels.Employee> mCurrentManager = new MutableLiveData<>();
    private MutableLiveData<CompanyModels.Employee> mCurrentEmployee = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    public EmployeesViewModel(){
        EmployeesRepository employeesRepository = new EmployeesRepository();
        mAllEmployeeList = employeesRepository.getEmployeeList();
        mIsLoading.setValue(true);
    }

    public void setTopLevelManagers(List<CompanyModels.Employee> employees) {
        if(employees != null){
            ArrayList<CompanyModels.Employee> managerList = new ArrayList<>();
            for(CompanyModels.Employee employee : employees){
                if(employee.managerId == null){
                    managerList.add(employee);
                }
            }
            mIsLoading.setValue(false);
            mManagers.setValue(managerList);
        }

    }

    public LiveData<List<CompanyModels.Employee>> getAllEmployees(){
        return mAllEmployeeList;
    }

    public LiveData<List<CompanyModels.Employee>> getAllManagers(){
        return mManagers;
    }

    public void onEmployeeClicked(CompanyModels.Employee emp){
        if(emp != null){
            List<CompanyModels.Employee> allEmployees = mAllEmployeeList.getValue();
            ArrayList<CompanyModels.Employee> manEmployees = new ArrayList<>();
            if (allEmployees != null) {
                for (CompanyModels.Employee employee1 : allEmployees) {
                    if (employee1.managerId != null) {
                        if (emp.id == employee1.managerId) {
                            manEmployees.add(employee1);
                        }
                    }
                }
                if(manEmployees.isEmpty()){
                    mCurrentEmployee.setValue(emp);
                }else{
                    mMangerEmployees.setValue(manEmployees);
                    mCurrentManager.setValue(emp);
                    mCurrentEmployee.setValue(null);
                }
            }
        }
    }

    public LiveData<List<CompanyModels.Employee>> getMangerEmployees() {
        return mMangerEmployees;
    }

    public LiveData<CompanyModels.Employee> getCurrentManager() {
        return mCurrentManager;
    }

    public LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    public LiveData<CompanyModels.Employee> getCurrentEmployee() {
        return mCurrentEmployee;
    }

    public void resetCurrentEmployee() {
        mCurrentEmployee.setValue(null);
    }
}
