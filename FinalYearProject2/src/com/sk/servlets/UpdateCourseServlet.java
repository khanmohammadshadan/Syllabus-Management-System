package com.sk.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sk.dbfiles.JDBCUtil;


@WebServlet(name="UpdateCourseServlet",value={"/UpdateCourseServlet"})
public class UpdateCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("INSIDE THE DOPOST METHOD");
		String course_code = req.getParameter("course_code");
		String new_course_name = req.getParameter("course_name");
		String new_course_coordinator = req.getParameter("course_coordinator");
		
		try {
			Connection con = JDBCUtil.getOraConnection();
			String sql = "update courses set course_name='"+new_course_name+"'"
					+", course_coordinator='"+new_course_coordinator+"'"+" where course_code='"+course_code+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			int updatecount = ps.executeUpdate();
			if(updatecount>0)
			{
				System.out.println("update successful, record updated"+updatecount);
				req.setAttribute("message"," Update successful");
				getServletContext().getRequestDispatcher("/UpdateCourse.jsp").forward(req, res);
			}
			else
			{
				System.out.println("update may have failed, updatecount="+updatecount);
			}
		
		
		} catch (ClassNotFoundException | SQLException e)
		{
			
			System.out.println("exception occured, details below");
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/AdminOperation.html").forward(req, res);
			
		}
		
System.out.println("reached my end of updatecourseservlet");	
	}

}
