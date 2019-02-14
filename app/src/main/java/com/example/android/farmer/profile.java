package com.example.android.farmer;

public class profile {

    private String Age,Gender,email,mobileNumber,name,state;
    public profile(){}

    public profile(String Age, String Gender, String email, String mobileNumber, String name, String state) {
        this.Age = Age;
        this.Gender = Gender;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.name = name;
        this.state = state;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
