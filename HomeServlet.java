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


public class Homeservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    Connection con=null;
    Statement st=null;
    ResultSet rs=null;
    PrintWriter pw=null;
    
    public Homeservlet() {
        super();
        
    
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
    	
    	
    	String action=request.getParameter("action");
    	
    	if(action.equalsIgnoreCase("postlist"))
    	{
    		getPostList(request,response);
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
    		String selectpost=request.getParameter("selectpostvalue");
    		String query2="select POST_DESCR, POST_AN_NAM from POSTING where POST_DESCR='"+selectpost+"'";
    		System.out.println(query2);
    		rs=st.executeQuery(query2);
    		
    		JSONArray arr=new JSONArray();
    		if(rs.next())
  
    		{
    			
    			JSONObject obj=new JSONObject();
    			obj.put("postdesc", rs.getString("POST_DESCR"));
    			obj.put("anoname",rs.getString("POST_AN_NAM"));
          System.out.println(rs.getString("POST_DESCR"));
        System.out.println(rs.getString("POST_AN_NAM"));
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


	private void getPostList(HttpServletRequest request,
			HttpServletResponse response) {
	
    	con=DataBaseConnection.getConnection();
    	try {
			pw=response.getWriter();
			st=con.createStatement();
			String query="select POST_DESCR from POSTING ORDER BY POST_DATE DESC";
			System.out.println(query);
			rs=st.executeQuery(query);
		JSONArray arr=new JSONArray();
			while(rs.next())
			{
				JSONObject obj=new JSONObject();
				
				obj.put("postd", rs.getString("POST_DESCR"));
				
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
