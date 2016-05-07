
package com.serverside;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONObject;

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


public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	PrintWriter pw=null;
    ResultSet rs=null;
    
    
    public EditProfileServlet() {
        super();
        }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		if(action.equalsIgnoreCase("EditProfile"))
		{
		getEditProfile(request,response);
		}
		else if(action.equals("retreive"))
		{
			getRetreive(request,response);
		}
	}

	
	private void getRetreive(HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			con=DataBaseConnection.getConnection();
			pw=response.getWriter();
			st=con.createStatement();
			String unameep=request.getParameter("usrnam");
			System.out.println("********unameep********"+unameep);
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+unameep);
			String queryret="select * from USER_DETAILS where NAME='"+unameep+"'";
			System.out.println(queryret);
			
			rs=st.executeQuery(queryret);
			JSONArray arr=new JSONArray();
			if(rs.next())
			{
			JSONObject obj=new JSONObject();
			obj.put("pwd", rs.getString("PASSWORD"));
			obj.put("contnum", rs.getString("CONTACT_NO"));
			obj.put("mailid", rs.getString("EMAIL_ID"));
			obj.put("uidval", rs.getString("ID_NO"));
			System.out.println("^^^^^   "+rs.getString("PASSWORD"));
			System.out.println("@@@@@     "+rs.getString("CONTACT_NO"));
			System.out.println("%%%%%%  "+rs.getString("EMAIL_ID"));
			System.out.println("*****   "+rs.getString("ID_NO"));
			
			arr.put(obj);
			}
			pw.print(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}


	private void getEditProfile(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		String userid=request.getParameter("useridval").toString();
		String uname=request.getParameter("usernameval").toString();
		String pwd=request.getParameter("passwordval").toString();
		
		String contactnum=request.getParameter("contactnumval").toString();
		String emailid=request.getParameter("emailidval").toString();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String unameuniqquery="select ID_NO from USER_DETAILS where NAME='"+uname+"'";
			System.out.println(unameuniqquery);
			rs=st.executeQuery(unameuniqquery);
			int i=0;
			while(rs.next())
			{
				i=i++;
			}
			if(i>1)
			{
				pw.print("User name not available");
			}
			else
			{
			String query="update USER_DETAILS SET NAME='"+uname+"', CONTACT_NO='"+contactnum+"', EMAIL_ID='"+emailid+"', PASSWORD='"+pwd+"' WHERE ID_NO="+userid+"";
			System.out.println(query);
			int n=st.executeUpdate(query);
			if (n>0) {
				pw.print("EditSuccessful");
				
			} 
			else {
				pw.print("EditFailed");
			}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally
		{
			
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
		
	}

}
