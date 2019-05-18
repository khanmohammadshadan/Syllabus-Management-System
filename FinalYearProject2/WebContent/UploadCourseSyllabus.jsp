<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> file upload example</title>
</head>
<body>
<nav>
<a href="Main_Page.html"> DASHBOARD</a>
<a href="CreateCourse.jsp"> Create Course  </a>
<a href="UpdateCourse.jsp"> Update Course</a>
<a href="DeleteCourse.jsp"> Delete Course</a>
<a href="ReviewCourse.jsp"> Review Course</a>
</nav>
<p>    <% 
try{
String message = (String)request.getAttribute("message");
out.println(message);
}
catch(Exception e)
{
	out.println("some error occured");
}

%></p>
<form action="FileUploadServlet" method="post" enctype="multipart/form-data">

<table border="0">
<tr>
<td> Enter course_code:</td>
<td> <input type="text" name="course_code" /></td>
</tr>
<tr>
<td> Enter file name</td> <td> <input type="text" name="file_name"/></td>
</tr>
<tr>
<td>upload your file</td>
<td> <input type="file" name="syllabus_file" /></td>
</tr>
<tr>
<td colspan="2"><input type="submit" value="upload"></td></tr>
</table>

</form>


</body>
</html>