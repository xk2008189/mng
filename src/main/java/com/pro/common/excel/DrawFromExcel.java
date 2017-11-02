package com.pro.common.excel;
 
/**
 * width:pix=getColumnWidthInPixels*1.15
 * height:pix=getHeightInPoints*96/72
 *
 * 本示例用来读取Excel报表文件，并力图将报表无差别转化成PNG图片
 * 使用POI读取Excel各项数据
 * 使用JAVA 2D绘制PNG
 * TODO 本示例基本实现了常见Excel的所有样式输出，但Office2007以后的版本添加了条件样式功能，因为POI的API无法读取条件样式，所以无法实现
 * 今后可以通过关键字标注等方法，在Excel中需要加入条件样式的单元格用注解标明，使用JAVA计算得出样式再绘制出来
 */
 
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;

import sun.awt.SunHints;
 
public class DrawFromExcel {
	
	// private static Logger logger = Logger.getLogger(DrawFromExcel.class);
	
	 public static void main(String[] args) throws Exception{
		 int[] fromIndex = { 0, 0 };
	     int[] toIndex = { 30, 11 };
	     File file = new File("D:/test/test.xls");
	     Workbook wb = WorkbookFactory.create(file);
	     Sheet sheet = wb.getSheet("新工艺双");
	     System.out.println("==========打印区域==========");
         String print1 = wb.getPrintArea(1);
         String[] printAreas = print1.split(",");
         for(int i=0;i<printAreas.length;i++){
        	 String area = printAreas[i].split("!")[1];
        	 String from  = area.split(":")[0];
        	 char a = from.split("\\$")[1].toCharArray()[0];
        	 fromIndex[1] = (int)a-65;
        	 fromIndex[0] = Integer.parseInt(from.split("\\$")[2])-1;
        	 String to  = area.split(":")[1];
        	 char b = to.split("\\$")[1].toCharArray()[0];
        	 toIndex[1] = (int)b-65;
        	 toIndex[0] = Integer.parseInt(to.split("\\$")[2])-1;
        	 
        	 doOneImage(fromIndex,toIndex,sheet,wb,i,file);
         }
         System.out.println(print1);
         System.out.println("==========打印区域==========");
	    
	 }
	
 
    public static void doOneImage(int[] fromIndex,int[] toIndex,Sheet sheet,Workbook wb,int n,File file) throws Exception {
        // 给定两个初始值，标志出导出区域，两个行列组合的单元格
        
 
        int imageWidth = 0;
        int imageHeight = 0;
 
        /*//2007
        if (wb instanceof XSSFWorkbook) {
        	XSSFWorkbook xhssfworkbook = (XSSFWorkbook) wb;
        	XSSFSheet xssfsheet = xhssfworkbook.getSheetAt(0);
        	  //处理sheet中的图形
        	List<POIXMLDocumentPart> drs =  xssfsheet.getRelations();
     
    		for (POIXMLDocumentPart  dr : drs) {
    			if (dr instanceof XSSFDrawing) { 
    				XSSFDrawing drawing = (XSSFDrawing) dr;  
                    List<XSSFShape> shapes = drawing.getShapes(); 
                    for (XSSFShape shape : shapes) {
                    	XSSFPicture pic = (XSSFPicture) shape;  
                    	XSSFClientAnchor anchor = pic.getPreferredSize();  
                    	System.out.println("======pictureIndex====Col1==="+anchor.getCol1());
    		    		System.out.println("======pictureIndex====Col2==="+anchor.getCol2());
    		    		System.out.println("======pictureIndex====Row1==="+anchor.getRow1());
    		    		System.out.println("======pictureIndex====Row2==="+anchor.getRow2());
    		    		System.out.println("");
                    }
    			}
    		}
        	//Map<String, List<HSSFPictureData>> picMap =  findAllPictureDate(Hssfsheet);
        } */
        
        // 获得一个 sheet 中合并单元格的数量
 		int sheetmergerCount = sheet.getNumMergedRegions();
 		// 遍历合并单元格
 		List<CellRangeAddress> rangeAddress = new ArrayList<CellRangeAddress>();
 		for (int i = 0; i < sheetmergerCount; i++) {
 			// 获得合并单元格加入list中
 			CellRangeAddress ca = sheet.getMergedRegion(i);
 			rangeAddress.add(ca);
 		}
        
 
        // 首先做初步的边界检测，如果指定区域是不合法的则抛出异常
       /* int rowSum = sheet.getPhysicalNumberOfRows();
        int colSum = sheet.getRow(0).getPhysicalNumberOfCells();
        if (fromIndex[0] > rowSum || fromIndex[0] > toIndex[0] || toIndex[0] > rowSum) {
            throw new Exception("the rowIndex of the area is wrong!");
        }
        if (fromIndex[1] > colSum || fromIndex[1] > toIndex[1] || toIndex[1] > colSum) {
            throw new Exception("the colIndex of the area is wrong!");
        }*/
 
        // 计算实际需要载入内存的二维Cell数组的大小，剔除隐藏行列
        int rowSize = toIndex[0]+1;
        int colSize = toIndex[1]+1;
 
        // 遍历需要扫描的区域
         
        UserCell[][] cells = new UserCell[rowSize][colSize];
        int[] rowPixPos = new int[rowSize + 1];
        rowPixPos[0] = 0;
        int[] colPixPos = new int[colSize + 1];
        colPixPos[0] = 0;
        for (int i = 0; i < rowSize; i++) {
 
            for (int j = 0; j < colSize; j++) {
 
                cells[i][j] = new UserCell();
                cells[i][j].setCell(sheet.getRow(i).getCell(j));
                cells[i][j].setRow(i);
                cells[i][j].setCol(j);
                boolean ifShow=(i>=fromIndex[0]) && (j>=fromIndex[1]);    //首先行列要在指定区域之间
                ifShow=ifShow && !(sheet.isColumnHidden(j) || sheet.getRow(i).getZeroHeight()); //其次行列不可以隐藏
                cells[i][j].setShow(ifShow);
 
                // 计算所求区域宽度
               double widthPix = !ifShow ? 0 : sheet.getColumnWidth(j)/256*8.456; // 如果该单元格是隐藏的，则置宽度为0
                if (i == fromIndex[0]) {
                    imageWidth += Math.round(widthPix);
                }
                widthPix = widthPix;
                //float widthPix  = (float) 136.3;
                colPixPos[j+1] = (int) (Math.round(widthPix)  + colPixPos[j]);
 
            }
 
            // 计算所求区域高度
            boolean ifShow=(i>=fromIndex[0]);    //行序列在指定区域中间
            ifShow=ifShow && !sheet.getRow(i).getZeroHeight();  //行序列不能隐藏
            double heightPoint = !ifShow ? 0 : sheet.getRow(i).getHeightInPoints()*1.367; // 如果该单元格是隐藏的，则置高度为0
            imageHeight += Math.round(heightPoint);        
            rowPixPos[i+1] = (int) (rowPixPos[i]+Math.round(heightPoint)) ;
 
        }
        String message =null;
       // String message = "====cells:"+JsonMapper.getInstance().toJson(cells);        
        //logger.info(message);
 
        imageHeight = imageHeight ;
        imageWidth = imageWidth;
 
       // wb.close();
 
        List<Grid> grids = new ArrayList<Grid>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                Grid grid = new Grid();
                // 设置坐标和宽高
                grid.setX(colPixPos[j]);
                grid.setY(rowPixPos[i]);
                grid.setWidth(colPixPos[j + 1] - colPixPos[j]);
                grid.setHeight(rowPixPos[i + 1] - rowPixPos[i]);
                grid.setRow(cells[i][j].getRow());
                grid.setCol(cells[i][j].getCol());
                grid.setShow(cells[i][j].isShow());
 
                // 判断是否为合并单元格
                int[] isInMergedStatus = isInMerged(grid.getRow(), grid.getCol(), rangeAddress);
 
                if (isInMergedStatus[0] == 0 && isInMergedStatus[1] == 0) {
                    // 此单元格是合并单元格，并且不是第一个单元格，需要跳过本次循环，不进行绘制
                    continue;
                } else if (isInMergedStatus[0] != -1 && isInMergedStatus[1] != -1) {
                    // 此单元格是合并单元格，并且属于第一个单元格，则需要调整网格大小                 
                    int lastRowPos=isInMergedStatus[0]>rowSize-1?rowSize-1:isInMergedStatus[0];
                    int lastColPos=isInMergedStatus[1]>colSize-1?colSize-1:isInMergedStatus[1];                 
                     
                    grid.setWidth(colPixPos[lastColPos + 1] - colPixPos[j]);
                    grid.setHeight(rowPixPos[lastRowPos + 1] - rowPixPos[i]);
                     
                     
                }
 
                // 单元格背景颜色
                if(cells[i][j].getCell()!=null){
                	CellStyle cs = cells[i][j].getCell().getCellStyle();
                	
                	/* if (cs.getFillPattern() == CellStyle.SOLID_FOREGROUND)
                    grid.setBgColor(cells[i][j].getCell().getCellStyle().getFillForegroundColorColor());*/
                	
                	// 设置字体
                	org.apache.poi.ss.usermodel.Font font = wb.getFontAt(cs.getFontIndex());
                	grid.setFont(font);
                	// 设置字体前景色
                	if (font instanceof XSSFFont) {
                		XSSFFont xf = (XSSFFont) font;
                		
                		grid.setFtColor(xf.getXSSFColor());
                	}
                	//设置单元格格式格式
                	grid.setCellStyle(cells[i][j].getCell().getCellStyle());
                	// 设置文本
                	String strCell = "";
                	switch (cells[i][j].getCell().getCellType()) {
                	case HSSFCell.CELL_TYPE_NUMERIC:   
                		if (cells[i][j].getCell().getCellStyle().getDataFormat()==31) {    //判断是日期类型  
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日");  
                            Date dt = HSSFDateUtil.getJavaDate(cells[i][j].getCell().getNumericCellValue());//获取成DATE类型     
                            strCell = dateformat.format(dt);   
                        }else{
                        	strCell = String.valueOf(cells[i][j].getCell().getNumericCellValue());
                        }
                		break;
                	case HSSFCell.CELL_TYPE_STRING:
                		strCell = cells[i][j].getCell().getStringCellValue();
                		System.out.println(font.getFontName());
                		if(font.getFontName()!=null&&font.getFontName().equals("Times New Roman")){
                			System.out.println(strCell);
                			/*byte[] byteArray = new byte[] {32};
                			String value = new String(byteArray);*/
                			strCell.replaceAll("  ", " ");
                			System.out.println(strCell);
                		}
                		break;
                	case HSSFCell.CELL_TYPE_BOOLEAN:
                		strCell = String.valueOf(cells[i][j].getCell().getBooleanCellValue());
                		break;
                	case HSSFCell.CELL_TYPE_FORMULA:
                		
                		try {
                			strCell = String.valueOf(cells[i][j].getCell().getNumericCellValue());
                		} catch (IllegalStateException e) {
                			strCell = String.valueOf(cells[i][j].getCell().getRichStringCellValue());
                		}
                		break;
                	default:
                		strCell = "";
                	}
                	
                	if(cells[i][j].getCell().getCellStyle().getDataFormatString().contains("0.00%")){
                		try{
                			double dbCell=Double.valueOf(strCell);
                			strCell=new DecimalFormat("#.00").format(dbCell*100)+"%";
                		}catch(NumberFormatException e){}                  
                	}
                	grid.setText(strCell.matches("\\w*\\.0") ? strCell.substring(0, strCell.length() - 2) : strCell);
                }else{
                	grid.setText("");
                }
 
                grids.add(grid);
            }
        }
        
       // message = "====grids:"+JsonMapper.getInstance().toJson(grids);        
     //   logger.info(message);
        
        List<HSSFPictureData> pictures =  (List<HSSFPictureData>) wb.getAllPictures();
        List<Grid> picGrid = new ArrayList<Grid>();
        if (wb instanceof HSSFWorkbook) {
        	HSSFWorkbook hssfworkbook = (HSSFWorkbook) wb;
        	HSSFSheet Hssfsheet = hssfworkbook.getSheet("新工艺双");
        	//处理sheet中的图形
            HSSFPatriarch hssfPatriarch = Hssfsheet.getDrawingPatriarch();
            //获取所有的形状图
            List<HSSFShape> shapes = hssfPatriarch.getChildren();
    		for (HSSFShape shape : shapes) {
	    		if (shape instanceof HSSFPicture) {
		    		HSSFPicture pic = (HSSFPicture) shape;
		    		//pic.resize();
		    		
		    		int pictureIndex = pic.getPictureIndex() - 1;
		    		HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
		    		
		    		
		    		if(anchor.getCol1()<=toIndex[1]&&anchor.getRow1()<=toIndex[0]){
		    			
		    			Grid grid = new Grid();
		    			
		    			// 设置坐标和宽高
		    			grid.setX(colPixPos[anchor.getCol1()]+(colPixPos[anchor.getCol1()+1]-colPixPos[anchor.getCol1()])*anchor.getDx1()/1000);
		    			grid.setY(rowPixPos[anchor.getRow1()]+(rowPixPos[anchor.getRow1()+1]-rowPixPos[anchor.getRow1()])*anchor.getDy1()/256);
		    			double width = 0.0;
		    			double height = 0.0;
		    			
		    			if(anchor.getCol1() == anchor.getCol2()){
		    				width = (colPixPos[anchor.getCol1()+1]-colPixPos[anchor.getCol1()])*(anchor.getDx2()-anchor.getDx1())/1000;
		    			}else{
		    				width += (colPixPos[anchor.getCol2()+1]-colPixPos[anchor.getCol2()])*anchor.getDx2()/1000;
		    				for(int i=anchor.getCol1()+1;i<=anchor.getCol2();i++){
		    					width += colPixPos[i]-colPixPos[i-1];
		    				}
		    				width = width - (colPixPos[anchor.getCol1()+1]-colPixPos[anchor.getCol1()])*anchor.getDx1()/1000;
		    			}
		    			
//		    			System.out.println("==========1==========="+rowPixPos[anchor.getRow1()+1]);
//		    			System.out.println("==========2==========="+rowPixPos[anchor.getRow1()]);
//		    			System.out.println("==========3==========="+anchor.getDy1());
//		    			System.out.println("==========4==========="+anchor.getDy2());
		    			if(anchor.getRow1() == anchor.getRow2()){
		    				height = (rowPixPos[anchor.getRow1()+1]-rowPixPos[anchor.getRow1()])*(anchor.getDy2()-anchor.getDy1())/256;
		    			}else{
		    				height += (rowPixPos[anchor.getRow2()+1]-rowPixPos[anchor.getRow2()])*anchor.getDy2()/256;
		    				for(int i=anchor.getRow1()+1;i<=anchor.getRow2();i++){
		    					height += rowPixPos[i] - rowPixPos[i-1];
		    				}
		    				height = height - (rowPixPos[anchor.getRow1()+1]-rowPixPos[anchor.getRow1()])*anchor.getDy1()/256;
		    			}
		    			
		    			grid.setWidth((int)width);
		    			grid.setHeight((int)height);
		    			

//		    			System.out.println("======col1====="+anchor.getCol1());
//		    			System.out.println("======row1====="+anchor.getRow1());
//	    			    System.out.println("======width====="+width);
//		    			System.out.println("======height====="+height);
//		    			System.out.println();
		    			HSSFPictureData picData = pic.getPictureData();
		    			if(picData!=null){
		    				grid.setData(picData.getData());
		    			}
		    			picGrid.add(grid);
		    		}
	    		}
    		}
        	//Map<String, List<HSSFPictureData>> picMap =  findAllPictureDate(Hssfsheet);
        } 
 
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        // 平滑字体
        g2d.setRenderingHint(SunHints.KEY_ANTIALIASING, SunHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIASING, SunHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g2d.setRenderingHint(SunHints.KEY_STROKE_CONTROL, SunHints.VALUE_STROKE_DEFAULT);
        g2d.setRenderingHint(SunHints.KEY_TEXT_ANTIALIAS_LCD_CONTRAST, 140);
        g2d.setRenderingHint(SunHints.KEY_FRACTIONALMETRICS, SunHints.VALUE_FRACTIONALMETRICS_OFF);
        g2d.setRenderingHint(SunHints.KEY_RENDERING, SunHints.VALUE_RENDER_DEFAULT);
        
       /* g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);*/
 
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, imageWidth, imageHeight);
 
        // 绘制表格
        for (int i = grids.size()-1;i>=0;i--) {
        	Grid g = grids.get(i);
            if (!g.isShow())
                continue;
 
            // 绘制背景色
            g2d.setColor(g.getBgColor() == null ? Color.white : g.getBgColor());
            g2d.fillRect(g.getX(), g.getY(), g.getWidth(), g.getHeight());
 
            // 绘制边框
            g2d.setColor(Color.black);
            g2d.setStroke(new BasicStroke(1));
          //  g2d.drawRect(g.getX(), g.getY(), g.getWidth(), g.getHeight());
            
            
            if(g.getCellStyle()!=null){
            	//下边框
            	if(g.getCellStyle().getBorderBottom()==HSSFCellStyle.BORDER_THIN){
            		g2d.drawLine(g.getX() + g.getWidth(), g.getY() + g.getHeight(), g.getX() + 1, g.getY() + g.getHeight());//下边框
            	}
            	if(g.getCellStyle().getBorderLeft()==HSSFCellStyle.BORDER_THIN){
            		g2d.drawLine(g.getX(), g.getY() + g.getHeight(), g.getX(), g.getY() + 1);//左边框
            	}
            	if(g.getCellStyle().getBorderTop()==HSSFCellStyle.BORDER_THIN){
            		g2d.drawLine(g.getX(), g.getY(), g.getX() + g.getWidth() - 1, g.getY());//上边框
            	}
            	if(g.getCellStyle().getBorderRight()==HSSFCellStyle.BORDER_THIN){
            		g2d.drawLine(g.getX() + g.getWidth(), g.getY(), g.getX() + g.getWidth(), g.getY() + g.getHeight() - 1);//右边框
            	}
            }
            
 
            // 绘制文字,居中显示
            //g2d.setColor(g.getFtColor());
           // Font font = g.getFont();
            //Font font = new Font("Helvetica",Font.ITALIC,12);
            Font font = g.getFont();
            if(font==null){
            	font = new Font("Helvetica",Font.ITALIC,12);
            }
            FontMetrics fm = g2d.getFontMetrics(font);
            int strWidth = fm.stringWidth(g.getText());// 获取将要绘制的文字宽度
            g2d.setFont(font);
            //判断是否居中
            if(g.getCellStyle()!=null&&g.getCellStyle().getAlignment()==HSSFCellStyle.ALIGN_CENTER){
            	g2d.drawString(g.getText(),
            			g.getX() + (g.getWidth() - font.getSize()) / 2,
            			g.getY() + (g.getHeight() - font.getSize()) / 2 + font.getSize());
            }else{
            	g2d.drawString(g.getText(),
            			g.getX(),
            			g.getY() + (g.getHeight() - font.getSize()) / 2 + font.getSize());
            }
        }
        
        for(Grid g : picGrid){
        		InputStream buffin = new ByteArrayInputStream(g.getData());
        		BufferedImage img = ImageIO.read(buffin); 
        		if(g.getWidth()>0&&g.getHeight()>0){
        			g2d.drawImage(img.getScaledInstance(g.getWidth(), g.getHeight(), Image.SCALE_SMOOTH),
        					g.getX(), g.getY(), g.getWidth(), g.getHeight(), null);
        		}
        }
 
        g2d.dispose();
        //TODO "d:/test/test.png"
        System.out.println(file.getPath()+"/"+n+".png");
        ImageIO.write(image, "png", new File(file.getParent()+"/"+n+".png"));
         
        System.out.println("Output to PNG file Success!");
    }
 
    /**
     * 判断Excel中的单元格是否为合并单元格
     *
     * @param row
     * @param col
     * @param rangeAddress
     * @return 如果不是合并单元格返回{-1,-1},如果是合并单元格并且是一个单元格返回{lastRow,lastCol},
     *         如果是合并单元格并且不是第一个格子返回{0,0}
     */
    private static int[] isInMerged(int row, int col, List<CellRangeAddress> rangeAddress) {
        int[] isInMergedStatus = { -1, -1 };
        for (CellRangeAddress cra : rangeAddress) {
            if (row == cra.getFirstRow() && col == cra.getFirstColumn()) {
                isInMergedStatus[0] = cra.getLastRow();
                isInMergedStatus[1] = cra.getLastColumn();
                return isInMergedStatus;
            }
            if (row >= cra.getFirstRow() && row <= cra.getLastRow()) {
                if (col >= cra.getFirstColumn() && col <= cra.getLastColumn()) {
                    isInMergedStatus[0] = 0;
                    isInMergedStatus[1] = 0;
                    return isInMergedStatus;
                }
            }
        }
        return isInMergedStatus;
    }
    
    /**
     * 获取所有图片
     * @param sheet
     * @return
     */
    public static Map<String, List<HSSFPictureData>> findAllPictureDate(HSSFSheet sheet){

        Map<String, List<HSSFPictureData>> dataMap = null;

        //处理sheet中的图形
        HSSFPatriarch hssfPatriarch = sheet.getDrawingPatriarch();
        //获取所有的形状图
        List<HSSFShape> shapes = hssfPatriarch.getChildren();

        if(shapes.size()>0){

            dataMap = new HashMap<String, List<HSSFPictureData>>();

            List<HSSFPictureData> pictureDataList = null;

            for(HSSFShape sp : shapes){
                if(sp instanceof HSSFPicture){
                    //转换
                    HSSFPicture picture = (HSSFPicture)sp;
                    //获取图片数据
                    HSSFPictureData pictureData = picture.getPictureData();
                    //图形定位
                    if(picture.getAnchor() instanceof HSSFClientAnchor){

                        HSSFClientAnchor anchor = (HSSFClientAnchor)picture.getAnchor();
                        //获取图片所在行作为key值,插入图片时，默认图片只占一行的单个格子，不能超出格子边界
                        int row1 = anchor.getRow1();
                        String rowNum = String.valueOf(row1);

                        if(dataMap.get(rowNum)!=null){
                                pictureDataList = dataMap.get(rowNum);
                        }else{
                                pictureDataList = new ArrayList<HSSFPictureData>();
                        }
                        pictureDataList.add(pictureData);
                        dataMap.put(rowNum, pictureDataList);
                        // 测试部分
                          int row2 = anchor.getRow2();
                          short col1 = anchor.getCol1();
                          short col2 = anchor.getCol2();
                          int dx1 = anchor.getDx1();
                          int dx2 = anchor.getDx2();
                          int dy1 = anchor.getDy1();
                          int dy2 = anchor.getDy2();

                          System.out.println("row1: "+row1+" , row2: "+row2+" , col1: "+col1+" , col2: "+col2);
                          System.out.println("dx1: "+dx1+" , dx2: "+dx2+" , dy1: "+dy1+" , dy2: "+dy2);
                    }
                }
            }
        }

        System.out.println("********图片数量明细 START********");
        int t=0;
        if(dataMap!=null){
            t=dataMap.keySet().size();
        }
        if(t>0){
                for(String key : dataMap.keySet()){
                    System.out.println("第 "+key+" 行， 有图片： "+ dataMap.get(key).size() +" 张");
                }
        }else{
                System.out.println("Excel表中没有图片!");
        }
        System.out.println("********图片数量明细 END ********");

        return dataMap;
    }
}
