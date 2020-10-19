package com.example.youthance.Model;

public class LoginModel {
    private String name, mobile, email, password;
    private String responseMessage;
    private int successCode;

    public LoginModel(String name, String mobile, String email, String password) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

    public LoginModel(String name, String mobile, String email, String password, String responseMessage, int successCode) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.responseMessage = responseMessage;
        this.successCode = successCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public int getSuccessCode() {
        return successCode;
    }

    public void setSuccessCode(int successCode) {
        this.successCode = successCode;
    }
}
