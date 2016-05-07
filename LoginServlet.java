package com.serverside;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.driver.DatabaseError;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Connection con=null;
       Statement st=null;
       ResultSet rs=null;
       PrintWriter pw=null;
       
   
    public Loginserver() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String action=request.getParameter("action");
	if(action.equals("login")){
		getLogin(request,response);
	}
	}

	
	private void getLogin(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		
		String uname=request.getParameter("username");
		String pwd=request.getParameter("password");
		System.out.println("____username:"+uname);
		System.out.println("____password"+pwd);
		try{
			pw=response.getWriter();
			
			st=con.createStatement();
			String query="select NAME,PASSWORD from user_details where NAME='"+uname+"' and PASSWORD='"+pwd+"'";
			System.out.println(query);
			rs=st.executeQuery(query);
			if(rs.next()){
			pw.print("LoginSuccessful");
			}
			else{
				pw.print("LoginFailed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			if(rs!=null)
			{
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(st!=null)
			{
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
