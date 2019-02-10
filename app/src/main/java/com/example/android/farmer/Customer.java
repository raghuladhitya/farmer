package com.example.android.farmer;

public class Customer {
    private String CustomerName,CustomerPhoneNumber;

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return CustomerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        CustomerPhoneNumber = customerPhoneNumber;
    }

    public Customer(String customerName, String customerPhoneNumber) {

        CustomerName = customerName;
        CustomerPhoneNumber = customerPhoneNumber;
    }
}
