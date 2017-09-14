package com.yangtzeu.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yangtzeu.dao.GradeDao;
import com.yangtzeu.dao.StudentDao;
import com.yangtzeu.util.DbUtil;
import com.yangtzeu.util.ResponseUtil;

import net.sf.json.JSONObject;

public class GradeDeleteServlet extends HttpServlet {
	
	DbUtil dbUtil = new DbUtil();
	GradeDao gradeDao = new GradeDao();
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
			String str[] = delIds.split(",");
			for(int i=0;i<str.length;i++){
				boolean flg = studentDao.getStudentByGradeId(conn, str[i]);
				if(flg){
					result.put("errorIndex", i);
					result.put("error", "此班级下面有学生,不能删除！");
					ResponseUtil.write(response, result);
					return;
				}
			}
			int delNums = gradeDao.gradeDelete(conn, delIds);
			if(delNums>0){
				result.put("success", "删除成功!");
				result.put("delNums", delNums);
			}else{
				result.put("error", "删除失败！");
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
