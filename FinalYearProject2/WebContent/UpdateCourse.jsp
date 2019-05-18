<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Update course</title>
</head>
<body>
<nav>
<a href="Main_Page.html"> DASHBOARD</a>
<a href="CreateCourse.jsp"> Create Course  </a>
<a href="UpdateCourse.jsp"> Update Course</a>
<a href="DeleteCourse.jsp"> Delete Course</a>
<a href="ReviewCourse.jsp"> Review Course</a>
</nav>
<p style="color:red"> 
<% String msg = (String)request.getAttribute("message");
	if(msg!=null)
	{
		out.println(msg);
		request.removeAttribute("message");
	}
%>
</p>

<form action="UpdateCourseServlet" method="post">

<table>
<tr>
<td> Enter course_code to update</td><td> <input type="text" name="course_code"/></td>
</tr>

<tr>
<td>Enter new name for course</td><td> <input type="text" name="course_name"/></td>
</tr>

<tr>
<td>Enter new course_coordinator </td><td> <input type="text" name="course_coordinator"/></td>
</tr>

<tr><td> <button type="submit"> UPDATE COURSE</button></td></tr>
</table>

</form>
<p> for uploading new course syllabus , go to the link below</p>
<a href="UploadCourseSyllabus.jsp"> Upload a New Course Syllabus</a>

</body>
</html>