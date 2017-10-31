package com.pro.system.common;





import java.awt.Color;
import java.awt.Font;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class Grid {

	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	private int row;
	
	private int col;
	
	private boolean show;
	
	private Color bgColor;
	
	private Font font;
	
	private XSSFColor ftColor;
	
	private String text;
	
	private byte[] data;
	
	private CellStyle cellStyle;

	public String getText() {
		return text;
	}

	public byte[] getData() {
		return data;
	}

	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(org.apache.poi.ss.usermodel.Font font1) {
		
		font = new Font("Helvetica",Font.ITALIC,font1.getFontHeight()/20);
	}


	public XSSFColor getFtColor() {
		return ftColor;
	}

	public void setFtColor(XSSFColor ftColor) {
		this.ftColor = ftColor;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
	
	
}
