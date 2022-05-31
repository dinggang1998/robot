package com.learn.robot.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: StevenDing
 * Description: 二维码生成工具类
 * @author 40274
 */

public class QRCodeUtil {

    // 二维码颜色
    private static final int BLACK = 0xFF000000;
    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

//    public static void main(String[] args) throws Exception {
//        String zString = zxingCodeCreate("www.baidu.com", 300, 300, "D:\\qrcode.jpg", "jpg");
//        System.out.println(zString);
//    }

    /**
     * 生成二维码
     *
     * @param text
     *            二维码内容
     * @param width
     *            二维码宽
     * @param height
     *            二维码高
     * @param outPutPath
     *            二维码生成保存路径
     * @param imageType
     *            二维码生成格式
     */
    public static String zxingCodeCreate(String text, int width, int height, String outPutPath, String imageType) {
        Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
        // 设置编码字符集
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            // 1、生成二维码
            BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);
            // 2、获取二维码宽高
            int codeWidth = encode.getWidth();
            int codeHeight = encode.getHeight();
            // 3、将二维码放入缓冲流
            BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < codeWidth; i++) {
                for (int j = 0; j < codeHeight; j++) {
                    // 4、循环将二维码内容定入图片
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
                }
            }
            // 生成二维码图片
            File outputFile = new File(outPutPath);//指定输出路径
            MatrixToImageWriter.writeToFile(encode, imageType, outputFile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", out);
            byte[] imageBytes = out.toByteArray();
            String base64String = Base64.getEncoder().encodeToString(imageBytes);
            return "data:image/jpg;base64," + base64String;
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println("二维码生成失败");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("生成二维码图片失败");
        }
        return null;
    }
}
