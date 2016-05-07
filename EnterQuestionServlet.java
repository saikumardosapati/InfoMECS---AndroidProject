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


public class EnterQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PrintWriter pw=null;
    
    public EnterQuestionServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("EnterQuestion");
		getEnterQuestion(request,response);
	}

	
	private void getEnterQuestion(HttpServletRequest request,
			HttpServletResponse response) {
		con=DataBaseConnection.getConnection();
		
		try{
		pw=response.getWriter();
		st=con.createStatement();
				
		String question=request.getParameter("qustnval");
		String anoname=request.getParameter("anonyname");
		String uname=request.getParameter("uname");
		int uidvalue=0;
		int qstncount=1;
		System.out.println("_____"+question);
		System.out.println("_______"+anoname);
		System.out.println("________"+uname);
		
		String uidquey="select ID_NO from USER_DETAILS where NAME='"+uname+"'";
		System.out.println(uidquey);
		
		rs=st.executeQuery(uidquey);
		
		if(rs.next())
		{
			uidvalue=rs.getInt("ID_NO");
		}
		
		
		String countquery="select count(*) from QUESTION";
		System.out.println(countquery);
		
		
		rs=st.executeQuery(countquery);
		if(rs.next())
		{
			qstncount=rs.getInt(1);
			qstncount=qstncount++;
		}
		
		String query="insert into QUESTION values("+qstncount+",sysdate,'"+question+"','"+anoname+"',"+uidvalue+")";
		
		System.out.println(query);
		int n=st.executeUpdate(query);
		if(n>0)
		{
			pw.print("QuestionSubmitted");
		}
		else
		{
			pw.print("SubmissionFailed");
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
