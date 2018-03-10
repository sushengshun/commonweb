package com.sss.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;  
  
/** 
 * 二维码生成工具类 
 * @author Cloud 
 * @data   2016-12-15 
 * QRCode 
 */  
  
public class QRCodeUtil {  
      
    //二维码颜色  
    private static final int BLACK = 0xFF000000;  
    //二维码颜色  
    private static final int WHITE = 0xFFFFFFFF;  
  
    /** 
     * ZXing 方式生成二维码
     * @param text    二维码内容
     * @param width    二维码宽 
     * @param height    二维码高 
     * @param outPutPath    二维码生成保存路径 
     * @param imageType     二维码生成格式 
     */  
	public static void zxingCodeCreate(String text, int width, int height, String outPutPath, String imageType) {
		Map<EncodeHintType, Object> his = new HashMap<EncodeHintType, Object>();
		his.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			// 1、生成二维码 //注：// 条形码的格式是 BarcodeFormat.EAN_13 不过条形码只能输入13位数字
			BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);

			//去白边
			encode=deleteWhite(encode);
			
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
			File outPutImage = new File(outPutPath);
			// 如果图片不存在创建图片
			if (!outPutImage.exists())
				outPutImage.createNewFile();
			// 5、将二维码写入图片
			ImageIO.write(image, imageType, outPutImage);
			
						
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	  
	/**
     * 生成包含字符串信息的二维码图片
     * @param outputStream 文件输出流路径
     * @param content 二维码携带信息
     * @param qrCodeSize 二维码图片大小
     * @param imageFormat 二维码的格式
     * @throws WriterException 
     * @throws IOException 
     */
    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws WriterException, IOException{  
            //设置二维码纠错级别ＭＡＰ
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();  
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  // 矫错级别  
            QRCodeWriter qrCodeWriter = new QRCodeWriter();  
            //创建比特矩阵(位矩阵)的QR码编码的字符串  
            BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, hintMap);  
            // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
            int matrixWidth = byteMatrix.getWidth();  
            BufferedImage image = new BufferedImage(matrixWidth-200, matrixWidth-200, BufferedImage.TYPE_INT_RGB);  
            image.createGraphics();  
            Graphics2D graphics = (Graphics2D) image.getGraphics();  
            graphics.setColor(Color.WHITE);  
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);  
            // 使用比特矩阵画并保存图像
            graphics.setColor(Color.BLACK);  
            for (int i = 0; i < matrixWidth; i++){
                for (int j = 0; j < matrixWidth; j++){
                    if (byteMatrix.get(i, j)){
                        graphics.fillRect(i-100, j-100, 1, 1);  
                    }
                }
            }
            return ImageIO.write(image, imageFormat, outputStream);  
    }  
      
    /**
     * 读二维码并输出携带的信息
     */
    public static void readQrCode(InputStream inputStream) throws IOException{  
        /*//从输入流中获取字符串信息
        BufferedImage image = ImageIO.read(inputStream);  
        //将图像转换为二进制位图源
        LuminanceSource source = new BufferedImageLuminanceSource(image);  
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
        QRCodeReader reader = new QRCodeReader();  
        Result result = null ;  
        try {
         result = reader.decode(bitmap);  
        } catch (ReaderException e) {
            e.printStackTrace();  
        }
        System.out.println(result.getText());  */
    }
    
    /**
     * 条形码生成
     * @param text 条形码的输入是13位的数字 如:6923450657713
     * @param width  生成图片宽度
     * @param height  生成图片高度
     * @param outPutPath 输出图片绝对路径
     * @param imageType 图片格式
     */
    public static void createBarCode(String text, int width, int height, String outPutPath, String imageType){
    	try {
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();  
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
			// 条形码的格式是 BarcodeFormat.EAN_13  
			// 二维码的格式是BarcodeFormat.QR_CODE  
			BitMatrix bm = new MultiFormatWriter().encode(text, BarcodeFormat.EAN_13, width, height, hints);  

			File out = new File(outPutPath);  
			BufferedImage image = new BufferedImage(width, height,  BufferedImage.TYPE_3BYTE_BGR); 
			for (int i = 0; i < width; i++) {  
			    for (int j = 0; j < height; j++) {  
			        image.setRGB(i, j, bm.get(i, j) ? BLACK : WHITE);  
			    }  
			} 
			ImageIO.write(image, imageType, out);
    	} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
    }
    
    /**
     * 删除白边，这样能保证二维码大小是给的宽度大
     * @param matrix
     * @return
     */
    private static BitMatrix deleteWhite(BitMatrix matrix) {  
        int[] rec = matrix.getEnclosingRectangle();  
        int resWidth = rec[2] + 1;  
        int resHeight = rec[3] + 1;  
  
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);  
        resMatrix.clear();  
        for (int i = 0; i < resWidth; i++) {  
            for (int j = 0; j < resHeight; j++) {  
                if (matrix.get(i + rec[0], j + rec[1]))  
                    resMatrix.set(i, j);  
            }  
        }  
        return resMatrix;  
    } 
    
    public static void testBarCode(){
    	try {
    		String text="sfewe2343";
    		int width=120;
    		int height=50;
    		String outPutPath="C:/Users/Administrator/Desktop/barcode.png";
    		String imageType="png";
    		
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();  
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");  
			// 条形码的格式是 BarcodeFormat.EAN_13  
			// 二维码的格式是BarcodeFormat.QR_CODE  
			BitMatrix bm = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_93, width, height, hints);  

			File out = new File(outPutPath);  
			BufferedImage image = new BufferedImage(width, height,  BufferedImage.TYPE_3BYTE_BGR); 
			for (int i = 0; i < width; i++) {  
			    for (int j = 0; j < height; j++) {  
			        image.setRGB(i, j, bm.get(i, j) ? BLACK : WHITE);  
			    }  
			} 
			ImageIO.write(image, imageType, out);
    	} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    	
    }
    
   
  
} 