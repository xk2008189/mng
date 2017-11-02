package com.pro.common.excel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;



public class Excel2PDF {
	
	public static void main(String[] args){
		office2PDF("D:/test/test2.xls","D:/test/test2.pdf");
	}
	
    public static int office2PDF(String sourceFile, String destFile) {  
  
        String OpenOffice_HOME = "C:/Program Files (x86)/OpenOffice 4/program/";// 这里是OpenOffice的安装目录,  
                                                                        // 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是尽对没题目的  
        // 假如从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'  
        if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/') {  
            OpenOffice_HOME += "/";  
        }  
        Process pro = null;  
        try {  
            // 启动OpenOffice的服务  
            String command = OpenOffice_HOME  
                    + "soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";  
            pro = Runtime.getRuntime().exec(command);  
            // connect to an OpenOffice.org instance running on port 8100  
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);  
            connection.connect();  
  
            // convert  
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
            File inputFile = new File(sourceFile);
            File outputFile = new File(destFile);
			converter.convert(inputFile, outputFile);  
  
            // close the connection  
            connection.disconnect();  
            // 封闭OpenOffice服务的进程  
            pro.destroy();  
            
            //pdfz转图片
            try {
				pdf2Imgs("D:/test/test.pdf","D:/test");
			} catch (Exception e) {
				e.printStackTrace();
			}
  
            return 0;  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            return -1;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            pro.destroy();  
        }  
  
        return 1;  
    } 
    
    /** 
     * 将pdf转换成图片 
     * 
     * @param pdfPath 
     * @param imagePath 
     * @return 返回转换后图片的名字 
     * @throws Exception 
     */  
    public static List<String> pdf2Imgs(String pdfPath, String imgDirPath) throws Exception {  
        Document document = new Document();  
        document.setFile(pdfPath);  
     
        float scale = 5f;//放大倍数  
        float rotation = 0f;//旋转度数  
     
        List<String> imgNames = new ArrayList<String>();  
        int pageNum = document.getNumberOfPages();  
        File imgDir = new File(imgDirPath);  
        if (!imgDir.exists()) {  
            imgDir.mkdirs();  
        }  
        for (int i = 0; i < pageNum; i++) {  
        	List<Image> images = document.getPageImages(i);
            BufferedImage image = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,  
                    Page.BOUNDARY_CROPBOX, rotation, scale);  
            RenderedImage rendImage = image;  
            try {  
                String filePath = imgDirPath + File.separator + i + ".jpg";  
                File file = new File(filePath);  
                ImageIO.write(rendImage, "jpg", file);  
                imgNames.add(FilenameUtils.getName(filePath));  
            } catch (IOException e) {  
                e.printStackTrace();  
                return null;  
            }  
            image.flush();  
        }  
        document.dispose();  
        return imgNames;  
    }  
}
