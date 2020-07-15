package com.example.android_test.java.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CompanyModels {

    public static class Company {
        @SerializedName("company_name")
        public final String companyName;
        public final List<Employee> employees;

        public Company(String companyName, List<Employee> employees) {
            this.companyName = companyName;
            this.employees = employees;
        }
    }

    public static class Employee implements Parcelable {
        public final int id;
        public final String name;
        public final @Nullable String phone;
        public final @Nullable String email;
        public final String title;
        @SerializedName("profile_pic")
        public final String profilePicUrl;
        @SerializedName("manager_id")
        public final @Nullable Integer managerId;
        public final @Nullable String department;

        public Employee(int id,
                        String name,
                        @Nullable String phone,
                        @Nullable String email,
                        String title,
                        String profilePicUrl,
                        @Nullable Integer managerId,
                        @Nullable String department){
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.title = title;
            this.profilePicUrl = profilePicUrl;
            this.managerId = managerId;
            this.department = department;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.email);
            dest.writeString(this.title);
            dest.writeString(this.profilePicUrl);
            dest.writeValue(this.managerId);
            dest.writeString(this.department);
        }

        protected Employee(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.email = in.readString();
            this.title = in.readString();
            this.profilePicUrl = in.readString();
            this.managerId = (Integer) in.readValue(Integer.class.getClassLoader());
            this.department = in.readString();
        }

        public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
            @Override
            public Employee createFromParcel(Parcel source) {
                return new Employee(source);
            }

            @Override
            public Employee[] newArray(int size) {
                return new Employee[size];
            }
        };
    }
}
