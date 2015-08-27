package com.mct.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;

import com.mct.model.Reply;

public class ReplyUtil {
	private static ReplyUtil instance;

	private ReplyUtil() {

	}

	public static ReplyUtil getInstance() {
		if (null == instance) {
			instance = new ReplyUtil();
		}
		return instance;
	}

	/**
	 * ���һ����ͨ����
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param u_id
	 *            �û�id
	 * @param a_id
	 *            ����id
	 * @param content
	 *            �ظ�����
	 * @param time
	 *            �ظ�ʱ��
	 * @return
	 */
	public boolean insertReply(Connection conn, int u_id, int a_id,
			String content, String time) {
		return insertReply(conn, u_id, a_id, content, time, 0);
	}

	/**
	 * ���һ����¥����
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param u_id
	 *            �û�id
	 * @param a_id
	 *            ����id
	 * @param content
	 *            �ظ�����
	 * @param time
	 *            �ظ�ʱ��
	 * @param parent
	 *            ���ǵ�¥��id
	 * @return
	 */
	public boolean insertReply(Connection conn, int u_id, int a_id,
			String content, String time, int parent) {
		String sql = "insert into reply (user_id,article_id,content,time,parent) values ("
				+ u_id
				+ ","
				+ a_id
				+ ",'"
				+ content
				+ "','"
				+ time
				+ "',"
				+ parent + ");";
		try {
			Statement statement = conn.createStatement();
			int result = statement.executeUpdate(sql);
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * �ɷ�ҳ����ĳ�����ӵ�����
	 * 
	 * @param conn
	 * @param article_id
	 *            ����id
	 * @param limit
	 *            ÿ�μ��ظ���
	 * @param current
	 *            ��ǰ�Լ��ظ���
	 * @return
	 */
	public ArrayList<Reply> getReply(Connection conn, int article_id,
			int limit, int current) {
		ArrayList<Reply> list = null;
		StringBuffer sb = new StringBuffer(
				"select r._id,r.content,r.time,r.parent,u._id,u.name,u.photo from reply r,users u where article_id=");
		sb.append(article_id);
		sb.append(" and r.user_id = u._id");
		sb.append(" order by r._id desc"); //�����г������ظ�����ǰ��
		if (limit > 0) {
			sb.append(" limit ").append(limit).append(" offset ").append(
					current);
		}
		String sql = sb.toString();
		try {
			Statement statement = conn.createStatement();
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			list = new ArrayList<Reply>();
			while (rs.next()) {
				int rId = rs.getInt("r._id");
				String content = rs.getString("r.content");
				String time = rs.getString("r.time");
				int parent = rs.getInt("r.parent");
				int uId = rs.getInt("u._id");
				String userName = rs.getString("u.name");
				String photo = rs.getString("u.photo");
				list.add(new Reply(rId, uId, article_id, parent, content, time,
						userName, photo));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��������id��������ĳ������¥(�ӵ�ǰ¥�����¥�ļ���)
	 * 
	 * @param conn
	 * @param article_id ����id
	 * @param reply_id ��ʼ�ظ���id
	 * @return
	 */
	public ArrayList<Reply> getTowerReply(Connection conn, int article_id,
			int reply_id) {
		// ��¼��ȡ��ǰ���۵�id
		Stack<Integer> stack = new Stack<Integer>();
		ArrayList<Reply> list = new ArrayList<Reply>();
		stack.push(reply_id);
		try {
			Statement statement = conn.createStatement();
			while (!stack.isEmpty()) {
				String sql = "select r._id,r.content,r.time,r.parent,u._id,u.name,u.photo from reply r,users u where article_id="
						+ article_id + " and r._id = " + stack.pop();
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					int rId = rs.getInt("r._id");
					String content = rs.getString("r.content");
					String time = rs.getString("r.time");
					int parent = rs.getInt("r.parent");
					int uId = rs.getInt("u._id");
					String userName = rs.getString("u.name");
					String photo = rs.getString("u.photo");
					list.add(new Reply(rId, uId, article_id, parent, content,
							time, userName, photo));
					// ������ǵĻظ�id��0��ѹ��ջ�м�����������¥��
					if (parent != 0) {
						stack.push(parent);
					}
				}
				rs.close();
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
