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


public class ForgotPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       Connection con=null;
       Statement st=null;
       ResultSet rs=null;
       PrintWriter pw=null;
   
    public ForgotPwdServlet() {
        super();
       
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action=request.getParameter("action");
    	if(action.equalsIgnoreCase("questionlist"))
    	{
        	getQuestionList(request,response);
    	}
    	else if(action.equalsIgnoreCase("selectqstn"))
    	{
    		getSelectQstn(request,response);
    	}
    	
	
    }

	
    

	private void getSelectQstn(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		try {
			st=con.createStatement();
			String selectedqust=request.getParameter("selectqstnvalue");
			String query1="select SEC_QID from SEC_QUESTION where Q_NAME='"+selectedqust+"'";
			System.out.println(query1);
			int selectedqid=0;
			rs=st.executeQuery(query1);
			if(rs.next())
			{
				selectedqid=rs.getInt("SEC_QID");
			}
			System.out.println(selectedqid);
			String securityanswer=request.getParameter("secans");
			String usernameval=request.getParameter("usrnam");
			System.out.println(usernameval);
			String uidquery="select ID_NO from USER_DETAILS where NAME='"+usernameval+"'";
			System.out.println(uidquery);
			int uid=0;
			rs=st.executeQuery(uidquery);
			if(rs.next())
			{
				uid=rs.getInt("ID_NO");
			}
			System.out.println(uid);
			String query2="select SEC_ANS, SEC_QID  from USER_DETAILS where ID_NO='"+uid+"'";
			System.out.println(query2);
			rs=st.executeQuery(query2);
			if(rs.next())
			{
			if((selectedqid==rs.getInt("SEC_QID"))&&(securityanswer.equalsIgnoreCase(rs.getString("SEC_ANS"))))
			{
				pw.print("Matching");
				System.out.println("Matching!!!!!!!!!!!!!!!");
			}
			else
			{
				pw.print("Not matching");
				System.out.println("Not matching!!");
			}
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void getQuestionList(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		try {
			st=con.createStatement();
			pw=response.getWriter();
			String query1="select Q_NAME from SEC_QUESTION";
			rs=st.executeQuery(query1);
			JSONArray arr=new JSONArray();
			while(rs.next())
			{
				JSONObject obj=new JSONObject();
				obj.put("qname", rs.getString("Q_NAME"));
				System.out.println(rs.getString("Q_NAME"));
				arr.put(obj);			
			}
			pw.print(arr);
			
			
			
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
	
    }

}
