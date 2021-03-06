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


public class DiscussionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	  Connection con=null;
	    Statement st=null;
	    ResultSet rs=null;
	    PrintWriter pw=null;
    
    public DiscussionServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String action=request.getParameter("action");
    	
    	if(action.equalsIgnoreCase("questionslist"))
    	{
    		getQuestionslist(request,response);
    	}
    	else if(action.equalsIgnoreCase("desc"))
    	{
    		getDesc(request,response);
    	}
    	
		
	}

	
	private void getDesc(HttpServletRequest request,
			HttpServletResponse response) {
		
		con=DataBaseConnection.getConnection();
    	try {
			pw=response.getWriter();
    		st=con.createStatement();
    		String selectqstn=request.getParameter("selectqstnvalue");
    		String query2="select Q_DESCR, Q_AN_NAME from QUESTION where Q_DESCR='"+selectqstn+"'";
    		System.out.println(query2);
    		rs=st.executeQuery(query2);
    		
    		JSONArray arr=new JSONArray();
    		if(rs.next())
  
    		{
    			
    			JSONObject obj=new JSONObject();
    			obj.put("qstndesc", rs.getString("Q_DESCR"));
    			obj.put("anoname",rs.getString("Q_AN_NAME"));
          System.out.println(rs.getString("Q_DESCR"));
        System.out.println(rs.getString("Q_AN_NAME"));
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


	private void getQuestionslist(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		con=DataBaseConnection.getConnection();
    	try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="select Q_DESCR from QUESTION ORDER BY Q_DATE DESC";
			System.out.println(query);
			rs=st.executeQuery(query);
		JSONArray arr=new JSONArray();
			while(rs.next())
			{
				JSONObject obj=new JSONObject();
				
				obj.put("qstnd", rs.getString("Q_DESCR"));
				
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
