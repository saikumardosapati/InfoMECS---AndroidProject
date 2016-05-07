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

public class EnterQuestion extends Activity{

	EditText questionet,qstnannamet;
	Button qstnsub;
	String uname=null;
	public static String question123,anonyname123;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enterquestion);
		
		questionet=(EditText) findViewById(R.id.question);
		qstnannamet=(EditText) findViewById(R.id.anoname);
		qstnsub=(Button) findViewById(R.id.qstnsubmit);
		uname=Login.uname;
		String uname1=uname.replace("+"," ");
		qstnannamet.setText(uname1);
		qstnsub.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				question123=URLEncoder.encode(questionet.getText().toString());
				anonyname123=URLEncoder.encode(qstnannamet.getText().toString());
				
				String request1=ConnectionUtil.req_url+"EnterQuestionServlet?action=EnterQuestion&qustnval="+question123+"&anonyname="+anonyname123+"&uname="+URLEncoder.encode(uname)+"";
				System.out.println(request1);
				String response=ConnectionUtil.send(request1);
				System.out.println(response);
				if (response.trim().equals("QuestionSubmitted")) {
					Toast.makeText(EnterQuestion.this,"Question Submitted",2000).show();
					Intent in=new Intent(EnterQuestion.this,Discussion.class);
					startActivity(in);
					} 
					else if(response.trim().equals("SubmissionFailed")) {
					Toast.makeText(EnterQuestion.this,"Submission failed", 2000).show();
					}	
			}
		});
		
		
		
	}
	
	
	
	
}
