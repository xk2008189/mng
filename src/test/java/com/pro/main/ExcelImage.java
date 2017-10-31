package com.pro.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelImage {
	
	
	public static void main(String[] args){
		
	}
	

	public Map<String,byte[] > getImgFiles(HSSFWorkbook workbook){
		//定义一个Map存储sheet名称和对应的图片byte[]
		Map<String,byte[] > imgMap = new HashMap<String, byte[]>();
		int num = workbook.getNumberOfSheets();
		List<HSSFPictureData> pictureList = workbook.getAllPictures();  
		if(num>2){
			for(int i=1;i<num;i++){
				HSSFSheet hssfsheet = workbook.getSheetAt(i);
				//获取sheet页面名称
				String sheetName = hssfsheet.getSheetName();
				//处理sheet中的图形
	            HSSFPatriarch hssfPatriarch = hssfsheet.getDrawingPatriarch();
	            //获取所有的形状图
	            List<HSSFShape> shapes = hssfPatriarch.getChildren();
	            
	            if(shapes==null||shapes.size()!=1){
	            	System.out.println("导入Excel："+sheetName+" sheet页图片数量不为1");
	            }
	            HSSFShape shape = shapes.get(0);
	            if(!(shape instanceof HSSFPicture)){
	            	System.out.println("导入Excel："+sheetName+" 没有图片");
	            }
	            
	            HSSFPictureData picdata = pictureList.get(i-1);
	            byte[] picbytes = picdata.getData();
	            
	            imgMap.put(sheetName, picbytes);
			}
		}
		return imgMap;
	}
}
