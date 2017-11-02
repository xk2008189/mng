package com.pro.common.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

public class XiBiaoExcel {
	
	
	public static final int tpRow = 10;//模板的行数
	public static final int tpCol = 3;//模板的列数
	public static final float heightInPoints = 17.25f;//1-8行高
	public static final float heightInPoints9 = 34.5f;//9行高
	public static final float heightInPoints10 = 40.25f;//10行高
	
	public static void main(String[] args){
		HSSFRow row;
		try {
			FileInputStream fs=new FileInputStream("d://ticket/xibiao.xls");  
			POIFSFileSystem ps=new POIFSFileSystem(fs);  
			HSSFWorkbook wb=new HSSFWorkbook(ps);    
			HSSFSheet sheet=wb.getSheetAt(0);  
			row = sheet.getRow(0);
			
			int countnum = 13;//需要生成的模板个数
			
			//将模板中的合并单元格保存到List以备用
			int num = sheet.getNumMergedRegions();
			List<CellRangeAddress> cellRangeAddressList = new ArrayList<CellRangeAddress>();
			for (int i = 0; i < num; i++) {  
				CellRangeAddress region = (CellRangeAddress) sheet.getMergedRegion(i);  
				cellRangeAddressList.add(region);
		    } 
			
			//获取所有的图片
			//处理sheet中的图形
            HSSFPatriarch hssfPatriarch = sheet.getDrawingPatriarch();
            //获取所有的形状图
            List<HSSFShape> shapes = hssfPatriarch.getChildren();
            int sum = shapes.size();//图片个数
            
			
			//循环复制模板，每5个模板进行换行
			for(int count = 0;count< countnum;count++){
				if(count>0){
					for(int i = 0;i<tpRow;i++){
						HSSFRow rowi = sheet.createRow(i+tpRow*count);
						if(i==8){
							rowi.setHeightInPoints(heightInPoints9);//设置9行高
						}else if(i==9){
							rowi.setHeightInPoints(heightInPoints10);//设置10行高
						}else{
							rowi.setHeightInPoints(heightInPoints);//设置其他行高
						}
						for(int j = 0;j<tpCol;j++){
							rowi.createCell(j);
						}
					}
				}else{
					HSSFRow row9 = sheet.createRow(9);
					row9.setHeightInPoints(heightInPoints10);//设置10行高
					for(int j = 0;j<tpCol;j++){
						row9.createCell(j);
					}
				}
				//System.out.println("=========count:"+count);
				//复制合并单元格
				if(count>0&&cellRangeAddressList.size()>0){
					for(CellRangeAddress region:cellRangeAddressList){
						int targetRowFrom = region.getFirstRow();  
						int targetRowTo = region.getLastRow();  
						CellRangeAddress newRegion = (CellRangeAddress) region.copy();  
						newRegion.setFirstRow(targetRowFrom+tpRow*count);  
						newRegion.setFirstColumn(region.getFirstColumn());  
						newRegion.setLastRow(targetRowTo+tpRow*count);  
						newRegion.setLastColumn(region.getLastColumn());  
						sheet.addMergedRegion(newRegion); 
					}
				}
				//复制单元格
				if(count>0){
					for(int i = 0;i<tpRow;i++){
						HSSFRow rowi = sheet.getRow(i);
						for(int j = 0;j<tpCol;j++){
							HSSFCell cell0 = rowi.getCell(j);
							HSSFCell cell5 = sheet.getRow(i+tpRow*count).createCell(j);
							copyCell(cell0,cell5);
						}
					}
				}
				//复制图片
				for (int i=0;i<sum;i++) {
	            	HSSFShape shape = shapes.get(i);
	            	if (shape instanceof HSSFPicture) {
	            		HSSFPicture pic = (HSSFPicture) shape;
	            		HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
	            		
	            		//复制图片图片
	            		HSSFClientAnchor anchor1 = new HSSFClientAnchor(anchor.getDx1(),anchor.getDy1(),anchor.getDx2(),anchor.getDy2(),
	            				anchor.getCol1(),anchor.getRow1()+tpRow*count,anchor.getCol2(),anchor.getRow2()+tpRow*count);
	            		anchor1.setAnchorType(anchor1.getAnchorType()); 
	            		hssfPatriarch.createPicture(anchor1, pic.getPictureIndex());
	            		
	            	}
	            }
			}
			

			FileOutputStream out=new FileOutputStream("d://ticket/xibiao1.xls");  
			out.flush();  
			wb.write(out);    
			out.close();
			System.out.println(row.getPhysicalNumberOfCells()+" "+row.getLastCellNum());   
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
  
	/**
	 * 复制单元格
	 * @param srcCell
	 * @param distCell
	 */
	private static void copyCell(HSSFCell srcCell, HSSFCell distCell) {  
        distCell.setCellStyle(srcCell.getCellStyle());  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {  
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                distCell.setCellValue(srcCell.getDateCellValue());  
            } else {  
                distCell.setCellValue(srcCell.getNumericCellValue());  
            }  
        } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {  
            distCell.setCellValue(srcCell.getRichStringCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {  
            // nothing21  
        } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {  
            distCell.setCellValue(srcCell.getBooleanCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {  
            distCell.setCellErrorValue(srcCell.getErrorCellValue());  
        } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {  
            distCell.setCellFormula(srcCell.getCellFormula());  
        } else { // nothing29  
  
        }  
    }  
}
