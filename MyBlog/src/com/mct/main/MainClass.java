package com.mct.main;

import java.util.ArrayList;

import com.mct.model.ArticleItem;
import com.mct.utils.ArticleUtil;
import com.mct.utils.DbUtil;
import com.mct.utils.ReplyUtil;
import com.mct.utils.UserDbUtil;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// boolean result = UserDbUtil.getInstance().regist(DbUtil.openDb(),
		// "abd", "123456", "D:/blog/photo/abd.jpg");
		// System.out.println(result);
		// File file = new File("D:/upload_test/pic1.jpg");
		// System.out.println(file.exists());

		// User u = UserDbUtil.getInstance().login(DbUtil.openDb(), "abc",
		// "123456");
		// System.out.println(u);

		// boolean result = UserDbUtil.getInstance().updateUserInfo(
		// DbUtil.openDb(), 4, "�����", "С���", "http://ssss//dsdsd", "123",
		// "��", "110110");
		// System.out.println(result);

		// boolean result = ArticleUtil.getInstance().postingArticle(
		// DbUtil.openDb(), "�ε�", "�ڶ������ӣ������ޱȳ���+1", 10000,
		// String.valueOf(System.currentTimeMillis()), 0);
		// System.out.println(result);

		// ArrayList<ArticleItem> list = ArticleUtil.getInstance().getArticle(
		// DbUtil.openDb(), 2, 1);
		// for (ArticleItem item : list) {
		// System.out.println(item);
		// }
		// ReplyUtil.getInstance().getReply(null, 1, 0, 0);
		boolean result = ReplyUtil.getInstance().insertReply(DbUtil.openDb(),
				10002, 2, "���Ͽ����Ϳ�����˹�ٿ޾���ʥ���ڰ����մ�Ұ���ʥ����",
				String.valueOf(System.currentTimeMillis()),5);
		System.out.println(result);

	}

}