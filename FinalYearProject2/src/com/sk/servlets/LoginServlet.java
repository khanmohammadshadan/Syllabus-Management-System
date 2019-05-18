package com.sk.servlets;

import com.sk.dbfiles.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name="LoginServlet",value= {"/studentlogin","/adminlogin"})
public class LoginServlet extends HttpServlet{
	
	private static final long serialVersionUID= 1L;

	public void Init(ServletConfig sc) {
		System.out.println("enter in the Init method of servlet");
		
		
	}
	
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		System.out.println("entered the service method ");
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		String logintype= req.getParameter("logintype");
		
		Connection con =  null;
		PreparedStatement ps= null;
		ResultSet rs=null;
		PrintWriter out = res.getWriter();
		
		try {
			con = JDBCUtil.getOraConnection();
		
		
		if(logintype.equals("student"))
		{
				String query = "select username,password from studentTable where username='"+username+"'";			
				ps = con.prepareStatement(query);	
				rs = ps.executeQuery();
				if(rs.next()==false)				// if no user is found, got student login
				{	
					RequestDispatcher rd1 = req.getRequestDispatcher("student_login.html");
					res.setContentType("text/html");
										//write the message on page
					rd1.include(req, res);
					
					out.println("<script> var err = document.getElementById('error_msg');"
					+" err.innerHTML='no user found';"
							+"</script>");						
					out.close();
					
				}
				else {
											//if user in student exists, compare his password with right password
					String rightpassword = rs.getString(2);
					if(password.equals(rightpassword)) {
						System.out.println("password is correct");
											
						//check if no session exists , if no session then create session
						HttpSession session =  req.getSession();
						session.setAttribute("user", username);
						session.setAttribute("logintype",logintype);
						
						RequestDispatcher rd4 = req.getRequestDispatcher("Main_Page.html");
						rd4.forward(req, res);
						
						//res.sendRedirect("Main_Page.html");// to  main page servlet	
					}
					else {
						//invoke this method when wrong password is enterd
						password_is_wrong(req, res);
					}
				}	
		}
		
		
		//if admin is loggin
		else if(logintype.equals("admin"))
		{
			//executing query
			String adminquery = "select username,password from AdminTable where username='"+username+"'";
			ps = con.prepareStatement(adminquery);
			 rs = ps.executeQuery(adminquery);
			
			 if(rs.next()==true)
			 {
				 System.out.println("rows exits in the resultset");
				 String rightpassword  = rs.getString(2);
				 if(password.equals(rightpassword))
				 {
				 //redirect to the next page
				// res.sendRedirect("Main_Page.html");
					 HttpSession session =  req.getSession();
						session.setAttribute("user", username);
						session.setAttribute("logintype",logintype);	
					 RequestDispatcher rd4 = req.getRequestDispatcher("Main_Page.html");
						rd4.forward(req, res);
				 }
				 else
				 {
					 password_is_wrong(req,res);
				 }
			 }
			 else
			 {
				 System.out.println("  no entry found in either table");
				 RequestDispatcher rd = req.getRequestDispatcher("AdminLogin.html");
					res.setContentType("text/html");
					 out = res.getWriter();
													//write the message on page
					rd.include(req, res);
					out.write("<script> var errmsg = document.getElementById('error_msg');"
							+ "errmsg.innerHTML='no such user found';"
							+ "</script>");
					out.close();
					
			 }
			
			
		}
		}
		catch (ClassNotFoundException e1) {
			System.out.println("class not found exception");
			e1.printStackTrace();
			//when exception occurs, go to loginpage
			goToLoginPage(req,res);
		} catch (SQLException e1) {
			System.out.println("SQL Exception found");
			e1.printStackTrace();
			goToLoginPage(req,res);
			
		}
		catch(NullPointerException nl) {
			System.out.print("a null pointer exception at");
			nl.printStackTrace();
			//see if user was logged in, if yes, then take it to main page, else to loginpage
			HttpSession session = req.getSession();
			if(session.getAttribute("user")==null) {
				System.out.print("user was not logged in, so login page");
				res.sendRedirect("student_login.html");
			}
			else {
				RequestDispatcher rd = req.getRequestDispatcher("Main_page.html");
				rd.forward(req, res);
			}
		}
		catch(Exception e) {
			System.out.print("simple exception catch");
			e.printStackTrace();
			res.sendRedirect("student_login.html");
		}
		finally {
			//closing all resourses
			JDBCUtil.cleanup(rs, ps, con);
		}
		
	}
	
	public void goToLoginPage(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("sending the user to login page");
		res.sendRedirect("LoginServlet");
	}
	
	public void destroy() {
		System.out.println("destroying of the login servlet initiated");
	}
	
	public void password_is_wrong(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		System.out.println("password is incorrect\n");
		
		RequestDispatcher rd = req.getRequestDispatcher("student_login.html");
		PrintWriter out = res.getWriter();
		rd.include(req, res);
		out.println("<script> var err=document.getElementById('error_msg');"
		
				+"err.innerHTML='password is incorrect';"
				+"</script>");
		out.close();
	}
	
	
}
