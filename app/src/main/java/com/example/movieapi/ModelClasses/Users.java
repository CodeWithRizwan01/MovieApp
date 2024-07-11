package com.example.movieapi.ModelClasses;

public class Users {
    String name,email,password,pImage;

    public Users() {
    }
    public Users(String name, String email, String password, String pImage) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.pImage = pImage;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }


}
