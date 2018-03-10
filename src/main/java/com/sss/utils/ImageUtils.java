package com.sss.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

public class ImageUtils {
	
	/**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */
	public static String getImgStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String str = new String(Base64.encodeBase64(data));
		return "data:image/jpeg;base64," + str;
	}
    
    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr 图片数据
     * @param imgFilePath 保存图片全路径地址
     * @return
     */
    public static boolean generateImage(String imgStr,String imgFilePath){
		if (imgStr == null) {
			return false;
		}
		if (imgStr.startsWith("data:image/jpeg;base64,")) {
			imgStr = imgStr.replace("data:image/jpeg;base64,", "");
		}

		try {
			byte[] b = Base64.decodeBase64(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;// 调整异常数据
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
    }
    
    public static byte[] toByteArray(File imageFile) {
        try {
			BufferedImage img = ImageIO.read(imageFile);
			ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile.length());
			try {
			    ImageIO.write(img, "jpg", buf);
			} catch (Exception e) {
			    e.printStackTrace();
			    return null;
			}
			return buf.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }


}
