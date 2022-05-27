package com.learn.robot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtilsNew{
    private static final Logger LOGGER= LoggerFactory.getLogger(RSAUtilsNew.class);

    private static final int MAX_DECRYPT_BLOCK = 256;

    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] bytes = cipher.doFinal(content);
        int inputLen = content.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(content, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(content, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] bytes = out.toByteArray();
        out.close();
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }


//    public static void main(String[] args) {
//        try {
//            //===============生成公钥和私钥，公钥传给客户端，私钥服务端保留==================
//            //生成RSA公钥和私钥，并Base64编码
////            KeyPair keyPair = getKeyPair();
////            String publicKeyStr = getPublicKey(keyPair);
////            String privateKeyStr = getPrivateKey(keyPair);
////            System.out.println("RSA公钥Base64编码:" + publicKeyStr);
////            System.out.println("RSA私钥Base64编码:" + privateKeyStr);
//
//            //=================客户端=================
//            //hello, i am infi, good night!加密
//            String message = "{\"ditch\":\"1\",\"order_type\":\"2\",\"deal_type\":\"5\",\"source\":\"1\",\"msisdn\":\"57494501\",\"recharge_amount\":\"111\",\"email\":\"a@q.com\",\"is2HB\":0,\"account_no\":\"\",\"channel\":1,\"trench\":\"1\"}";
//            String pk = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwfHXVpcI+J2CbnAqHNeIfzksql+L0/VX" +
//                    "lS4BsS40ohCB4TRTSGYUaKQgxfHgOdPnVM2W9eWZx7KrDygOFNq0H45armNcRtIsGYN5BufOe857" +
//                    "HkZPAA+35FSv+HlGiMIKmSdoAy6L5iAu3GtzNR6Mh2Dce33rAjKerSMFcMdgcRHVfEYT55YWNUDl" +
//                    "OwWHAmWarnZeoVIYITbCAzlcoYkdS20j7fvZ1wdP98lZhTeUaFyaa43sRMo3u1eWFCorxqEHMVmh" +
//                    "0ys0m5DYsXzQ+VUQShwXaQX9VMpvS1Ruo22gEIkeVf+Kod8BIs7chWeSPS58798Skewq9A4U54I9" +
//                    "ZWum4wIDAQAB";
//            //将Base64编码后的公钥转换成PublicKey对象
//            PublicKey publicKey =string2PublicKey(pk);
//            //用公钥加密
//            byte[] publicEncrypt = publicEncrypt(message.getBytes(), publicKey);
//            //加密后的内容Base64编码
//            String byte2Base64 = byte2Base64(publicEncrypt);
//            System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);
//
//
//            //##############    网络上传输的内容有Base64编码后的公钥 和 Base64编码后的公钥加密的内容     #################
//
//
//
//            //===================服务端================
//            //将Base64编码后的私钥转换成PrivateKey对象
////            PrivateKey privateKey = string2PrivateKey(privateKeyStr);
////            //加密后的内容Base64解码
////            byte[] base642Byte = base642Byte(byte2Base64);
////            //用私钥解密
////            byte[] privateDecrypt = privateDecrypt(base642Byte, privateKey);
////            //解密后的明文
////            System.out.println("解密后的明文: " + new String(privateDecrypt));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) throws Exception{
//        String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDB8ddWlwj4nYJucCoc14h/OSyq" +
//                "X4vT9VeVLgGxLjSiEIHhNFNIZhRopCDF8eA50+dUzZb15ZnHsqsPKA4U2rQfjlquY1xG0iwZg3kG" +
//                "5857znseRk8AD7fkVK/4eUaIwgqZJ2gDLovmIC7ca3M1HoyHYNx7fesCMp6tIwVwx2BxEdV8RhPn" +
//                "lhY1QOU7BYcCZZqudl6hUhghNsIDOVyhiR1LbSPt+9nXB0/3yVmFN5RoXJprjexEyje7V5YUKivG" +
//                "oQcxWaHTKzSbkNixfND5VRBKHBdpBf1Uym9LVG6jbaAQiR5V/4qh3wEiztyFZ5I9Lnzv3xKR7Cr0" +
//                "DhTngj1la6bjAgMBAAECggEBALSGMZSN3XHc2u2FRg22qX0ScPrdGMBgm/dl7XlIswEvMFW5aNP5" +
//                "AIIuU7ssWzQ4hBkxjJSx9KQnMIZZGFJdCFBfJxUvs1LQiu/sqpi86DvhhEQ32Y0LFHZaRbmY2Qiu" +
//                "5UKNpYlgqCO6TSQqeAA3wUK57ECegkN3bq9l68iALMrQ2EGuxpywEIFJYU0Y3KcK1Pg0AFIYnx9h" +
//                "SdUFtE3BDIZPct+ch+Q4xLnjVksG5e0mIsRzEKtmRzntzAmXZj4lBg8KZNH+HoZ89FD0YxRxRzuj" +
//                "Qt7Ra78HN2yzayustap9RurscYrKXeMU1KqtRz/5fKvPw52TgN60b/zc49nwpiECgYEA9DZFS7OJ" +
//                "ASnykQVkpObLK83eNl6/fF+3CFo/nPIg6F80KdlfVffQZmK4ONoTMpC906/VOj184dRy3zA59oWF" +
//                "To3pYGP2zutDat/5qDDu1XXr8KHFIsCpyJ4iWGVk94M2b4S1SMfQd0X0rwX+7qTJ7YBY79tqCkxD" +
//                "a3VSWRQaBMkCgYEAy05qxQy8EyIcVjaksd1CzvMWvaZOA6vxjFxfgqb/9DscLMme9anQfJPVNUFk" +
//                "QA0Z6a4TK0hcqKqEXpAxkyAcA59qkMxumgajAr9FYqzm3WaSPYPdBetHNReXoMIxcY4TDGI7/sso" +
//                "x/dQGlAfMvqZIeQWIZUmL9rGT7NqQ2rKQEsCgYBapYJXNQ7XlcBpv/y5kMk9Oy8OM2EUQ1rnCMiv" +
//                "8tfwKNZt6jeO62s5dc83mwqhLESpvIyDD6u5D2iMG5SIQstWeypzEufbqkvd8cDzgyZDqX+oVAOe" +
//                "gLEEvF23PfuaO+QKxNGdcD2a6CaIiMs1vYkOVSOt9F2J8QDddDDay7FUsQKBgEBLnmyO6HPMl9WU" +
//                "FmXBqb01F34GuCllGVDgbbwuAEhG4VQwtzrN54deYVEkSwYOnqFOUfWgPt2Af7hANc1nnDRxGAyO" +
//                "+nNvoeHeh5z7PU/LfnhB2YZjr/NUcLmAcI6O9es2re6YZV9IEWcg65z9r6wOLAmW16rOr2TJQtPr" +
//                "U+cdAoGAaBuQKkTHRiAtOXnz/2TEL3IBl6ioKrsh/aYroaMBOPP0B1qayZ3ecYOhCnYAFYl+jYS7" +
//                "9MYaDH8IaF1ZkUkeOVI9uRP4fGdqSuVznoqdJiiY3f1iZ+eoWhghZ3iNOGR2uunklTTpKWKSFyZR" +
//                "JhlZU5tllPLQbgzsFQBoV4xLLrw=";
//        //将Base64编码后的私钥转换成PrivateKey对象
//        PrivateKey privateKey = string2PrivateKey(privateKeyStr);
//        //加密后的内容Base64解码
//        byte[] base642Byte = base642Byte("bo6TZjEgKW7EMoIF+cyHQhOyHtw7YADdlJKARi6gcB7+hbHTfzPFEr61Fp79N6U2T4mSSQGnxiVKV62ZxNaevPhlDfdflWSO1aXws2hZ+Dpizbj9WvXmPXrh6fN6OZ2yaC5zesIjjCiyvDO05L5sujVfmhNkLZljVyvPa3ivpNI0oTyf6PUsybA9ENcsgUf/m49RndO/nyeXe3Wl3KqSgSziGEMHPz/pGMPhCEYA94BfQk7ekqSC6mhnhpfK9k7QrsFjdby8cNLW6dWBk0Eom/HIbUTPJf7I4Bh5XbkEqQpTsDgIxe0HKdDET/a4E9aU7rTUdCHeSSGGEAytYGluog==");
//        //用私钥解密
//        byte[] privateDecrypt = privateDecrypt(base642Byte, privateKey);
//        String deurl = URLDecoder.decode(new String(privateDecrypt),"UTF-8");
//        //解密后的明文
//        System.out.println("解密后的明文: " + deurl);
//    }

}
