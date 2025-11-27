package org.example;

import java.util.Random;

public class BaseTest {
    
    public static String getRandomEmail() {
        return "test" + System.currentTimeMillis() + "@gmail.com";
    }
    
    public static String getRandomName() {
        return "User" + System.currentTimeMillis();
    }
    
    public static String getRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }
}
