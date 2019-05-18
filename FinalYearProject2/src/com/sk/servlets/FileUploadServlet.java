package com.sk.servlets;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sk.dbfiles.JDBCUtil;

@MultipartConfig(maxFileSize=1699999)
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        // gets values of text fields
        String course_code = request.getParameter("course_code");
        String file_name = request.getParameter("file_name");
       
         
        InputStream inputStream = null; // input stream of the upload file
         
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("syllabus_file");
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
         
        Connection con = null; // connection to the database
        PreparedStatement statement = null;
        String message=""; // message will be sent back to client
         
        try {
            con = JDBCUtil.getOraConnection();
            // constructs SQL statement
            String sql = "INSERT INTO  course_syllabus(course_code,file_name, syllabus_file) values (?, ?, ?)";
             statement = con.prepareStatement(sql);
            statement.setString(1, course_code);
             statement.setString(2, file_name);
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
                statement.setBlob(3, inputStream);
            }
            else {
            	System.out.println("input stream is null");
            }
 
            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
            else {
            	message=" file upload failed try again!";
            }
            request.setAttribute("Message", message);
        } catch (SQLException | ClassNotFoundException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
           
         JDBCUtil.cleanup(statement, con);
            // sets the message in request scope
            request.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/UploadCourseSyllabus.jsp").forward(request, response);
        }
    }

}
