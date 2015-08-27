package com.mct.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mct.model.Reply;
import com.mct.utils.DbUtil;
import com.mct.utils.ReplyUtil;

public class GetReplyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		String type = req.getServletPath();
		if (null != type) {
			ArrayList<Reply> list = null;
			type = type.substring(1);
			if (type.equals("r_list")) {
				// �ɷ�ҳ��������
				// ����id
				int article_id = Integer.parseInt(req
						.getParameter("article_id"));
				// ÿ�λ�ȡ�Ļظ���
				String limitStr = req.getParameter("limit");
				if (null != limitStr && !"".equals(limitStr.trim())) {
					int limit = Integer.parseInt(limitStr.trim());
					// ��ǰ�Ѿ����ص�������
					String currentStr = req.getParameter("current");
					int current = 0;
					if (null != currentStr && !"".equals(currentStr.trim())) {
						current = Integer.parseInt(currentStr);
					}
					list = ReplyUtil.getInstance().getReply(
							DbUtil.openDb(), article_id, limit, current);
				}else{
					list = ReplyUtil.getInstance().getReply(
							DbUtil.openDb(), article_id, 0, 0);
				}
			} else if (type.equals("tower_list")) {
				// ����һ����¥����������
				// ��ȡ�ظ������ӵ�id
				int article_id = Integer.parseInt(req
						.getParameter("article_id"));
				// ��ǰ¥���id
				int reply_id = Integer.parseInt(req.getParameter("reply_id"));
				list = ReplyUtil.getInstance().getTowerReply(DbUtil.openDb(),
						article_id, reply_id);
			}
			if (null != list && list.size() > 0) {
				pw.write(JSON.toJSONString(list));
			}
		} else {
			pw.write("{result:-1}");
		}
		pw.flush();
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
