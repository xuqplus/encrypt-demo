package com.example.jcedemo;

import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Test {

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

        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder base64Decoder = new BASE64Decoder();

        String encrypt = base64Encoder.encode(message.getBytes());
        byte[] decodeBuffer = base64Decoder.decodeBuffer(encrypt);

        log(encrypt);
        log(new String(decodeBuffer));
    }
}
