package com.sk.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sk.dbfiles.JDBCUtil;

@WebServlet(name="CourseSelector",value= {"/CourseSelector"})
public class CourseSelector extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void Init(ServletConfig sc) {
		System.out.println("inside the init method");
	}
	
	protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		try {
			System.out.println("inside the service method");
			System.out.println("session of user=="+req.getSession(false).getAttribute("user"));
			System.out.println("current course="+req.getSession(false).getAttribute("current_course"));
			//check if user is not logged in, or current course is not set
			if(req.getSession(false)==null ) //
			{
			System.out.println("session is already created");
			//req.getSession();
			}
			else if(req.getSession(false).getAttribute("current_course")==null)
			{//if no current course
			
			findCourse(req,res);
			
			/*String nocoursemsg = "<script> var err = document.getElementById('error_msg');"
					+"err.innerHTML='no course found';";
			includemainpage(req,res);
			out.println(nocoursemsg);
			System.out.print("no course msg printed");*/
			}
		else {
			System.out.println("finding a new Course");
			findCourse(req,res);
			}
		}
		catch(Exception e)
		{
			System.out.println("an exception occured and caught, tranfering to the main page");
			e.printStackTrace();
			RequestDispatcher rd6 = req.getRequestDispatcher("Main_Page.html");
			rd6.forward(req, res);
		}
	
	}
	
	private void includemainpage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		RequestDispatcher rd1 = req.getRequestDispatcher("Main_Page.html");
		rd1.include(req, res);
	}
	
private void forwardmainpage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		RequestDispatcher rd1 = req.getRequestDispatcher("Main_Page.html");
		rd1.forward(req, res);
	}

	public void destroy() {
		System.out.println("destroy of courseselector called");
		
	}
	
	
	public void findCourse(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		Connection con = null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		PrintWriter out = res.getWriter();
		
		System.out.println("finding the course");
		String coursecode = req.getParameter("course_code");
		System.out.println("course code ="+coursecode);
		
		try {
			con = JDBCUtil.getOraConnection();
			String query = "select * from courses"
					+ " where course_code='"+coursecode+"'";
			ps = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE);
			rs= ps.executeQuery();
		//check if resultset in not null
			if(rs.next()==false)
			{
				System.out.println("no such course. print it");
				
				includemainpage(req,res);
				String jsmessage = "<script> var err= document.getElementById('error_msg');"
				
						+"err.innerHTML='no such course, check again';"
						+"</script>";
				
				out.println(jsmessage);
			}
			else 
			{
				
				System.out.println("course exists");
				req.getSession(false).setAttribute("current_course", coursecode);
				String jsmessage = "<script> var err= document.getElementById('error_msg');"
						
						+"err.innerHTML='COURSE EXITS:';"// print course here only
						+"</script>";
				
				 
				 RequestDispatcher rd3 = req.getRequestDispatcher("Main_Page.html");
				 rd3.include(req,res);// or should it be forward();
				// print course details
				 out.println("<table id=\"course_table\">"
				 		+ "<tr>"
				 		+ "<th>"
				 		+ "Course Code"
				 		+ "</th>"
				 		+ "<th>"
				 		+ "Course Name"
				 		+ "</th>"
				 		+ "<th>"
				 		+ "Course Cordinator"
				 		+ "</th>"
				 		+ "<th>"
				 		+ "Course View"
				 		+ "</th>"
				 		+ "<th>"
				 		+ "Course Download"
				 		+ "</th>"
				 		
				 		+ " </tr> <tr>");
		
					//printing the details
					out.print("<td>"+rs.getString(1)+"</td>");
					out.print("<td>"+rs.getString(2)+"</td>");
					out.println("<td>"+rs.getString(3)+"</td>");
					out.println("<td><form method=\"post\" action=\"ViewSyllabusServlet\" target=\"_blank\"><button type=\"submit\">View</button></form></td>");
					out.println("<td><form action=\"DBFileDownloadServlet\"  method=\"post\">" + 
							"<input type=\"submit\"  value=\"download file\"/>" + 
							"</form> </td>");
					
					out.println("</tr></table>");
						 
				 out.println(jsmessage);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("class not found exception");
			res.getWriter().println("<script> document.getElementById('error_msg').innerHTML='class not found error; ENTER THE COURSE AGAIN';");
			
			e.printStackTrace();
			forwardmainpage(req,res);
		} catch (SQLException e) {
			System.out.println("sql exception");
			res.getWriter().println("<script> document.getElementById('error_msg').innerHTML='SQL ERROR, ENTER THE COURSE AGAIN';");
			
			e.printStackTrace();
			forwardmainpage(req,res);
		}
		
	}
	
}
