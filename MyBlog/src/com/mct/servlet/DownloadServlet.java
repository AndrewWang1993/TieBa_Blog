package com.mct.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// File file = new File("D:\\upload_test\\pic_001.png");
		// download(file, resp);
		resp.setContentType("text/html;charset=GBK");
		// �õ������ļ�������
		// String filename=request.getParameter("filename");
		// ���������������
		String filename = new String(req.getParameter("filename").getBytes(
				"iso-8859-1"), "gbk");
		// ����file����
		File file = new File(UpdateUserInfo.PHOTO_PATH + filename);
		// ����response�ı��뷽ʽ
		resp.setContentType("application/x-msdownload");

		// д��Ҫ���ص��ļ��Ĵ�С
		resp.setContentLength((int) file.length());
		// ���ø����ļ���
		// response.setHeader("Content-Disposition","attachment;filename="+filename);
		// �����������
		resp.setHeader("Content-Disposition", "attachment;filename="
				+ new String(filename.getBytes("gbk"), "iso-8859-1"));
		// �����ļ���i/o��
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buReader = new BufferedInputStream(fis);
		byte[] b = new byte[1024];// �൱�����ǵĻ���
		// ��response�����еõ������,׼������
		OutputStream toClient = resp.getOutputStream();
		int num;
		// ��ʼѭ������
		while ((num = buReader.read(b)) != -1) {
			// ��b�е�����д���ͻ��˵��ڴ�
			toClient.write(b, 0, num);
		}
		// ��д�뵽�ͻ��˵��ڴ������,ˢ�µ�����
		toClient.flush();
		toClient.close();
		fis.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}