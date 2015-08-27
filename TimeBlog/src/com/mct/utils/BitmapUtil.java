package com.mct.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.widget.ImageView;

import com.mct.blog.R;

public class BitmapUtil {
	// ͼ�񻺴�
	private static Map<String, SoftReference<Bitmap>> cache = new HashMap<String, SoftReference<Bitmap>>();

	/**
	 * ��ȡԲ����ʾ��Bitmap
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap out = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(out);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		canvas.drawCircle(width / 2, height / 2, width / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		Rect rect = new Rect(0, 0, width, height);
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return out;
	}

	/**
	 * �����û���Ϣ��ȡͷ�񣬲�����ͷ��ImageView��(�÷���ֱ�ӻ�ȡ��ͼ����Դ���ػ���Ӧ�õ�Ĭ��ͷ��)
	 * 
	 * @param context
	 * @param name�û���
	 * @param isHasIconInRemote���������Ƿ��п����ص�ͷ��
	 * @param iv
	 *            ��ʾͷ���ImageView
	 * @return 0:��ʾ��Ҫ���� 1:��ʾ�������ز������óɹ�
	 */
	public static int getBitmap(Context context, String name,
			boolean isHasIconInRemote, ImageView iv) {
		if (!cache.containsKey(name)) {
			// ������û��ͼ��
			// ����û���ͷ��ɹ�����(˵�������Ѿ����أ����ܻ�δ����)
			if (isHasIconInRemote) {
				// �ȼ�Ȿ���Ƿ��л����ͷ���������
				InputStream in = getLocalIcon(name);
				if (in != null) {
					iv.setImageBitmap(saveIntoCache(in, name));
				} else {
					// ����û�л��棬��Ҫ������������
					return 0;
				}
			} else {
				// �û�û��ͷ��ɹ����أ�����Ĭ��ͷ��
				iv.setImageBitmap(saveIntoCache(context.getResources()
						.openRawResource(R.drawable.user), "default"));
			}
		} else {
			// ������������ͼ���¼
			Bitmap b = null;
			// ��������û��ͷ������ֻ����Ĭ��ͷ��
			if (!isHasIconInRemote) {
				b = cache.get("default").get(); // ��ȡĬ��ͷ��
			} else {
				b = cache.get(name).get();
			}
			if (b != null) {
				// �õ������ͼ�����
				iv.setImageBitmap(b);
			} else {
				// ͼ�񱻻����ˣ��Ƴ���¼Ȼ�����»�ȡ
				cache.remove(name);
				getBitmap(context, name, isHasIconInRemote, iv);
			}
		}
		return 1;
	}

	// ��ͼ��ת�������浽������
	private static Bitmap saveIntoCache(InputStream in, String name) {
		Bitmap b = BitmapFactory.decodeStream(in);
		Bitmap bitmap;
		if ("default".equals(name)) { // Ĭ�ϵĲ�����
			bitmap = b;
		} else {
			bitmap = BitmapUtil.getRoundBitmap(b);
		}
		cache.put(name, new SoftReference<Bitmap>(bitmap));
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	// ��ȡ���ػ����ͷ��
	private static InputStream getLocalIcon(String name) {
		File file = new File(CommonUtil.PHOTO_CACHE_PATH + name);
		if (file.exists()) {
			InputStream in = null;
			try {
				in = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return in;
		}
		return null;
	}

}
