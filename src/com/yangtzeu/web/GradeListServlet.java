package com.yangtzeu.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yangtzeu.dao.GradeDao;
import com.yangtzeu.model.Grade;
import com.yangtzeu.model.PageBean;
import com.yangtzeu.util.DbUtil;
import com.yangtzeu.util.JsonUtil;
import com.yangtzeu.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GradeListServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String gradeName = request.getParameter("gradeName");
		if(gradeName==null){
			gradeName="";
		}
		Grade grade = new Grade();
		grade.setGradeName(gradeName);
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Connection conn = null;
		try{
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			JSONArray jsonArray = JsonUtil.formatRsToJsonArray(gradeDao.gradeList(conn, pageBean, grade));
			int total = gradeDao.getCount(conn, grade);
			result.put("rows", jsonArray);
			result.put("total", total);
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
