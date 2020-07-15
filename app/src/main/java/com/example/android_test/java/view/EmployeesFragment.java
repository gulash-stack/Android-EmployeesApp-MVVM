package com.example.android_test.java.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_test.R;
import com.example.android_test.java.adapters.EmployeesAdapter;
import com.example.android_test.java.model.CompanyModels;

import java.util.ArrayList;

public class EmployeesFragment extends Fragment {

    private static final String ARGS1 = "employees";
    private static final String ARGS2 = "manager";
    private ArrayList<CompanyModels.Employee> mEmployees;
    private CompanyModels.Employee mManager;
    private Communicator mCommunicator;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCommunicator = (Communicator) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mEmployees = getArguments().getParcelableArrayList(ARGS1);
            mManager = getArguments().getParcelable(ARGS2);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_employees, container, false);
        RecyclerView employeesRecycler = root.findViewById(R.id.employee_list);

        if(mManager != null){
            View managerContainer = root.findViewById(R.id.manager_container);
            managerContainer.setVisibility(View.VISIBLE);
            ImageView managerPic = root.findViewById(R.id.manager_photo);
            TextView managerTitle = root.findViewById(R.id.manager_title);
            TextView managerName = root.findViewById(R.id.manager_name);
            managerTitle.setText(mManager.title);
            managerName.setText(mManager.name);

            Glide.with(this)
                    .load(mManager.profilePicUrl)
                    .circleCrop()
                    .into(managerPic);
        }

        EmployeesAdapter employeesAdapter = new EmployeesAdapter(mCommunicator, mEmployees);
        employeesRecycler.setAdapter(employeesAdapter);

        return root;
    }

    static EmployeesFragment newInstance(@Nullable CompanyModels.Employee manager, @Nullable ArrayList<CompanyModels.Employee> employees) {
        EmployeesFragment fragment = new EmployeesFragment();
        Bundle arguments = new Bundle();
        if(employees != null) {
            arguments.putParcelableArrayList(ARGS1, employees);
            arguments.putParcelable(ARGS2,manager);
            fragment.setArguments(arguments);
        }
        return fragment;
    }
}
