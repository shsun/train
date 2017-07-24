package com.demo.sys;

public class LoginUserEntry {

    private String name;
    private String password;

    public LoginUserEntry(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
