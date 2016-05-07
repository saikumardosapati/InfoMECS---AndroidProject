package com.serverside;

import java.io.File;
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

/**
 * Servlet implementation class FileUpload1
 */
public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection con=null;
	Statement st=null;
	ResultSet rs=null;
	PrintWriter pw=null;
	
	
	private File tmpDir;
	private File destinationDir1;
	     String fileurl="";
	     String fileurl1="";
	     String temppath="";
	     float filesize=0;
	     String filetype="";
       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload1() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	try
		{
			
			String fpath1="D:/Workspace-juno(eclipse)/ServerSideProject/WebContent/image";
			System.out.println(fpath1);
			
			destinationDir1 = new File(fpath1);
	     
			System.out.println(destinationDir1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("do post of traffic  upload servlet called");
		String information=request.getParameter("information");
		String anoname=request.getParameter("anonyname");
		String uname=request.getParameter("uname");
		int uidvalue=0;
		int postcount=1;
		String fileurl=request.getParameter("filepathvalue");
		try
		{
			con=DataBaseConnection.getConnection();
			st=con.createStatement();
			pw=response.getWriter();
			String uidquey="select ID_NO from USER_DETAILS where NAME='"+uname+"'";
			System.out.println(uidquey);
		
			rs=st.executeQuery(uidquey);
		
			if(rs.next())
			{
				uidvalue=rs.getInt("ID_NO");
			}
			
		
			String countquery="select count(*) from POSTING";
			System.out.println(countquery);
			
			rs=st.executeQuery(countquery);
			if(rs.next())
			{
			postcount=rs.getInt(1);
			postcount=postcount++;
			}
		
			String query="insert into POSTING values("+postcount+",'"+information+"',sysdate,'"+anoname+"',"+postcount+","+uidvalue+")";
			System.out.println(query);
			int n=st.executeUpdate(query);
			int fileid=0;
			String fileidquery="select count(*) from FILE_DETAILS";
			rs=st.executeQuery(fileidquery);
			if(rs.next())
			{
			fileid=rs.getInt(1);
			}
			String queryfileupdate="insert into FILE_DETAILS values('"+fileid+"',null,"+fileurl+",sysdate,'"+postcount+"')";
			int p=st.executeUpdate(queryfileupdate);
			if((n>0)&&(p>0))
			{
				System.out.println("Successful");
				pw.print("InformationSubmitted");
			}
			else
			{
				System.out.println("Failed");
				pw.print("SubmissionFailed");
			}
		}
		catch(Exception e)
		{
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
}
