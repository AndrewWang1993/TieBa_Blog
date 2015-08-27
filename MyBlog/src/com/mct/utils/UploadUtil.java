package com.mct.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * �����ϴ��ļ�������
 * 
 * @author huajian.zhang
 * 
 */
public class UploadUtil {

	/**
	 * 
	 * @param req
	 * @param savePath
	 *            ����·�����磺D:/blog/photo/
	 * @param saveName
	 *            �����ļ������磺acb.jpg(��׺����ʡ��)
	 * @return
	 */
	public static boolean saveFile(HttpServletRequest req, String savePath,
			String saveName) {
		final long MAX_SIZE = 3 * 1024 * 1024;// �����ϴ��ļ����Ϊ 3M
		// ʵ����һ��Ӳ���ļ�����,���������ϴ����ServletFileUpload
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		dfif.setSizeThreshold(4096);// �����ϴ��ļ�ʱ������ʱ����ļ����ڴ��С,������4K.���ڵĲ��ֽ���ʱ����Ӳ��
		dfif.setRepository(new File(savePath + "temp\\"));
		// ���ô����ʱ�ļ���Ŀ¼,web��Ŀ¼�µ�ImagesUploadTempĿ¼
		// �����Ϲ���ʵ�����ϴ����
		ServletFileUpload sfu = new ServletFileUpload(dfif);

		sfu.setSizeMax(MAX_SIZE);// ��������ϴ��ߴ�

		try {
			// ��request�õ� ���� �ϴ�����б�
			List<FileItem> fileList = sfu.parseRequest(req);
			// �õ������ϴ����ļ�66
			Iterator<FileItem> fileItr = fileList.iterator();
			// ѭ�����������ļ�
			while (fileItr.hasNext()) {
				FileItem fileItem = null;
				// �õ���ǰ�ļ�
				fileItem = (FileItem) fileItr.next();
				// ���Լ�form�ֶζ������ϴ�����ļ���(<input type="text" />��)
				if (fileItem == null || fileItem.isFormField()) {
					continue;
				}
				String u_name = savePath + saveName;
				// �����ļ�
				fileItem.write(new File(u_name));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ��Ⲣ�����ļ���
	public static boolean isDirsExist(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return file.mkdirs();
		}
	}
}