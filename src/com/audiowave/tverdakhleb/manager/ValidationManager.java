package com.audiowave.tverdakhleb.manager;

public class ValidationManager {
    private static final String NAME_REGEX  ="^[\\p{Upper}]+[\\p{Alpha}]*";
    private static final String MAIL_REGEX  ="^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String LOGIN_REGEX ="^[\\p{Alpha}]+\\w{2,}";
    private static final String PASSWORD_REGEX ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";

    public boolean checkLogin(String login){
        return login.matches(LOGIN_REGEX);
    }

    public boolean checkPassword(String password){
       return password.matches(PASSWORD_REGEX);
    }

    public boolean checkMail(String mail){
        return mail.matches(MAIL_REGEX);
    }

    public boolean checkName(String name){
        return name.matches(NAME_REGEX);
    }
}
