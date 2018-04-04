package com.example.simonas.tp4android;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simonas on 2018.04.04.
 */

public class Validation {

    private static final String VALID_ID_REGEX ="^[0-9]{1,7}$";
    private static final String VALID_NAME_ADD_REGEX ="^[A-Za-z ]{1,1000}$";
    private static final String VALID_SEARCHNAME_SEARCH_REGEX ="^[A-Za-z]{1,1000}$";
    private static final String VALID_CREDENTIALS_REGEX ="^[A-Za-z0-9.-]{5,13}$";
    private static final String VALID_EMAIL_ADDRESS_REGEX = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";


    public static boolean isValidCredentials(String credentials){
        Pattern credentialsPattern = Pattern.compile(VALID_CREDENTIALS_REGEX);
        Matcher credentialsMatcher = credentialsPattern.matcher(credentials);
        return credentialsMatcher.find();
    }

    public static boolean isValidEmail(String email){
        Pattern emailPattern = Pattern.compile(VALID_EMAIL_ADDRESS_REGEX);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.find();
    }
  /*  public static boolean isValidID(String ID){
        Pattern IDPattern = Pattern.compile(VALID_ID_REGEX);
        Matcher IDMatcher = IDPattern.matcher(ID);
        return IDMatcher.find();
    }


    public static boolean isValidNameForAdd(String username){
        Pattern NamePattern = Pattern.compile(VALID_NAME_ADD_REGEX);
        Matcher NameMatcher = NamePattern.matcher(username);
        return NameMatcher.find();
    }


    public static boolean isValidSearchNameForSearch(String SearchName){
        Pattern SearchNamePattern = Pattern.compile(VALID_SEARCHNAME_SEARCH_REGEX);
        Matcher SearchNameMatcher = SearchNamePattern.matcher(SearchName);
        return SearchNameMatcher.find();
    }*/
}


