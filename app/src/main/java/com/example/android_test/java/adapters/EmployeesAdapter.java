package com.example.android_test.java.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_test.R;
import com.example.android_test.java.model.CompanyModels;
import com.example.android_test.java.view.Communicator;

import java.util.ArrayList;

public class EmployeesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CompanyModels.Employee> employees;
    private Communicator communicator;

    public EmployeesAdapter(Communicator communicator, ArrayList<CompanyModels.Employee> employees){
        this.employees = employees;
        this.communicator = communicator;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeesAdapter.EmployeesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof EmployeesViewHolder){
            EmployeesViewHolder employeesViewHolder = (EmployeesViewHolder) holder;
            employeesViewHolder.empName.setText(employees.get(position).name);
            employeesViewHolder.empTitle.setText(employees.get(position).title);
            String url = employees.get(position).profilePicUrl;

            Glide.with(communicator.getContext())
                    .load(url)
                    .circleCrop()
                    .into(employeesViewHolder.empPhoto);

            ((EmployeesViewHolder) holder).bindData(employees.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return employees != null ? employees.size() : 0;
    }

    class EmployeesViewHolder extends RecyclerView.ViewHolder {

        private ImageView empPhoto;
        private TextView empName, empTitle;

        EmployeesViewHolder(@NonNull View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.employee_name);
            empTitle = itemView.findViewById(R.id.employee_title);
            empPhoto = itemView.findViewById(R.id.employee_photo);
        }

        void bindData(CompanyModels.Employee employee){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.getEmployeesViewModel().onEmployeeClicked(employee);
                }
            });
        }
    }
}
