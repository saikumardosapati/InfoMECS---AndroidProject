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


public class ViewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	PrintWriter pw=null;
	ResultSet rs=null;
     
    public ViewPostServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action=request.getParameter("action");
	if(action.equalsIgnoreCase("comment")){
		getComment(request,response);
		}
	else if (action.equalsIgnoreCase("like")) {
		getLike(request,response);
	}
	else if(action.equalsIgnoreCase("vcmnts")){
		getVcmnts(request,response);
	}
	else if(action.equalsIgnoreCase("likes")){
		getLikes(request,response);
	}
	}

	
	private void getLikes(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		con=DataBaseConnection.getConnection();
		try {
			pw=response.getWriter();
    		st=con.createStatement();
			String postdescrcmnt=request.getParameter("postidvalue");
			System.out.println(postdescrcmnt);
			String querycmntid="select POST_ID from POSTING where POST_DESCR='"+postdescrcmnt+"'";
			System.out.println(querycmntid);
			rs=st.executeQuery(querycmntid);
			int postidvalue=0;
			if(rs.next()){
				postidvalue=rs.getInt("POST_ID");
				
				
				
			}
			String ql="select count(*) from LIKES where POST_ID="+postidvalue+"";
			System.out.println(ql);
			rs=st.executeQuery(ql);
			if(rs.next())
			{
				pw.print(rs.getInt(1)+" "+"Likes");
			}
			else
			{
				pw.print("fail");
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		
	}

	private void getVcmnts(HttpServletRequest request,
			HttpServletResponse response) {
			con=DataBaseConnection.getConnection();
			try {
				pw=response.getWriter();
	    		st=con.createStatement();
				String postdescrcmnt=request.getParameter("postdescrval");
				System.out.println(postdescrcmnt);
				String querycmntid="select POST_ID from POSTING where POST_DESCR='"+postdescrcmnt+"'";
				System.out.println(querycmntid);
				rs=st.executeQuery(querycmntid);
				int postidvalue=0;
				if(rs.next()){
					postidvalue=rs.getInt("POST_ID");
				}
				String querycmnt="select CMNT_DECR,CMNT_AN_NAM from COMMENTS where POST_ID="+postidvalue+"";
				System.out.println(querycmnt);
				rs=st.executeQuery(querycmnt);
				System.out.println("*************");
				JSONArray arr=new JSONArray();
				while(rs.next()){
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!#######");
					JSONObject obj=new JSONObject();
					obj.put("comments",rs.getString("CMNT_DECR"));
					obj.put("cmntnames",rs.getString("CMNT_AN_NAM"));
					System.out.println("%%%%%%%%%"+rs.getString("CMNT_DECR"));
					System.out.println("************"+rs.getString("CMNT_AN_NAM"));
					arr.put(obj);
					System.out.println("$$$$$$$$$$$$$$$$$$$");
				}
				pw.print(arr);
								
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	
	
	private void getLike(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		try {
			int lcount=0;
			pw=response.getWriter();
			st=con.createStatement();
			String luname=request.getParameter("lname").replace("+", " ");
			String postdesc=request.getParameter("postdesc");
			int count=0;
			String query="select count(*) from LIKES";
			rs=st.executeQuery(query);
			if(rs.next()){
				count=rs.getInt(1);
				count=count++;
			}
			String query1="select ID_NO from USER_DETAILS where NAME='"+luname+"'";
			System.out.println(query1);
			rs=st.executeQuery(query1);
			int uidvalue=0;
			
			if(rs.next()){
				uidvalue=rs.getInt("ID_NO");
			}
			
			String query2="select POST_ID from POSTING where POST_DESCR='"+postdesc+"'";
			System.out.println(query2);
			rs=st.executeQuery(query2);
			int postidvalue=0;
			if(rs.next()){
				postidvalue=rs.getInt("POST_ID");
			}
			String lcountquery="select LIKE_CNT from LIKES where POST_ID="+postidvalue+"";
			rs=st.executeQuery(lcountquery);
			if(rs.next()){
				lcount=rs.getInt("LIKE_CNT");
				lcount=lcount++;
			}
			
			String query4="insert into LIKES values("+count+","+lcount+",sysdate,"+null+","+uidvalue+","+postidvalue+")";
			int n=st.executeUpdate(query4);
			if(n>0){
				pw.print("Liked");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getComment(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		try {
			pw=response.getWriter();
			st=con.createStatement();
			String cmntuname=request.getParameter("cmuname").replace("+", " ");
			String cmntvalue=request.getParameter("cmntvalue");
			String cmntanoname=request.getParameter("cmanname");
			String postdesc=request.getParameter("postdesc");
			String query1="select ID_NO from USER_DETAILS where NAME='"+cmntuname+"'";
			System.out.println(query1);
			rs=st.executeQuery(query1);
			int uidvalue=0;
			
			if(rs.next()){
				uidvalue=rs.getInt("ID_NO");
			}
			String query2="select POST_ID from POSTING where POST_DESCR='"+postdesc+"'";
			System.out.println(query2);
			rs=st.executeQuery(query2);
			int postidvalue=0;
			if(rs.next()){
				postidvalue=rs.getInt("POST_ID");
			}
			int count=0;
			String query3="select count(*) from COMMENTS";
			
			System.out.println(query3);
			rs=st.executeQuery(query3);
			if(rs.next())
			{
				count=rs.getInt(1);
				count=count++;
			}
			
			String query4="insert into COMMENTS values("+count+",'"+cmntvalue+"',sysdate,'"+cmntanoname+"',"+postidvalue+","+uidvalue+")";
			System.out.println(query4);
			int n=st.executeUpdate(query4);
			if(n>0)
			{
				System.out.println("Successfully submitted the comment");
				pw.print("CommentSubmitted");
				System.out.println("Comment submitted");
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
