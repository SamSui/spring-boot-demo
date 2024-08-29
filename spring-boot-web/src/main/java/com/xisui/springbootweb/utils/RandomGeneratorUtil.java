package com.xisui.springbootweb.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class RandomGeneratorUtil {
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char ch = str.charAt((int) Math.floor(Math.random() * str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        getRandomNumber();
    }

    public static String getRandomNumber() throws UnsupportedEncodingException {
        // 使用默认的RandomGenerator
        RandomGenerator defaultRandom = RandomGeneratorFactory.of("L64X1024MixRandom").create();
        System.out.println("Random number: " + defaultRandom.nextInt());


        // 使用指定的RandomGenerator
        RandomGenerator xoroshiroRandom = RandomGeneratorFactory.of("Xoroshiro128PlusPlus").create();
        System.out.println("Another random number: " + xoroshiroRandom.nextLong());
        byte[] bytes = new byte[10];
        defaultRandom.nextBytes(bytes);
        System.out.println("Another random number2: " + new String(bytes, StandardCharsets.UTF_8));

        return "success";
    }
}
