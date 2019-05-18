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
 * Servlet implementation class ChangeReviewStatusServlet
 */
@WebServlet("/ChangeReviewStatusServlet")
public class ChangeReviewStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		String course_code = req.getParameter("course_code");
		
		try {
			Connection con = JDBCUtil.getOraConnection();
			Statement st = con.createStatement();
			String sql = "update courses set review_status='reviewed' where course_code='"+course_code+"'";
			int update_count= st.executeUpdate(sql);
			
			if(update_count>0)
			{
				System.out.println("reviewd successfully");
				req.setAttribute("message", "course reviewed successfully");
				getServletContext().getRequestDispatcher("/ReviewCourse.jsp").forward(req, res);
			}
			else
			{
				System.out.println("course review attempt failed; count="+update_count);
				req.setAttribute("message", "course review attempt failed");
				getServletContext().getRequestDispatcher("/ReviewCourse.jsp").forward(req, res);
			}
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("exception occured\n caught\n");
			e.printStackTrace();
			getServletContext().getRequestDispatcher("/AdminOperation.html").forward(req, res);
		}
		
	}

}
