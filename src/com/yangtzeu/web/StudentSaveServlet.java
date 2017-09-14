package com.yangtzeu.web;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yangtzeu.dao.StudentDao;
import com.yangtzeu.model.Student;
import com.yangtzeu.model.PageBean;
import com.yangtzeu.util.DateUtil;
import com.yangtzeu.util.DbUtil;
import com.yangtzeu.util.JsonUtil;
import com.yangtzeu.util.ResponseUtil;
import com.yangtzeu.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StudentSaveServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	StudentDao studentDao = new StudentDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String stuName = request.getParameter("stuName");
		String sex = request.getParameter("sex");
		String birthday = request.getParameter("birthday");
		String gradeId = request.getParameter("gradeId");
		String email = request.getParameter("email");
		String stuDesc = request.getParameter("stuDesc");
		String stuNo = request.getParameter("stuNo");
		
		Student student = null;
		try {
			student=new Student(stuName,sex,DateUtil.formatString(birthday, "yyyy-MM-dd"),Integer.parseInt(gradeId),email,stuDesc);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuNo)){
			student.setStuNo(stuNo);
		}
		Connection conn = null;
		try{
			conn = dbUtil.getCon();
			JSONObject result = new JSONObject();
			int saveNums = 0;
			if(StringUtil.isNotEmpty(stuNo)){
				saveNums = studentDao.studentModify(conn, student);
			}else{
				saveNums = studentDao.studentAdd(conn, student);
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
