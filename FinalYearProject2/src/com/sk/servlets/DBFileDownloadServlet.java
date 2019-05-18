package com.sk.servlets;

import com.sk.dbfiles.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DBFileDownloadServlet")
@MultipartConfig( fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)


public class DBFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096999; 
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 System.out.println("insode dopost of DBFileDownloadServlet");
		        String course_code = request.getParameter("course_code");
		         if(course_code==null)
		         {
		        	 System.out.println("no course code in request");
		        	 course_code=(String) request.getSession(false).getAttribute("current_course");
		        	 System.out.println("current course  value="+course_code);
		         }
		        Connection conn = null; // connection to the database
		         
		        try {
		            // connects to the database
						Connection con = JDBCUtil.getOraConnection();
					
		        	
		            // queries the database
		            String sql = "SELECT * FROM course_syllabus WHERE course_code = ?";
		            PreparedStatement statement = con.prepareStatement(sql);
		            statement.setString(1, course_code);
		 
		            ResultSet result = statement.executeQuery();
		            if (result.next()) {
		                // gets file name and file blob data
		                String fileName = result.getString("file_name");
		                Blob blob = result.getBlob("syllabus_file");
		                InputStream inputStream = blob.getBinaryStream();
		               
		               // int fileLength = inputStream.available();
		                long fileLength = blob.length();
		                //byte[] bytesoffile = blob.getBytes(1,fileLength);
		                 
		                System.out.println("fileLength = " + fileLength);
		               // System.out.println("bytesoffile.length="+bytesoffile.length);
		 
		                ServletContext context = getServletContext();
		 
		                // sets MIME type for the file download
		                String mimeType = context.getMimeType(fileName);
		                if (mimeType == null) {        
		                    mimeType = "application/octet-stream";
		                }              
		                 
		                // set content properties and header attributes for the response
		                response.setContentType(mimeType);
		               //response.setContentLengthLong(fileLength);
		                response.setContentLengthLong(fileLength);
		                String headerKey = "Content-Disposition";
		                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		                response.setHeader(headerKey, headerValue);
		 
		                // writes the file to the client
		                OutputStream outStream = response.getOutputStream();
		                 
		                byte[] buffer = new byte[BUFFER_SIZE];
		                int bytesRead = -1;
		                 
		                while ((bytesRead = inputStream.read(buffer)) != -1) {
		                    outStream.write(buffer, 0, bytesRead);
		                }
		                 
		                inputStream.close();
		                outStream.close();             
		            } else {
		            	 response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		            	 response.getWriter().print("File not found for the file id: " + course_code);  
		            }
		        }catch (SQLException ex) {
		            ex.printStackTrace();
		            //response.getWriter().print("SQL Error: " + ex.getMessage());
		            throw new ServletException(ex);
		        } catch (ClassNotFoundException e) {
					System.out.println(" catch of class not found");
					e.printStackTrace();
					 throw new ServletException(e);
					
				} finally {
		            if (conn != null) {
		                // closes the database connection
		                try {
		                    conn.close();
		                } catch (SQLException ex) {
		                    ex.printStackTrace();
		                }
		            }    
		
	}

	}
}
