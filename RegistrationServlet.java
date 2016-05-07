package com.serverside;

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
 * Servlet implementation class RegistrationServlet
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		String idnumber=request.getParameter("uid");
		System.out.println(idnumber);
		String username=request.getParameter("uname");
		System.out.println(username);
		String useremail=request.getParameter("uemail");
		System.out.println(useremail);
		String gender=request.getParameter("ugender");
		System.out.println(gender);
		String connum=request.getParameter("uconnum");
		System.out.println(connum);
		String useraddr=request.getParameter("uaddr");
		System.out.println(useraddr);
		String secque=request.getParameter("sec_que");
		System.out.println(secque);
		String secans=request.getParameter("sec_ans");
		System.out.println(secans);
		String password=request.getParameter("pwd");
		System.out.println(password);
		String confpwd=request.getParameter("confpwd");
		System.out.println(confpwd);
		String deptid=request.getParameter("deptid");
		System.out.println(deptid);
		String batchid=request.getParameter("batch");
		System.out.println(batchid);
		PrintWriter pw=response.getWriter();
		String query="insert into USER_DETAILS values('"+username+"',"+idnumber+",'"+useremail+"','"+gender+"',"+connum+",'"+useraddr+"','"+secans+"','"+password+"',"+deptid+","+batchid+","+secque+",null)";
		try {
			con=DataBaseConnection.getConnection();
			st=con.createStatement();
			if(password.equals(confpwd))
			{
			int n=st.executeUpdate(query);
			if(n>0)
			{
				pw.print("Inserted");
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			}
		finally
		{
			DataBaseConnection.closeAll(rs, st, con);
		}

	}

}
