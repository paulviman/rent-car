package com.example.carrental.Services;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;

public class Encrypt {

    public String encrypt(String password){
        String encodedPassword = Base64.encodeBase64URLSafeString(password.getBytes());
        return encodedPassword;
    }
    public String decrypt(String password){

        byte[] decodedBytes = Base64.decodeBase64(password.getBytes());
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
}
