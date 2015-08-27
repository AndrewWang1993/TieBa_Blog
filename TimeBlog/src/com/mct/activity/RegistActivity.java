package com.mct.activity;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.mct.blog.R;
import com.mct.task.UserTask;
import com.mct.utils.BitmapUtil;
import com.mct.utils.CommonUtil;

/**
 * ��¼����
 * 
 * @author Whunf
 * 
 */
public class RegistActivity extends Activity implements OnClickListener {
	// ����֮��ĵ�ַ
	private Uri picUri = Uri.parse("file:///mnt/sdcard/temp00.jpg");
	// �ü�֮��ĵ�ַ
	private Uri imageUri = Uri.parse("file:///mnt/sdcard/temp.jpg");

	private String uploadIconPath;

	private static final int CHOOSE_BIG_PICTURE = 1;
	private static final int TACK_PICTURE = 2;
	private ImageView mIcon;
	private EditText etName, etPsw, etRePsw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist);
		mIcon = (ImageView) findViewById(R.id.m_icon);
		etName = (EditText) findViewById(R.id.et_name);
		etPsw = (EditText) findViewById(R.id.et_psw);
		etRePsw = (EditText) findViewById(R.id.et_re_psw);
		mIcon.setOnClickListener(this);
		findViewById(R.id.m_regist).setOnClickListener(this);
		findViewById(R.id.m_cancel).setOnClickListener(this);

		Bitmap defaultIcon = BitmapFactory.decodeStream(getResources()
				.openRawResource(R.drawable.user));
		mIcon.setImageBitmap(BitmapUtil.getRoundBitmap(defaultIcon));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.m_icon:
			showGetPictureDialog();
			break;
		case R.id.m_regist:
			String userName = etName.getText().toString().trim();
			if (null != userName && !"".equals(userName)) {
				String psw = etPsw.getText().toString();
				if (null != psw && !"".equals(psw)) {
					if (psw.equals(etRePsw.getText().toString())) {
						UserTask task = new UserTask(UserTask.OPT_REGIST,
								mHandler);
						if (null != uploadIconPath) {
							task.execute(userName, psw, uploadIconPath);
						} else {
							task.execute(userName, psw);
						}
					} else {
						CommonUtil.showMsg(RegistActivity.this, "�����������벻ƥ��");
					}
				} else {
					CommonUtil.showMsg(RegistActivity.this, "δ��������");
				}
			} else {
				CommonUtil.showMsg(RegistActivity.this, "δ�����û���");
			}
			break;
		case R.id.m_cancel:
			finish();
			break;
		case R.id.btn_get_from_local:
			// ��ͼ���ȡͼ��
			dialog.dismiss();
			getFromPictureHome();
			break;
		case R.id.btn_get_from_camera:
			// �������ȡͼ��
			dialog.dismiss();
			startCamera();
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				CommonUtil.showMsg(RegistActivity.this, "ע��ʧ��");
				break;
			case 1:
				CommonUtil.showMsg(RegistActivity.this, "ע��ɹ�");
				finish();
				break;
			}
		}

	};

	private Dialog dialog;

	// ��ʾ��ȡͼ�񵯳���
	private void showGetPictureDialog() {
		if (null == dialog) {
			View layout = getLayoutInflater().inflate(
					R.layout.dialog_get_picture_layout, null);
			layout.findViewById(R.id.btn_get_from_local).setOnClickListener(
					this);
			layout.findViewById(R.id.btn_get_from_camera).setOnClickListener(
					this);
			dialog = new Dialog(this, R.style.MyDialog);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(layout);
			Window window = dialog.getWindow();
			window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.width = window.getWindowManager().getDefaultDisplay().getWidth();
			window.setAttributes(lp);
		}
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case TACK_PICTURE:
			// ������֮���ͼ����вü������������ü���Activity
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(picUri, "image/*");
			intent.putExtra("crop", "true");
			// // aspectX aspectY �ǿ�ߵı���
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			intent.putExtra("outputFormat", "JPEG");
			intent.putExtra("return-data", false);
			startActivityForResult(intent, CHOOSE_BIG_PICTURE);
			break;
		case CHOOSE_BIG_PICTURE:
			if (imageUri != null) {
				Bitmap b = BitmapUtil.getRoundBitmap(decodeUriAsBitmap(imageUri));
				mIcon.setImageBitmap(b);
				uploadIconPath = imageUri.getPath();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void startCamera() {
		// ����ϵͳ�����չ���
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
		intent.putExtra("noFaceDetection", false);
		startActivityForResult(intent, TACK_PICTURE);
	}

	// ��ͼ���ȡ���ü�
	private void getFromPictureHome() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra("outputFormat", "JPEG");
		startActivityForResult(intent, CHOOSE_BIG_PICTURE);
	}

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
