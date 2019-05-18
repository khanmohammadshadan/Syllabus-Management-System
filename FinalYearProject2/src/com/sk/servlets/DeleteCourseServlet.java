package com.sk.servlets;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sk.dbfiles.JDBCUtil;

/**
 * Servlet implementation class DeleteCourseServlet
 */
@WebServlet("/DeleteCourseServlet")
public class DeleteCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("inside the doPost");
		
		String course_code = req.getParameter("course_code");
		
		try {
			Connection con = JDBCUtil.getOraConnection();
			String sql = " delete courses where course_code='"+course_code+"'";
			Statement statement = con.createStatement();
			int x = statement.executeUpdate(sql);
			  sql = "delete course_syllabus where course_code='"+course_code+"'";
			  System.out.println("x(courses table delete count)="+x);
			  int y = statement.executeUpdate(sql);
			  System.out.println("y(courses_syllabus table delete count)="+y);
			  if(y>0 && x>0) {
					System.out.println(" SUCCESS\n rows updated="+y);
					req.setAttribute("message", " delete successful");
					getServletContext().getRequestDispatcher("/DeleteCourse.jsp").forward(req, res);
					System.out.println("forwarded to deletecourse.jsp page");
				 }
				 else {
					 System.out.println(" no rows updated");
					 req.setAttribute("message", " delete unsuccessful");
					 getServletContext().getRequestDispatcher("/DeleteCourse.jsp").forward(req, res);
				 }
				 
					 
		
		} catch (ClassNotFoundException e) {
			System.out.println("class not found exception occured and caught");
			e.printStackTrace();
			System.out.println("redirecting to Main_Page.html");
			getServletContext().getRequestDispatcher("/Main_Page.html").forward(req, res);
			
		} catch (SQLException e) {
			System.out.println("sql exception occured and caught");
			e.printStackTrace();
			System.out.println("redirecting to Main_Page.html");
			getServletContext().getRequestDispatcher("/Main_Page.html").forward(req, res);
		
			e.printStackTrace();
		}
		
		
		
	}

}
