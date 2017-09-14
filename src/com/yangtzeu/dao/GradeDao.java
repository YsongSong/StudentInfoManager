package com.yangtzeu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.yangtzeu.model.Grade;
import com.yangtzeu.model.PageBean;
import com.yangtzeu.util.StringUtil;

public class GradeDao {
	/**
	 * 班级信息查询  分页
	 * @param conn
	 * @param pageBean
	 * @return
	 * @throws Exception
	 */
	public ResultSet gradeList(Connection conn, PageBean pageBean, Grade grade) throws Exception{
		StringBuffer sb = new StringBuffer("select * from t_grade");
		
		if(grade!=null&&StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		
		//Mysql分页
		/*if(pageBean!=null){
			sb.append(" limt "+pageBean.getStart()+","+pageBean.getRows());
		}*/
		//SQLServer分页
		if(pageBean!=null){
			sb.append(" order by gradeId offset "+pageBean.getStart()+" row fetch next "+pageBean.getRows()+" rows only ");
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		return pstmt.executeQuery();
	}
	
	/**
	 * 获取总记录数
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public int getCount(Connection conn, Grade grade)throws Exception{
		StringBuffer sb = new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		PreparedStatement pstmt = conn.prepareStatement(sb.toString().replaceFirst("and", "where"));
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * 删除所选班级信息  delete from tablename where field in(1,3,5)
	 * @param conn
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(Connection conn, String delIds)throws Exception{
		String sql = "delete from t_grade where gradeId in("+delIds+")";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * 班级信息添加
	 * @param conn
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeAdd(Connection conn, Grade grade)throws Exception{
		String sql = "insert into t_grade values(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		return pstmt.executeUpdate();
	}
	
	/**
	 * 班级信息修改
	 * @param conn
	 * @param grade
	 * @return
	 * @throws Exception
	 */
	public int gradeModify(Connection conn, Grade grade)throws Exception{
		String sql = "update t_grade set gradeName=?,gradeDesc=? where gradeId=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade.getGradeName());
		pstmt.setString(2, grade.getGradeDesc());
		pstmt.setInt(3, grade.getGradeId());
		return pstmt.executeUpdate();
	}
}
