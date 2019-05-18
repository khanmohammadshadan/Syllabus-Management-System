package com.sk.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sk.dbfiles.JDBCUtil;

@WebServlet("/CreateNewCourseServlet")
public class CreateNewCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("inside the do post of createnewcourseservlet");
		
		String course_code = req.getParameter("course_code");
		String course_name = req.getParameter("course_name");
		String course_coordinator = req.getParameter("course_coordinator");
		Connection con = null;
		
		 
		try {
			 con= JDBCUtil.getOraConnection();
			
			//first check if this course_code already exits
			String checkCourseSql = "select * from courses where course_code='"+course_code+"'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(checkCourseSql);
			
			//if rs is null;
			if(rs.next())
			{
				System.out.println("some course already exists");
				// if some course exist
				req.setAttribute("message"," course already exists");
				getServletContext().getRequestDispatcher("CreateNewCourse.jsp").include(req, res);
				
			}
			else
			{
				System.out.println(" no such course exits, so creating new course");
				String insertCourseSQL = "insert into courses(course_code,course_name,course_coordinator) values(?, ?, ?)";
				PreparedStatement statement = con.prepareStatement(insertCourseSQL);
				statement.setString(1, course_code);
				statement.setString(2, course_name);
				statement.setString(3, course_coordinator);
				 statement.executeQuery();
				System.out.println("query executed");
				req.setAttribute("message", "course added successfully");
				getServletContext().getRequestDispatcher("/CreateCourse.jsp").forward(req, res);;
				
				
			}
		
		
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("exception occured and caught --- details--");
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/AdminOperation.html").forward(req, res);
		}
		
		
		
	}

}
