package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	private static final String query = "INSERT INTO BOOKDATA(BOOKNAME,BOOKEDDITION,BOOKPRICE) VALUES(?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//get printWriter
		PrintWriter pw = res.getWriter();
		
		//set content type
		res.setContentType("text/html");
		
		//GET THE book info
		String bookName = req.getParameter("bookName");
		String bookEddition = req.getParameter("bookEddition");
		float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));
		
		//LOAD jdbc driver
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		//generate the connection
		try (Connection con = DriverManager.getConnection("jdbc:mysql:///book","root","Kng@2002");
				PreparedStatement ps = con.prepareStatement(query);){
			
			ps.setString(1, bookName);
			ps.setString(2, bookEddition);
			ps.setFloat(3, bookPrice);
			
			int count =ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is registered succefully</h2>");
			}
			else {
				pw.println("<h2>Record is not to registered succefully</h2>");
			}
			
		}
		catch(SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h2>");
			}
		catch(Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
		
	}
	
	
	
	

}
