package com.learn.robot.util;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


public class RSAUtils{
    public static final String ENCRYPTION_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * 生成密钥
     */
    public static Map<String, Object> initKey() throws Exception {
        /* 初始化密钥生成器 */
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(1024);

        /* 生成密钥 */
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("PublicKey", publicKey);
        keyMap.put("PrivateKey", privateKey);
        return keyMap;
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("PublicKey");
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key)keyMap.get("PrivateKey");
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * 加密
     */
    public static byte[] encrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtils.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtils.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtils.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm(),new BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 解密
     */
    public static byte[] decrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtils.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtils.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtils.getKey(keyAndFactoryMap);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 生成钥匙
     */
    public static Map<String, Object> generateKeyAndFactory(String keyString, boolean isPublic) throws Exception {
        byte[] keyBytes = Base64Utils.decode(keyString);

        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key key = null;
        if (isPublic) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);
        }

        Map<String, Object> keyAndFactoryMap = new HashMap<String, Object>(2);
        keyAndFactoryMap.put("key", key);
        keyAndFactoryMap.put("keyFactory", keyFactory);

        return keyAndFactoryMap;
    }

    /**
     * 从指定对象中获取钥匙
     */
    public static Key getKey(Map<String, Object> map) {
        if (map.get("key") == null) {
            return null;
        }
        return (Key)map.get("key");
    }

    /**
     * 从指定对象中获取钥匙工厂
     */
    public static KeyFactory getKeyFactory(Map<String, Object> map) {
        if (map.get("keyFactory") == null) {
            return null;
        }
        return (KeyFactory)map.get("keyFactory");
    }

    /**
     * 对信息生成数字签名（用私钥）
     */
    public static String sign(byte[] data, String keyString) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtils.generateKeyAndFactory(keyString, false);
        Key key = RSAUtils.getKey(keyAndFactoryMap);

        PrivateKey privateKey = (PrivateKey)key;

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);

        return Base64Utils.encode(signature.sign());
    }

    /**
     * 校验数字签名（用公钥）
     */
    public static boolean verify(byte[] data, String keyString, String sign) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtils.generateKeyAndFactory(keyString, true);
        Key key = RSAUtils.getKey(keyAndFactoryMap);

        PublicKey publicKey = (PublicKey)key;

        // 取公钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64Utils.decode(sign));
    }

//     public static void main(String[] args) throws Exception {
//      Map<String, Object> keyMap = RSAUtils.initKey();
//      String publicKey = RSAUtils.getPublicKey(keyMap);
//      String privateKey = RSAUtils.getPrivateKey(keyMap);
//      System.out.println("公钥 -> " + publicKey);
//      System.out.println("私钥 -> " + privateKey);
////      System.out.println("公钥加密，私钥解密");
//      String sourceString = "test 123456";
//
//      byte[] encodedData = RSAUtils.encrypt(sourceString.getBytes(), publicKey, true);
//      System.out.print("加密后字符串：" + Base64Utils.encode(encodedData));
//      byte[] decodedData = RSAUtils.decrypt(Base64Utils.decode(Base64Utils.encode(encodedData)), privateKey, false);
//      String targetString = new String(decodedData);
//      System.out.println("加密前: " + sourceString + "，解密后: " + targetString);
//     }

