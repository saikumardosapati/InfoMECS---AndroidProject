package com.example.infomecs;
import  java.net.ResponseCache;
import java.net.URLEncoder;

import org.json.JSONArray;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class ForgotPwd extends Activity{
	Spinner questions;
	public static String[] questionitems;
	public static String unamelogin;
	String selectedqustn;
	EditText secans,username;
	Button submit;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotpwd);
		
		secans=(EditText) findViewById(R.id.secanswer);
		submit=(Button) findViewById(R.id.butsubmit);
		questions=(Spinner) findViewById(R.id.secquestion);
		username=(EditText) findViewById(R.id.usernamefp);
		
		Intent ik=getIntent();
		unamelogin=ik.getStringExtra("un");
		
		System.out.println("UUUUUUUUUUUUUUUUUUUser "+unamelogin);
		
		username.setText(unamelogin);
		String request= ConnectionUtil.req_url+"ForgotPwdServlet?action=questionlist";
		System.out.println("________"+request);
		String response=ConnectionUtil.send(request);
		System.out.println("________"+response);
		try {
			JSONArray arr=new JSONArray(response);
			questionitems=new String[arr.length()];
			for(int i=0;i<arr.length();i++){
				questionitems[i]=arr.getJSONObject(i).getString("qname");
			}
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		ArrayAdapter adapter=new ArrayAdapter(ForgotPwd.this, android.R.layout.simple_list_item_1,questionitems);
		questions.setAdapter(adapter);
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
		questions.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
				selectedqustn=questionitems[position];
				System.out.println(selectedqustn);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		submit.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				String secanswerval=secans.getText().toString();
				String usname=username.getText().toString();
				
				String req123=ConnectionUtil.req_url+"ForgotPwdServlet?action=selectqstn&selectqstnvalue="+URLEncoder.encode(selectedqustn)+"&usrnam="+URLEncoder.encode(usname)+"&secans="+secanswerval+"";
				System.out.println(req123);
				
				String res123=ConnectionUtil.send(req123);
				System.out.println("##################################"+res123);
				if(res123.trim().equals("Matching"))
				{
				Toast.makeText(getApplicationContext(), "Logging in", 3000).show();
				Intent i=new Intent(ForgotPwd.this,EditProfile.class);
				startActivity(i);
				}
				else if(res123.trim().equals("Not matching"))
				{
					Toast.makeText(getApplicationContext(), "Invalid credentials", 5000).show();
				}
				
			}
		});
	}

}
