package com.example.jcedemo;

import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DesedeTest {

    private static final void log(Object object) {
        System.out.println(String.valueOf(object));
    }

    private static final void log(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            int i = 0xff & b;
            if (i < 16) sb.append(0);
            sb.append(Integer.toHexString(i));
        }
        System.out.println(sb.toString() + "   " + sb.length());
    }

    @Test
    public void test() throws Exception {
        String message = "hello, 世界.";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("desede");
        keyGenerator.init(168);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytes = secretKey.getEncoded();
        log(bytes);

        Cipher cipher = Cipher.getInstance("desede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encrypt = cipher.doFinal(message.getBytes());
        log(encrypt);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        log(new String(cipher.doFinal(encrypt)));
    }
}
