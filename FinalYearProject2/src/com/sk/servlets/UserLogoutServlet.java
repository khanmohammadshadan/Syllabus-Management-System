package com.sk.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserLogoutServlet
 */
@WebServlet(
		description = "loggs user out", 
		urlPatterns = { 
				"/UserLogoutServlet", 
				"/LogOut.jtc"
		})
public class UserLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("inside the serice mthod of UserLogoutServlet");
	HttpSession session = request.getSession();
	session.removeAttribute("user");
	session.removeAttribute("current_course");
	session.removeAttribute("logintype");
	session.invalidate();
	response.sendRedirect("student_login.html");
	
	
	}

}
