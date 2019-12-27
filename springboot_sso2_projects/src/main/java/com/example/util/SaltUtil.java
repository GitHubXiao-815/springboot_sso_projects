package com.example.util;

import java.security.SecureRandom;

//生成盐
public  class SaltUtil {
    public static String getSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        return new String(bytes);
    }

    public static void main(String[] args) {
        String salt = SaltUtil.getSalt();
        System.out.println(salt);
    }
}
