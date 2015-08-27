package com.mct.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mct.blog.R;
import com.mct.utils.CommonUtil;

/**
 * ��ӭ����
 * 
 * @author Whunf
 * 
 */
public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		isCacheFolderExist();
		toLogin();
	}

	private void toLogin() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
	}

	//��Ⲣ��������ͷ�񻺴��ļ���
	private boolean isCacheFolderExist() {
		File file = new File(CommonUtil.PHOTO_CACHE_PATH);
		if (file.exists()) {
			return true;
		} else {
			return file.mkdirs();
		}
	}

}