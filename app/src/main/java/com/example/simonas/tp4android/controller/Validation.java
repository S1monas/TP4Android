package com.example.simonas.tp4android.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Simonas on 2018.04.04.
 */

public class Validation {
    private static final String VALID_TOURNAMENT_ADD_REGEX ="^[a-zA-Z]|\\d+[$@]$";
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

    public static boolean isValidTournamentForAdd(String tournament){
        Pattern tournamentNamePattern = Pattern.compile(VALID_TOURNAMENT_ADD_REGEX);
        Matcher tournamentNameMatcher = tournamentNamePattern.matcher(tournament);
        return tournamentNameMatcher.find();
    }
}


