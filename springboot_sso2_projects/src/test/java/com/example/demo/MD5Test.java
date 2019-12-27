package com.example.demo;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;

public class MD5Test {
    @Test
    public void  fun01() throws Exception{
        Md5Hash md5Hash = new Md5Hash("123456","123456",2019);

        System.out.println(md5Hash);
    }

}
