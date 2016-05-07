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

public class RiCmnt extends Activity {
	Button submitiscmt;
	EditText iscmntdesc;
	public static String cmntdesc=null;
	public static String uname=null;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ricmnt);
		submitiscmt=(Button)findViewById(R.id.issuesubcmnt);
		iscmntdesc=(EditText) findViewById(R.id.issuedescritpioncmnt);
		submitiscmt.setOnClickListener(new OnClickListener() {
			
		
			public void onClick(View v) {
				String isscmntval=iscmntdesc.getText().toString();
				cmntdesc=getIntent().getStringExtra("CMNTClicked");
				uname=Login.uname;
				String request=ConnectionUtil.req_url+"RiCmntServlet?action=riaseIssue&issuedescr123="+URLEncoder.encode(isscmntval)+"&cmntdescr="+URLEncoder.encode(cmntdesc)+"&usrname="+URLEncoder.encode(uname)+"";
				System.out.println("______"+request);
				String response=ConnectionUtil.send(request);
				System.out.println("________"+response);
				  if (response.trim().equals("Issue Submitted")) {
					  Toast.makeText(RiCmnt.this, "Issue submitted successfully", 5000).show();
					 Intent in=new Intent(RiCmnt.this,ViewPost.class);
					  startActivity(in);
				}			
				  else{
					  Toast.makeText(RiCmnt.this, "Submission failed", 5000);
				  }
				
			}
		});
	}

}
