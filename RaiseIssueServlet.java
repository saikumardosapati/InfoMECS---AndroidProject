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


public class RaiseIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   Connection con=null;
   Statement st=null;
   ResultSet rs=null;
   PrintWriter pw=null;
    public RaiseIssueServlet() {
        super();   
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String action=request.getParameter("raiseIssue");
	getrasieIssue(request,response);
	}

	
    private void getrasieIssue(HttpServletRequest request,
			HttpServletResponse response) {
    	con=DataBaseConnection.getConnection();
    	try{
    		pw=response.getWriter();
    		st=con.createStatement();
    	System.out.println("!!!!!!!!!!!@@@@@@@@@########");
    	String issuedescrval=request.getParameter("issuedescr123");
    	System.out.println(issuedescrval);
    	System.out.println("############@@@@@@@@@!!!!!!!!");
    	int count=0;
    	String countquery="select count(*) from RAISE_ISSUE";
    	System.out.println(countquery);
    	System.out.println("@@@@@@@@@@@!!!!!!!!!!!############");
    	rs=st.executeQuery(countquery);
    
		if(rs.next()){
			count=rs.getInt(1);
			count=count++;
		}
    	System.out.println("Issue ID is"+count);
    	
		int postid=1;
    	String postdescr=request.getParameter("postdescr");
    	String postidquery="select POST_ID from POSTING where POST_DESCR='"+postdescr+"'";
    	System.out.println(postidquery);
    	rs=st.executeQuery(postidquery);
    	if(rs.next()){
    		postid=rs.getInt("POST_ID");
    	}
    	System.out.println(postid);
    	int iscount=0;
    	String iscountquery="select ISSUE_CNT from RAISE_ISSUE where POST_ID="+postid+"";
    	System.out.println(iscountquery);
    	rs=st.executeQuery(iscountquery);
    	if(rs.next())
    	{
    		iscount=rs.getInt(1);
    		iscount=iscount++;
    	}
    	System.out.println(iscount);
    	
    	int uid=1;
    	String uname=request.getParameter("usrname");
    	String uidquery="select ID_NO from USER_DETAILS where NAME='"+uname+"'";
    	System.out.println(uidquery);
    	rs=st.executeQuery(uidquery);
    	if(rs.next()){
    		uid=rs.getInt("ID_NO");
    	}
    	System.out.println(uid);
 	   String query="insert into RAISE_ISSUE values("+count+","+iscount+",'"+issuedescrval+"',sysdate,"+postid+","+uid+")";
 	   System.out.println(query);
    	int n=st.executeUpdate(query);
    	if(n>0)
    	{
    		pw.print("Issue Submitted");
    		System.out.println("Issue Submitted");
    		
    	}
    	if(iscount>10)
    	{
    		String quertDelete="delete from posting where POST_ID="+postid+" ";
    		System.out.println(quertDelete);
    		int d=st.executeUpdate(quertDelete);
    	if(d>0)
    	{
    		System.out.println("deleted"+postid);
    	}
    	else
    	{
    		System.out.println("not deleted");
    	}
    		
    		
    	}
    	}
    	catch(Exception e){
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
