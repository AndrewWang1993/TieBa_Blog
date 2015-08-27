package com.mct.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库打开工具
 * @author huajian.zhang
 *
 */
public class DbUtil {
	private static Connection instance;

	public static Connection openDb() {
		if (null == instance) {
			String driver = "com.mysql.jdbc.Driver";
			// URL指向要访问的数据库名diary_db
			String url = "jdbc:mysql://127.0.0.1:3306/blog_db?Unicode=true&characterEncoding=utf-8";
			// MySQL配置时的用户名
			String user = "root";
			// Java连接MySQL配置时的密码
			String password = "root";
			try {
				// 加载驱动程序
				Class.forName(driver);
				// 连续数据库
				instance = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return instance;
	}
	
	public static String utfString(String oldStr){
		String newStr = oldStr;
		try {
			newStr = new String(oldStr.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newStr;
	}

}
