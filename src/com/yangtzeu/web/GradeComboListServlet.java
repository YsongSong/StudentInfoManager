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

public class GradeComboListServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		try{
			conn=dbUtil.getCon();
			JSONArray jsonArray= new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "«Î—°‘Ò...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(JsonUtil.formatRsToJsonArray(gradeDao.gradeList(conn, null,null)));
			ResponseUtil.write(response, jsonArray);
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
