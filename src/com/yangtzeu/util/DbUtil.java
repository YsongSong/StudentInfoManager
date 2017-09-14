package com.yangtzeu.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库工具类
 * @author Administrator
 *
 */
public class DbUtil {
	
	private String dbUrl="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=db_student";//数据库连接地址
	private String dbUserName="sa";//登录名
	private String dbPassword="123";//密码
	private String jdbcName="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动名称
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws Exception
	 */
	public Connection getCon()throws Exception{
		Class.forName(jdbcName);
		Connection con=DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}
	
	/**
	 * 关闭数据库连接
	 * @param con
	 * @throws Exception
	 */
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}
	
	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			dbUtil.getCon();
			System.out.println("数据库连接成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据库连接失败！");
		}
	}
}
