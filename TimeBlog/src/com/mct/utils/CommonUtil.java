package com.mct.utils;

import android.content.Context;
import android.widget.Toast;

public class CommonUtil {
	//���������ص�ͷ�񻺴�
	public static final String PHOTO_CACHE_PATH = "/sdcard/blog/photo/";

	public static void showMsg(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
