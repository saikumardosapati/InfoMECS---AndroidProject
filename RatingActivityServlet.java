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

public class RatingActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con=null;
	Statement st=null;
	PrintWriter pw=null;
	ResultSet rs=null;  
    
    public RatingActivityServlet() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getParameter("action");
		if(action.equalsIgnoreCase("rateretrieve")){
			getRateretrieve(request,response);
			}
		
		else if(action.equalsIgnoreCase("ratesubmit")){
			getRatesubmit(request,response);
		}
	}

	
	private void getRateretrieve(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			con=DataBaseConnection.getConnection();
			st=con.createStatement();
			pw=response.getWriter();
			String qstnval=request.getParameter("qstn");
			String ansval=request.getParameter("answer");
			System.out.println("++++++++++++"+ansval);
			int ansid=0;
			float sum=0.0f;
			String ansidquery="select ANS_ID from ANSWER where ANS_DESCR='"+ansval+"'";
			rs=st.executeQuery(ansidquery);
			System.out.println("*******query is*****"+ansidquery);
			if(rs.next())
			{
				ansid=rs.getInt("ANS_ID");
			}
			//float totratecount=0.0f;
			float ratecount=0.0f;
			/*String ratequery="select RATE_VAL_COUNT from RATING where ANS_ID="+ansid+"";
			rs=st.executeQuery(ratequery);
			if(rs.next())
			{
				ratecount=Float.parseFloat(rs.getString("RATE_VAL_COUNT"));
				
			}*/
			int count=1;
			String totratemems="select count(*) from RATING where ANS_ID="+ansid+"";
			rs=st.executeQuery(totratemems);
			if(rs.next())
			{
				count=rs.getInt(1);
			
			}
			String qsum="select RATE_VAL from RATING where ANS_ID="+ansid+"";
			System.out.println(qsum);
			rs=st.executeQuery(qsum);
			while(rs.next())
			{
				sum=sum+Float.parseFloat(rs.getString("RATE_VAL"));
			
				System.out.println("@@@@@@@@@@@@@"+sum);
				
			}
	
			
		float totratecount=sum/Float.parseFloat(String.valueOf(count));
			System.out.println("#####value is #######"+totratecount);
			//String rat=String.valueOf(totratecount);
			pw.print(totratecount);
			
						
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


	private void getRatesubmit(HttpServletRequest request,
			HttpServletResponse response) {
		
			try {
			con=DataBaseConnection.getConnection();
			st=con.createStatement();
			pw=response.getWriter();
			
			String ansval=request.getParameter("answer");
			String ratevalue=request.getParameter("rateval");
			
			System.out.println("!!!!!!!!!!!!!!"+ansval);	
			System.out.println("@@@@@@@@@@@@@@@@"+ratevalue);	
			
			int rateidcount=0;
			int ansid=0;
			
			String query="select count(*) from RATING";
			System.out.println(query);
			rs=st.executeQuery(query);
			
			if(rs.next())
			{
				rateidcount=rs.getInt(1);
				rateidcount=rateidcount++;
			}
			
			
			String ansidquery="select ANS_ID from ANSWER where ANS_DESCR='"+ansval+"'";
			rs=st.executeQuery(ansidquery);
			if(rs.next())
			{
				ansid=rs.getInt("ANS_ID");
			System.out.println("+++++id value is::++"+ansid);
				
			}
			
			
			float rateval=Float.parseFloat(ratevalue);
			float ratevalcount=0.0f;
			String ratevalquery="select RATE_VAL_COUNT from RATING where ANS_ID="+ansid+"";
			rs=st.executeQuery(ratevalquery);
			if(rs.next())
			{
				ratevalcount=Float.parseFloat(rs.getString("RATE_VAL_COUNT"));
				ratevalcount=ratevalcount+rateval;
			}
			
			
			String rateinsertquery="insert into RATING values("+rateidcount+",'"+ratevalue+"','"+ratevalcount+"',"+ansid+")";
			System.out.println(rateinsertquery);
			int n=st.executeUpdate(rateinsertquery);
			if(n>0)
			{
				pw.print("Rating submitted");
			}
			else
			{
				pw.print("Rating failed");
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
