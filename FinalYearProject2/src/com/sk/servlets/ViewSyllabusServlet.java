package com.sk.servlets;

import com.sk.dbfiles.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
import java.io.*;

@WebServlet("/ViewSyllabusServlet")
public class ViewSyllabusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = null;
		Statement st = null;
		ResultSet rs = null;
		Connection con =  null;
		try{
		 con = JDBCUtil.getOraConnection();
		
		String course_code = request.getParameter("course_code");
		if(course_code==null)
			course_code= (String) request.getSession(false).getAttribute("current_course");
		String sql1 = "select * from course_syllabus where course_code='"+course_code+"'";
		
		Blob file = null;
		byte[] filedata = null;
	
			st = con.createStatement();
			rs = st.executeQuery(sql1);
			
				//check for the file
				if(rs.next())
				{
					file = rs.getBlob("syllabus_file");
					filedata = file.getBytes(1,(int)file.length());
					response.setContentType("application/pdf");
					response.setHeader("Content-Disposition", "inline");
					response.setContentLength(filedata.length);
					OutputStream output = response.getOutputStream();
					output.write(filedata);
					output.flush();
					output.close();
					
				}
				else
				{
					out = response.getWriter();
					out.println("file not found");	
					
				}
				/*	FIRST		response.setContentType("application/pdf");
				response.setHeader("Content-Disposition", "inline");
				response.setContentLength(filedata.length);
				OutputStream output = response.getOutputStream();
				output.write(filedata);
				output.flush();
				output.close();*/
			}
			catch(SQLException s){
				out= response.getWriter();
				out.println("error while viewing file"+s.getMessage());
				
			}
			catch(Exception e)
			{
				out.println("error while viewing file"+e.getMessage());
				
			}
		
	}
}