//    public static void main(String[] args) throws Exception {
//        String publicKey =
//                "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC9yid99t3HVdG9YPptU3m3+Tn4S73fzgYJaQHwo+5LLTFYilItuh/VGLFM0W30X/QGcggpN9St1wGO+9SvBKb4qMb4gaIvh7PdDcAgVSvSVdemLNlP3VsuBpX69UrCcNf5cq7vRjW4oHbwGoioEMhHXgduVyU6kuA8NN9Xq+ovwIDAQAB";
//        String privateKey =
//                "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKmeWwcu24ujjOzBztT3cNf233DtMFbmr3QIcVh1u2sPqdIcGDyhZ2QvQjoFON7KmqGnyIrdvBS+PxMWa7+6NBuhZ4LULu+NUs9YXmvNi2pjAjUkDEj+cbxzJCjBLr1eYfAlWQKdy6wZmM7/Ty7L0JxAbNBDEzuJGBUR49XFT88XAgMBAAECgYAUl1jNom6coRo2k8CcMf3M+fdeKi04H4ElQPEhoAlEkf43t5lpot5+Vw5fJMsmoMns3gWGdiyjCAV4N7yU5fH1RiVDllL+pft9FrY/7G1HwW/IqqNoQBekcwmLX1UO4hJgndZeCWnKhK87y0p5jHa69aJLh5N5d1VA3SVz3iKeYQJBANOKZdqaF6LmJ1u5kYDhl4ELmi9QC5ipKu3Gkp1pq4nmzj5XV/JUb/vXmqxMRDSttml9eFLSMcLcOBC/ebEj1QMCQQDNRGjtA4A79NvqAKpVgWdS+9CMm7ZmLEy0aJPsaVu8gDG5/c862hWufqWiethZFxNDAlrWrFjW4NxIoQuFWs9dAkAlpIUHZ1A5bpcmXVklA/+/uN7Q+H93xPKcqS9Bz6H0whuFQGmn95qNyAoACvpAHYFOOEOHCVxNfn6DjG8NNIEdAkEAn8h7wNuzy3gsY7gG365uhrs8OOfRETG0t86cLzBIV7CWhMhcstwmeoFdX+AuXxNGTCgeG/Nm6EV7VBIAjNsGhQJAberIGUk2aDD+wM1KOhO9WIvvC7Tv0VF5wAJbqxelEeh8Hy7x5xGocGszj7pffcLm6BbHCW6axiY0PIKwvAWrAg==";
//        System.out.println("公钥 -> " + publicKey);
//        System.out.println("私钥 -> " + privateKey);
//        System.out.println("公钥加密，私钥解密");
////        String sourceString =
////                "VlnTYGQVcjCYnt00mvsSc1QH73Cw15fH4ibvbb0V/SkF+y0zFX1ItCrAubzotObD0C+JDXTYufJ6tugsv1FFipksyeZ/ju6gSl6vEZMjvhO0fW2fU/yecKQbGfdIJ0eWiSmnlUC+USIDmCYI/2xaC83WJinmHR6G549BNyekqto=";
////        String sourceString = "YuAwWGscRmZJ7mSmXsVJa90ramsqBwZWojfhPDiAG/1jukdLfQsWNLHgQjkAGWIXYnDE/fUwNFxYZiqfnKhP1tUAvscJoTlCJdfmOB48GiHJ4wGuo73Z4ap0N8dkppHb/njkReJDwspHfY1S15geEH9jW1vLJLYo1/W7ztWm8=";
////        String sourceString = "JJUOn0hwmb4dWyyAuXnTaNS8imbSIA9yndR2pcvzX5Hev6AFF+pQyUA/O0hCl+uPB6Usvzf0mu8F/NMGsh0g4g2+pfFZrgnblHTMESFsxCHfrfh5HdSz2IoU5eJTcDa/EZxmNtKkMIbZ2nQ6ZJ20nh5k8DcXquAcdlex/n6jSdU=";
//        String sourceString = "O3yT3mlP0BHBA3/UfBOjSym+sEYRF+qeYN+L7IjyYA5xwvH+AOWljrV2LGdLPNDz0TuWudUR59Gh9On4ODvpyQYac2HCKzhPhlQcrSV6n6XGuh+RhzigbLdtneRUrpiDkPVIzFLoJt66eCUCQQlfmJAUXyu2FJJXtRLxN3vtCA8=";
////        String sourceString = "ZswqYBo83O56p2gHlmYnAiYNGbXYTBU5E3wYzERxM0HksGchz19rhKV1ycJfHZIN87eoWOP1Ck77sP5rPBg0z+/+8y+fQGIpktvhVdIVP5NFwWjXsDITQ4lgAYTYuyE1ZSvRJLh4JWdGj+qTF9FsVKnx4V73U8lj5xRFEGvRSCg="
//        System.out.println(sourceString.length());
//        //        String sourceString = "Ppn8h8sK5Dp9pWOpBtl179Kffg0yZXTOhiRBUPTl8OqNUPfBjnR8BGFAk2ZsEUYRCkR14SKYmczCdMB9MMlPEhknO3eaKs5dq85H%2BVA0iK0bFUBMBO60h3zJyJk5RmgG0OGXHQB0lO3dTZNzq2MqFYFRfi2mKagjUg%2BVV0R4WAQ=";
//        byte[] decodedData = RSAUtils.decrypt(Base64Utils.decode(sourceString.replace("%2B","+")), privateKey, false);
//        String targetString = new String(decodedData);
//        System.out.println("加密前: " + sourceString + "，解密后: " + targetString);
//    }
//}
//
//
//    /**
//     * 解密<br>
//     * 用私钥解密
//     *
//     * @param data
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static String decryptByPrivateKey(String data, String key) throws Exception {
//        byte[] dataArray = Base64Utils.decode(data);
//        return new String(decryptByPrivateKey(dataArray, key));
//    }
//
//    /**
//     * 解密<br>
//     * 用私钥解密
//     *
//     * @param data
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
//        // 对密钥解密
//        byte[] keyBytes = Base64Utils.decode(key);
//
//        // 取得私钥
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//
//        // 对数据解密
//        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        int inputLen = data.length;
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        int offSet = 0;
//        byte[] cache;
//        int i = 0;
//        // 对数据分段解密
//        while (inputLen - offSet > 0) {
//            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
//                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
//            } else {
//                cache = cipher.doFinal(data, offSet, inputLen - offSet);
//            }
////            out.write(cache, 0, cache.length);
//            out.write(cache);
//            i++;
//            offSet = i * MAX_DECRYPT_BLOCK;
//        }
//        byte[] decryptedData = out.toByteArray();
//        out.close();
//
//        return decryptedData;
////        return cipher.doFinal(data);
//    }

}
