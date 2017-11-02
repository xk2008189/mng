package com.pro.common.excel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;

public class TicketExcel {
	
	
	public static final int tpRow = 15;//模板的行数
	public static final int tpCol = 4;//模板的列数
	public static final int five = 5;//每行几个模板
	public static final float heightInPoints = 19.25f;//行高
	
	public static void main(String[] args){
		HSSFRow row;
		try {
			FileInputStream fs=new FileInputStream("d://ticket/test.xls");  
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
			//循环复制模板，每5个模板进行换行
			for(int count = 0;count< countnum;count++){
				//每行生成5个模板，所以需要计算当前需要生成第几行第几个模板
				int rowCount = (int)((count)/five)+1;//第几模板行
				int colCount = (count)%five+1;//第几个模板
				//如果超过一排小票就需要多创建15行
				if(rowCount>1&&colCount==1){
					for(int i = 0;i<tpRow;i++){
						HSSFRow rowi = sheet.createRow(i+tpRow*(rowCount-1));
						rowi.setHeightInPoints(heightInPoints);//设置行高
						for(int j = 0;j<tpCol*five;j++){
							rowi.createCell(j);
						}
					}
				}
				System.out.println("=========count:"+count);
				//复制合并单元格
				if(count>0&&cellRangeAddressList.size()>0){
					for(CellRangeAddress region:cellRangeAddressList){
						int targetRowFrom = region.getFirstRow();  
						int targetRowTo = region.getLastRow();  
						CellRangeAddress newRegion = (CellRangeAddress) region.copy();  
						newRegion.setFirstRow(targetRowFrom+tpRow*(rowCount-1));  
						newRegion.setFirstColumn(region.getFirstColumn()+tpCol*(colCount-1));  
						newRegion.setLastRow(targetRowTo+tpRow*(rowCount-1));  
						newRegion.setLastColumn(region.getLastColumn()+tpCol*(colCount-1));  
						sheet.addMergedRegion(newRegion); 
					}
				}
				//复制单元格
				if(count>0){
					for(int i = 0;i<tpRow;i++){
						HSSFRow rowi = sheet.getRow(i);
						for(int j = 0;j<tpCol;j++){
							HSSFCell cell0 = rowi.getCell(j);
							HSSFCell cell5 = sheet.getRow(i+tpRow*(rowCount-1)).createCell(j+tpCol*(colCount-1));
							copyCell(cell0,cell5);
						}
					}
				}
			}
			

			FileOutputStream out=new FileOutputStream("d://ticket/test1.xls");  
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
