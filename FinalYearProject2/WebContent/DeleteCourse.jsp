<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DeleteCourse</title>
</head>
<nav>
<a href="Main_Page.html"> DASHBOARD</a>
<a href="CreateCourse.jsp"> Create Course  </a>
<a href="UpdateCourse.jsp"> Update Course</a>
<a href="DeleteCourse.jsp"> Delete Course</a>
<a href="ReviewCourse.jsp"> Review Course</a>
</nav>
<body>
<p style="color:red">
<% String msg = (String)request.getAttribute("message");
	if(msg!=null)
	{
		out.println(msg);
		request.removeAttribute("message");
	}	
	
%></p>
<br>
<form action="DeleteCourseServlet" method="post">
<table>

<tr>
<td> enter course code to delete</td><td> <input type="text" name="course_code"/></td>
</tr>

<tr>
<td><input type="submit" value="delete course"/></td>
</tr>
</table>

</form>
</body>
</html>