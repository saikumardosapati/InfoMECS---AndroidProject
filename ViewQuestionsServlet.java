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


public class ViewQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	PrintWriter pw=null;
	ResultSet rs=null;
	
    public ViewQuestionsServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String action=request.getParameter("action");
		if(action.equalsIgnoreCase("answer")){
			getAnswer(request,response);
			}
		
		else if(action.equalsIgnoreCase("vanss")){
			getVanss(request,response);
		}
	}

	
	private void getVanss(HttpServletRequest request,
			HttpServletResponse response) {
		
		con=DataBaseConnection.getConnection();
		try {
			pw=response.getWriter();
    		st=con.createStatement();
			String qstndescrcmnt=request.getParameter("qstndescrval");
			System.out.println(qstndescrcmnt);
			String queryansid="select Q_ID from QUESTION where Q_DESCR='"+qstndescrcmnt+"'";
			System.out.println(queryansid);
			rs=st.executeQuery(queryansid);
			int qstnidvalue=0;
			if(rs.next()){
				qstnidvalue=rs.getInt("Q_ID");
			}
			String querycmnt="select ANS_DESCR from ANSWER where Q_ID="+qstnidvalue+"";
			System.out.println(querycmnt);
			rs=st.executeQuery(querycmnt);
			System.out.println("*************");
			JSONArray arr=new JSONArray();
			while(rs.next()){
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!#######");
				JSONObject obj=new JSONObject();
				obj.put("answer",rs.getString("ANS_DESCR"));
				System.out.println("%%%%%%%%%"+rs.getString("ANS_DESCR"));
				arr.put(obj);
				System.out.println("$$$$$$$$$$$$$$$$$$$");
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
				
					e.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
		
	}


	private void getAnswer(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		
		String ansusrname=request.getParameter("ansuname").replace("+", " ");
		String ansval=request.getParameter("answervalue");
		System.out.println("##################"+ansval);
		String ansanoname=request.getParameter("ansanname").replace("+"," ");
		String qstndesc=request.getParameter("questiondesc").replace("+", " ");
		
		
		try {
			pw=response.getWriter();
			st=con.createStatement();
		
			String query1="select ID_NO from USER_DETAILS where NAME='"+ansusrname+"'";
			System.out.println(query1);
			rs=st.executeQuery(query1);
			int uidvalue=0;
			
			if(rs.next()){
				uidvalue=rs.getInt("ID_NO");
			}
			String query2="select Q_ID from QUESTION where Q_DESCR='"+qstndesc+"'";
			System.out.println(query2);
			rs=st.executeQuery(query2);
			int qstnidvalue=0;
			if(rs.next()){
				qstnidvalue=rs.getInt("Q_ID");
			}
			
			int count=0;
			String query3="select count(*) from ANSWER";
			
			System.out.println(query3);
			rs=st.executeQuery(query3);
			if(rs.next())
			{
				count=rs.getInt(1);
				count=count++;
			}
			
			String query4="insert into ANSWER values("+count+",'"+ansval+"',sysdate,'"+ansanoname+"',"+qstnidvalue+","+uidvalue+")";
			System.out.println(query4);
			int n=st.executeUpdate(query4);
			if(n>0)
			{System.out.println("Answer submitted");
				pw.print("AswerSubmitted");
				
			}
			else
			{
				System.out.println("Not Submitted");
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
				
					e.printStackTrace();
				}
			}
			if(con!=null)
			{
				try {
					con.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
