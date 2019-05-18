package com.sk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sk.dbfiles.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(value = {"/doCRUDoperation"})
public class doCRUDoperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
  
    
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		System.out.println("****************************\nentered into the servcice method of doCRUDOperation");
		// if session is valid then 
		if(session==null)
		{
			res.sendRedirect("student_login.html");
		}
		else if(session.getAttribute("logintype").equals("student"))
		{
			System.out.println("student is logged in");
			String jsmessage = "<script> var err = document.getElementById('error_msg');"
					+ "err.innerHTML='student is not allowed to do operations';"
					+ "</script>";
			
			/*try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println("Interrupted exception, caught, sending back to main page");
				e.printStackTrace();
				RequestDispatcher rd = req.getRequestDispatcher("Main_Page.html");
				rd.forward(req, res);
			}*/
			
			System.out.println("includeing the main page with error message");
			RequestDispatcher rd2 = req.getRequestDispatcher("Main_Page.html");
			rd2.include(req, res);
			out.println(jsmessage);
		}
		else {
			try {
				Connection con = JDBCUtil.getOraConnection();
				String query = "select username,status from adminTable where username='"+session.getAttribute("user")+"'";
				
				PreparedStatement ps =  con.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE);
				ResultSet rs = null;
				rs = ps.executeQuery();
				String jsmessage = "";
				while(rs.next())
				{
					System.out.println("rs.next exists\n rs.geString(2)="+rs.getString(2));
					String status = rs.getString(2);
					session.setAttribute("admin_status", status);
					if(status.equalsIgnoreCase("dean"))
					{
						System.out.println("setting the status as dean");
						 jsmessage = "<script> var permission_status= 'dean'; </script>";
					}
					else {
						System.out.println("setting the status as dean");
					jsmessage ="<script> var permission_status='faculty';</script>";	
					
					}
					
				}
				
			getServletContext().getRequestDispatcher("/Main_Page.html").forward(req, res);
				out.println("setting the message for adminoperationpage==="+jsmessage+"\n");
				out.println(jsmessage);
				out.close();
				
				
			} catch (ClassNotFoundException e) {
				System.out.println("class not found exception caught, sending to Main page");
				e.printStackTrace();
				RequestDispatcher rd2 = req.getRequestDispatcher("Main_Page.html");
				rd2.forward(req, res);
			} catch (SQLException e) {
				System.out.println("SQL exception caught, sending to Main page");
				e.printStackTrace();
				RequestDispatcher rd2 = req.getRequestDispatcher("Main_Page.html");
				rd2.forward(req, res);
			}
			
			System.out.print("last line :user is a "+session.getAttribute("admin_status"));
			
			
		}
		
	}

}
