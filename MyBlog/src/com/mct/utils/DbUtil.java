package com.mct.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * ���ݿ�򿪹���
 * @author huajian.zhang
 *
 */
public class DbUtil {
	private static Connection instance;

	public static Connection openDb() {
		if (null == instance) {
			String driver = "com.mysql.jdbc.Driver";
			// URLָ��Ҫ���ʵ����ݿ���diary_db
			String url = "jdbc:mysql://127.0.0.1:3306/blog_db?Unicode=true&characterEncoding=utf-8";
			// MySQL����ʱ���û���
			String user = "root";
			// Java����MySQL����ʱ������
			String password = "root";
			try {
				// ������������
				Class.forName(driver);
				// �������ݿ�
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
