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
import com.yangtzeu.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GradeSaveServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String gradeName = request.getParameter("gradeName");
		String gradeDesc = request.getParameter("gradeDesc");
		String gradeId = request.getParameter("gradeId");
		Grade grade=new Grade(gradeName,gradeDesc);
		if(StringUtil.isNotEmpty(gradeId)){
			grade.setGradeId(Integer.parseInt(gradeId));
		}
		Connection conn = null;
		try{
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = 0;
			if(StringUtil.isNotEmpty(gradeId)){
				saveNums = gradeDao.gradeModify(conn, grade);
			}else{
				saveNums = gradeDao.gradeAdd(conn, grade);
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true"); 
				result.put("errorMsg", "ÃÌº” ß∞‹");
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
