package com.example.carrental.Model;

public class User {
    public int id;
    public String name;
    public String email;
    public String phone;
    public String address;
    public String password;
    public int no_rents;
    public int role;

    public boolean isAdmin() {
        if (this.role == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getNo_rents() {
        return no_rents;
    }

    public void setNo_rents(int no_rents) {
        this.no_rents = no_rents;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
