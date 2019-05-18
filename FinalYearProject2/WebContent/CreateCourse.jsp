<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create New Course</title>
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
<%
String msg = (String) request.getAttribute("message");
if(msg!=null)
{
	out.println(msg);
	request.removeAttribute("message");
}
%>
</p>

<form action="CreateNewCourseServlet" method="post">
<table>

<tr>
<td> Enter new course code </td><td><input type="text" name="course_code"/></td>
</tr>

<tr>
<td> Enter Course Name </td><td> <input type="text" name="course_name"/></td>
</tr>

<tr>
 <td>Enter Course Coordinator name</td><td> <input type="text" name="course_coordinator"/></td>
 </tr>
 
 <tr>
 <td><input type="submit" /></td>
 </tr>

</table>

</form>
<p> <a href="UploadCourseSyllabus.jsp"> click to upload new course syllabus</a>
</body>
</html>