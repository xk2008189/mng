package com.pro;

import java.io.IOException;
import java.util.zip.ZipFile;

public class ZipTest {

	public static void main(String[] args){
		String zipFileName = "";
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
