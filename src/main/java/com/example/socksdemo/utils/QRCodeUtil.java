package com.example.socksdemo.utils;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.catalina.manager.host.Constants.CHARSET;


public class QRCodeUtil {
    private static final String QR_CODE_IMAGE_PATH = "E:\\二维码\\微信图片_20200619131915.jpg";

    private static void generateQRCodeImage(String port,String ip,String password, int width, int height, String filePath) throws WriterException, IOException {
        String text="salsa20:"+password+"@"+ip+":"+port;
        //System.out.println(text);
        //转base64加密
        String base64_1 = Base64.getEncoder().encodeToString(text.getBytes("utf-8"));
        String replace= base64_1.replaceAll("=","");
        replace="ss://"+replace;
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(replace, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);

       MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static Map<String,String> getQRCodeImage(String key_type,String port,String domain_name,String password, int width, int height) throws WriterException, IOException {
        Map<String,String> map=new HashMap<String,String>();
        // String str="chacha20:yu2019@jp.888xrb.cc:51016";
        String text=key_type+":"+password+"@"+domain_name+":"+port;
        //System.out.println(text);
        //转base64加密
        String base64_1 = Base64.getEncoder().encodeToString(text.getBytes("utf-8"));
        String replace= base64_1.replaceAll("=","");
        replace="ss://"+replace;
        map.put("qrcode_info",replace);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(replace, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        String str = new BASE64Encoder().encode(pngData);
        map.put("qrcode",str);
        return map;
    }

    /**
     * 读二维码并输出携带的信息
     */
    public static String readQrCode(InputStream inputStream) throws IOException{
        //从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Map<DecodeHintType,Object> hints = new LinkedHashMap<DecodeHintType,Object>();
        // 解码设置编码方式为：utf-8，
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
//优化精度
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//复杂模式，开启PURE_BARCODE模式
        hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

       // QRCodeReader reader = new QRCodeReader();
        Result result =null;
        try {
            result=new MultiFormatReader().decode(bitmap, hints);

        } catch (ReaderException e) {
            e.printStackTrace();
        }
        return  result.getText();
    }


    public static void main(String[] args) {
        String str=null;
        try{
           str=  readQrCode(new FileInputStream(new File("E:\\二维码\\微信图片_20200619143040.png")));
           System.out.println(str);
        }catch (Exception e){
           e.getMessage();
        }
//https://qr.alipay.com/fkx10326wwilnyiydbhjcce?t=1592544957528

//       String string="Y2hhY2hhMjA6eXUyMDE5QGpwLjg4OHhyYi5jYzo1MTAxNg";
//       //String string="c2Fsc2EyMDpwYXNzd2Q";
//        Base64.Decoder decoder = Base64.getMimeDecoder();
//        byte[] bs_1 = decoder.decode(string);
//        try {
//            System.out.println(new String(bs_1, "utf-8"));
//        }catch (Exception e){
//            e.getMessage();
//        }


//         String str="chacha20:yu2019@jp.888xrb.cc:51016";
//        try{
//            String base64_1 = Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
//            System.out.println(base64_1);
//        }catch (Exception e){
//            e.getMessage();
//        }

//        try{
//            generateQRCodeImage("62121","192.168.5.1","123456",350,350,QR_CODE_IMAGE_PATH);
//        }catch (Exception e){
//                  e.getMessage();
//        }


    }



}
