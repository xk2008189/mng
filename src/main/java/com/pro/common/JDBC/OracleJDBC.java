package com.pro.common.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.pro.common.Util.PropertiesLoader;

public class OracleJDBC {
	
	
	public static void testOracle(String planID)	{
	    Connection con = null;// 创建一个数据库连接
	    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	    ResultSet result = null;// 创建一个结果集对象
	    try
	    {
	        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
	        System.out.println("开始尝试连接数据库！");
	        PropertiesLoader pro = new PropertiesLoader("oracle.properties");
	    	
	        
	  /*      String url = "jdbc:oracle:" + "thin:@192.168.0.88:1521:orcl";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
	        String user = "mes_production2";// 用户名,系统默认的账户名
	        String password = "1234qwer!@#$";// 你安装时选设置的密码*/
	    	String url = pro.getProperty("url");
		    String user = pro.getProperty("user");
		    String password = pro.getProperty("password");
	    	con = DriverManager.getConnection(url, user, password);// 获取连接
	        System.out.println("连接成功！");
	       /* String sql = "SELECT 1 AS needmakecardnum,"+
	        		  " bundle.*,"+
	        		  " product.productcount,"+
	        		  " product.TASKPRODUCTSERIALNO,"+
	        		  " taskorder.priority,"+
	        		  " orderline.productstyleno,"+
	        		  " orderline.bakstr20 AS cuttingsize"+
	        		" FROM production_task_bundle bundle"+
	        		" LEFT JOIN production_task_product product"+
	        		" ON bundle.TASKPRODUCTCODE = product.TASKPRODUCTCODE"+
	        		" AND bundle.optiontype     = 'tailor'"+
	        		" LEFT JOIN production_task_orderline orderline"+
	        		" ON orderline.taskorderlinecode = bundle.taskorderlinecode"+
	        		" LEFT JOIN production_task_order taskorder"+
	        		" ON taskorder.taskordercode = orderline.taskordercode"+
	        		" WHERE bundle.companycode   ='2e2d377c-82fa-407b-aa28-94a763bd74c7'"+
	        		" AND bundle.bakstr1 = ?"+
	        		" AND bundle.isplaned        =2"+
	        		" AND (bundle.isdelete      IS NULL"+
	        		" OR bundle.isdelete         =0)"+
	        		" AND (product.bakstr1       = '添加成功'"+
	        		" OR product.bakstr1         = '修改成功'"+
	        		" OR product.bakstr1         = '重置计划状态成功,自动拉取工序流成功'"+
	        		" OR product.bakstr1         = '失败：工作中心CJ0不存在'"+
	        		" OR product.bakstr1         = '失败：计划已到生产库中'"+
	        		" OR product.bakstr1         = '商品需重新推送')"+
	        		" ORDER BY taskproductserialno,"+
	        		"  optiontype,"+
	        		"  optionpart";*/
	        String sql = "update PRODUCTION_TASK_BUNDLE bundle set BUNDLENO = '1111111111111111111111111' where bakstr1 = ? and bundleno = '00000000-0000-0000-0000-0000000'";
	        pre = con.prepareStatement(sql);// 实例化预编译语句
	        pre.setString(1, planID);// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
	        pre.executeUpdate();
	        con.commit();
	       /* result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
	        while (result.next()){
	        	// 当结果集不为空时
	        	System.out.println(result);
	        }*/
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
	        try
	        {
	            // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
	            // 注意关闭的顺序，最后使用的最先关闭
	            if (result != null)
	                result.close();
	            if (pre != null)
	                pre.close();
	            if (con != null)
	                con.close();
	            System.out.println("数据库连接已关闭！");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	}

}
