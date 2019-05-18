<%@page import="java.util.*,java.sql.*,com.sk.dbfiles.*" %>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
      
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Reviewing a course</title>
<style>
table,tr,td,th{
border: 2px solid black;
text-align:center;
border-collapse: collapse;

}

</style>
</head>
<body>
<%if(session.getAttribute("admin_status").equals("faculty")){
		System.out.println("admin is faculty");
		//String jsmessage="<script>document.getElementById('error_msg').innerHTML='faculty cannot review/approve course</script>";
		getServletContext().getRequestDispatcher("/Main_Page.html").forward(request, response);
		/* System.out.println("printing js message");
		out.println(jsmessage); */
}
%>
<nav>
<a href="Main_Page.html"> DASHBOARD</a>
<a href="CreateCourse.jsp"> Create Course  </a>
<a href="UpdateCourse.jsp"> Update Course</a>
<a href="DeleteCourse.jsp"> Delete Course</a>
<a href="ReviewCourse.jsp"> Review Course</a>
</nav>

<p id="error_msg"> 
<% 
String msg = (String) request.getAttribute("message");
if(msg!=null)
	{
		out.println(msg);
		request.removeAttribute("message");
	}
	
	
%>
</p> 
<p>
<%

int linenum = 1;
//check if user is a dean
try{
	//HttpSession session = request.getSession(false);
	System.out.println("inside the reviewcourse.jsp");
	
	
	Connection con = JDBCUtil.getOraConnection();
	Statement statement = con.createStatement();
	String sql = "select * from courses";
	ResultSet rs = statement.executeQuery(sql);

%>
</p>
<table style="border:2px black;border-collapse:collapse;">
<tr>
<th> S.No.</th><th> COURSE CODE</th> <th> COURSE NAME</th> <th>COORDINATOR</th> <th> REVIEW STATUS</th>
</tr>
<%
while(rs.next()){
%>	
<tr>
<td><% out.print(linenum);linenum++; %></td>
<td><%  out.print(rs.getString("course_code")); %></td>
<td><% out.print(rs.getString("course_name")); %></td>
<td><% out.print(rs.getString("course_coordinator"));%></td>
<td><%= rs.getString("review_status") %></td>
</tr>
<% 
}
}//end of try
catch(SQLException sq){
	System.out.println("sql exception occured");
	throw new SQLException();
}
%>
</table>
<hr>
<span> Enter the course_code to review</span>
<form action="ChangeReviewStatusServlet" method="post">
<input type="text" name="course_code"/>
<button type="submit">Reviewed/Approved</button>
</form>

</body>
</html>