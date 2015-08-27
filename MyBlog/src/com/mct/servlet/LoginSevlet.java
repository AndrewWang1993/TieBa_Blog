package com.mct.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.mct.model.User;
import com.mct.utils.DbUtil;
import com.mct.utils.UserDbUtil;

public class LoginSevlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// super.doGet(req, resp);
		resp.setContentType("text/html;charset=UTF-8");
		// 获取用户名
		String userName = req.getParameter("name");
		// 获取密码
		String psw = req.getParameter("psw");
		User u = UserDbUtil.getInstance().login(DbUtil.openDb(), userName, psw);
		PrintWriter pw = resp.getWriter();
		if (null != u) {
			String json = JSON.toJSONString(u);
			pw.write(json);
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
