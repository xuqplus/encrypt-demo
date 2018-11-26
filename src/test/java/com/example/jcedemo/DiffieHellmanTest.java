package com.example.jcedemo;

import org.junit.Test;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class DiffieHellmanTest {

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
        byte[] aSecretkey;

        byte[] bPublickey;
        byte[] bPrivatekey;
        byte[] bSecretkey;

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("dh");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();
        aPublickey = dhPublicKey.getEncoded();
        aPrivatekey = dhPrivateKey.getEncoded();

        log(aPublickey);
        log(aPrivatekey);

        KeyFactory keyFactory1 = KeyFactory.getInstance("dh");
        X509EncodedKeySpec x509EncodedKeySpec1 = new X509EncodedKeySpec(aPublickey);
        DHPublicKey dhPublicKeyTmp = (DHPublicKey) keyFactory1.generatePublic(x509EncodedKeySpec1);
        DHParameterSpec dhParameterSpec1 = dhPublicKeyTmp.getParams();

        KeyPairGenerator keyPairGenerator1 = KeyPairGenerator.getInstance("dh");
        keyPairGenerator1.initialize(dhParameterSpec1);
        KeyPair keyPair1 = keyPairGenerator1.generateKeyPair();
        DHPublicKey dhPublicKey1 = (DHPublicKey) keyPair1.getPublic();
        DHPrivateKey dhPrivateKey1 = (DHPrivateKey) keyPair1.getPrivate();
        bPublickey = dhPublicKey1.getEncoded();
        bPrivatekey = dhPrivateKey1.getEncoded();

        log(bPublickey);
        log(bPrivatekey);

        aSecretkey = getSecretKey(aPublickey, bPrivatekey);
        bSecretkey = getSecretKey(aPublickey, bPrivatekey);

        log(aSecretkey);
        log(bSecretkey);
    }

    /**
     * 根据对方的公钥和自己的私钥生成本地密钥
     */
    public static final byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("dh");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey publicKey1 = keyFactory.generatePublic(x509EncodedKeySpec);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        PrivateKey privateKey1 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        KeyAgreement keyAgreement = KeyAgreement.getInstance("dh");
        keyAgreement.init(privateKey1);
        keyAgreement.doPhase(publicKey1, true);
//        -Djdk.crypto.KeyAgreement.legacyKDF=true
        SecretKey secretKey = keyAgreement.generateSecret("aes");
        return secretKey.getEncoded();
    }
}
