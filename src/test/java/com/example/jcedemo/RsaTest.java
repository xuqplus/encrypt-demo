package com.example.jcedemo;

import org.junit.Test;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RsaTest {

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

        byte[] aPublickey;
        byte[] aPrivatekey;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("rsa");
        keyPairGenerator.initialize(4096);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        aPublickey = rsaPublicKey.getEncoded();
        aPrivatekey = rsaPrivateKey.getEncoded();

        log(aPublickey);
        log(aPrivatekey);

        Cipher cipher = Cipher.getInstance("rsa");
        // 私钥加密公钥解密
        cipher.init(Cipher.ENCRYPT_MODE, rsaPrivateKey);
        byte[] encrypt = cipher.doFinal(message.getBytes());
        log(encrypt);

        cipher.init(Cipher.DECRYPT_MODE, rsaPublicKey);
        byte[] bytes = cipher.doFinal(encrypt);
        log(new String(bytes));

        // 公钥加密私钥解密
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        encrypt = cipher.doFinal(message.getBytes());
        log(encrypt);

        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        bytes = cipher.doFinal(encrypt);
        log(new String(bytes));
    }
}
