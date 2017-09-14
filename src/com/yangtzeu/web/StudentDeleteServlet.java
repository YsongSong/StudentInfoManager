package com.yangtzeu.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yangtzeu.dao.StudentDao;
import com.yangtzeu.dao.StudentDao;
import com.yangtzeu.util.DbUtil;
import com.yangtzeu.util.ResponseUtil;

import net.sf.json.JSONObject;

public class StudentDeleteServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String delIds = request.getParameter("delIds");
		Connection conn = null;
		try{
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int delNums = studentDao.studentDelete(conn, delIds);
			if(delNums>0){
				result.put("success", "É¾³ý³É¹¦!");
				result.put("delNums", delNums);
			}else{
				result.put("error", "É¾³ýÊ§°Ü£¡");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
