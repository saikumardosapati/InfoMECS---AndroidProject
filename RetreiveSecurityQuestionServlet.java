package com.serverside;

import gvjava.org.json.JSONArray;
import gvjava.org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RetrieveSecurityQuestionServlet
 */
public class RetrieveSecurityQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveSecurityQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		ResultSet rs=null;
		Statement st=null;
		String query="select * from SEC_QUESTION";
		JSONArray array=new JSONArray();
		PrintWriter pw=response.getWriter();
		try
		{
			con=DataBaseConnection.getConnection();
			st=con.createStatement();
			rs=st.executeQuery(query);
			while(rs.next())
			{
				JSONObject object=new JSONObject();
				object.put("secid", rs.getString(1));
				object.put("secname", rs.getString(2));
				array.put(object);
			}
			JSONObject obj1=new JSONObject();
			obj1.put("secresult", array);
			pw.println(obj1);
			System.out.println(obj1);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DataBaseConnection.closeAll(rs, st, con);
		}
	}
				
		
}


