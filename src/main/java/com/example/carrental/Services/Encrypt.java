package com.example.carrental.Services;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;

public class Encrypt {

    public String encrypt(String password){
        String encodedPassword = Base64.encodeBase64URLSafeString(password.getBytes());
        return encodedPassword;
    }
    public String decrypt(String password){
        String decodedPassword = Arrays.toString(Base64.decodeBase64(password.getBytes()));
        return decodedPassword;
    }
}
