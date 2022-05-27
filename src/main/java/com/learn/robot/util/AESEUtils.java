package com.learn.robot.util;

import org.apache.commons.net.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * AES对称加密和解密
 */
public class AESEUtils {

    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同，也可以不同!
    private static String KEY="@hcetwx#!lecxetc";

    private static String IV="@hcetwx#!lecxetc";

    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";

    /**
     * 因为静态变量不能直接从配置文件通过@value获取，所以采用此方法
     * @param key
     */

    /**
     * 加密方法 返回base64加密字符串
     * 和前端保持一致
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv)  {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);//"算法/模式/补码方式"NoPadding PKCS5Padding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted).replaceAll("[\\s*\t\n\r]", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(new Base64().decode(data));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) {
        return decrypt(data, KEY, IV);
    }

//    public static String decryMsisdn(String msisdn) {
//        try{
//            if(StringUtils.isNotBlank(msisdn)){
//                msisdn = AESEUtils.decrypt(msisdn);
//            }
//        }catch (Exception e){
//            msisdn = "";
//            e.printStackTrace();
//            System.out.println("号码解密失败");
//        }
//        return msisdn;
//    }



}