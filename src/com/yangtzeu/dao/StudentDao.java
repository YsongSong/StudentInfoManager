package com.yangtzeu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yangtzeu.model.PageBean;
import com.yangtzeu.model.Student;
import com.yangtzeu.util.DateUtil;
import com.yangtzeu.util.StringUtil;

public class StudentDao {
	
	/**
	 * 学生信息分页查询
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public ResultSet studentList(Connection conn, PageBean pageBean, Student student, String bbirthday, String ebirthday)throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_student s,t_grade g where s.gradeId = g.gradeId");
		
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex = '"+student.getSex()+"'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		/**
		 * 日期范围查询
		 * select * from t_student s,t_grade g where s.gradeId = g.gradeId and birthday >='1990-01-02' and birthday <='2001-01-01'
		 */
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and s.birthday >='"+bbirthday+"'");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and s.ebirthday <='"+ebirthday+"'");
		}
		
		if(pageBean!=null){
			sb.append(" order by s.stuNo offset "+pageBean.getStart()+" row fetch next "+pageBean.getRows()+" rows only ");
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	/**
	 * 获取总记录数
	 * @param conn
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int getCount(Connection conn, Student student, String bbirthday, String ebirthday) throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_student s,t_grade g where s.gradeId = g.gradeId");
		if(StringUtil.isNotEmpty(student.getStuNo())){
			sb.append(" and s.stuNo like '%"+student.getStuNo()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getStuName())){
			sb.append(" and s.stuName like '%"+student.getStuName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getSex())){
			sb.append(" and s.sex = '"+student.getSex()+"'");
		}
		if(student.getGradeId()!=-1){
			sb.append(" and s.gradeId ='"+student.getGradeId()+"'");
		}
		/**
		 * 日期范围查询
		 * select * from t_student s,t_grade g where s.gradeId = g.gradeId and birthday >='1990-01-02' and birthday <='2001-01-01'
		 */
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append(" and s.birthday >='"+bbirthday+"'");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and s.ebirthday <='"+ebirthday+"'");
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString());
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * 删除所选学生信息  delete from tablename where field in(1,3,5)
	 * @param conn
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int studentDelete(Connection conn, String delIds)throws Exception{
		String sql = "delete from t_student where stuNo in("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 学生信息添加
	 * @param conn
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int studentAdd(Connection conn, Student student)throws Exception{
		String sql = "insert into t_student values(?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, student.getStuName());
		pstmt.setString(2, student.getSex());
		pstmt.setString(3, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(4, student.getGradeId());
		pstmt.setString(5, student.getEmail());
		pstmt.setString(6, student.getStuDesc());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 学生信息修改
	 * @param conn
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int studentModify(Connection conn, Student student)throws Exception{
		String sql = "update t_student set stuName=?, sex=?, birthday=?, gradeId=?,email=?, stuDesc=? where stuNo=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, student.getStuName());
		pstmt.setString(2, student.getSex());
		pstmt.setString(3, DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
		pstmt.setInt(4, student.getGradeId());
		pstmt.setString(5, student.getEmail());
		pstmt.setString(6, student.getStuDesc());
		pstmt.setString(7, student.getStuNo());
		return pstmt.executeUpdate();
	}
	
	public boolean getStudentByGradeId(Connection conn, String gradeId)throws Exception{
		String sql = "select * from t_student where gradeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, gradeId);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return true;
		}
		return false;
	}
}
