package com.pro.system.common;

import java.io.InputStream;  
import java.util.ArrayList;  
  
import org.apache.commons.net.ftp.FTPClient;  
  
/** 
 *  
 * 接口定义 
 *  
 * 
 */  
public interface FtpInterface {  
      
    public FTPClient ftp(String ip,String user,String password);  
      
    public ArrayList<String[]> csv(InputStream in);  
  
}  