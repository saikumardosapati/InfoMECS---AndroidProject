package com.example.infomecs;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class RaiseIssue extends Activity{
	EditText issdescr;
	Button issuesub1;
	public static String postdesc=null;
	public static String uname=null;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.raise_issue);
	issdescr=(EditText) findViewById(R.id.issuedescritpion);
	issuesub1=(Button)findViewById(R.id.issuesub);
	issuesub1.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
		postdesc=ViewPost.desc;
		uname=Login.uname;
		String issuedescrval=issdescr.getText().toString();
		System.out.println("Issue description:"+issuedescrval);
		String request1=ConnectionUtil.req_url+"RaiseIssueServlet?action=riaseIssue&issuedescr123="+URLEncoder.encode(issuedescrval)+"&postdescr="+URLEncoder.encode(postdesc)+"&usrname="+URLEncoder.encode(uname)+"";
		System.out.println("______"+request1);
		String response=ConnectionUtil.send(request1);
		System.out.println("________"+response);
		  if (response.trim().equals("Issue Submitted")) {
			  Toast.makeText(RaiseIssue.this, "Issue submitted successfully", 5000).show();
			 Intent in=new Intent(RaiseIssue.this,Home.class);
			  startActivity(in);
		}			
		  else{
			  Toast.makeText(RaiseIssue.this, "Submission failed", 5000);
		  }
			
		}
	});
	
	
	
	}
	

}
