package com.example.infomecs;

import java.net.URLEncoder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends Activity{
	EditText user, pwd;
	Button sub,frgtpwd,epbut;
	public static String uname=null, password=null;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.login);
	user=(EditText) findViewById(R.id.lusername);
	pwd=(EditText) findViewById(R.id.password);
	sub=(Button)findViewById(R.id.login_submit);
	frgtpwd=(Button) findViewById(R.id.forgotpwdbutton);
	epbut=(Button) findViewById(R.id.buteditprof);
	
	
	sub.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			 uname=URLEncoder.encode(user.getText().toString());

				password=URLEncoder.encode(pwd.getText().toString());
			Toast.makeText(getApplicationContext(), ""+uname, 3000).show();
	
		System.out.println("Username:"+uname);
		System.out.println("Password:"+password);
		String request1= ConnectionUtil.req_url+"Loginserver?action=login&username="+URLEncoder.encode(uname)+"&password="+URLEncoder.encode(password)+"";
		System.out.println("_______"+request1);
		String response=ConnectionUtil.send(request1);
		System.out.println("_______"+response);
		if(response.trim().equals("LoginSuccessful")){
						
		Toast.makeText(Login.this,"Login Successful", 5000).show();
		Intent in=new Intent(Login.this,Home.class);
		in.putExtra("unm",uname);
		startActivity(in);
		}
		else if (response.trim().equals("LoginFailed")) {
			Toast.makeText(Login.this, "Login failed. Try again", 5000).show();
			}
			
		}
		
	});
	
	frgtpwd.setOnClickListener(new OnClickListener() {
		
		
		public void onClick(View v) {
			String usname=user.getText().toString();
			Toast.makeText(getApplicationContext(), ""+usname, 3000).show();
			if(usname==null)
			{
				Toast.makeText(getApplicationContext(), "Provide username", 5000).show();
			}
			else
			{
				Intent in=new Intent(Login.this,ForgotPwd.class);
				in.putExtra("un", usname);
				startActivity(in);
			}
			
		}
	});
   
	epbut.setOnClickListener(new OnClickListener() {
		

		public void onClick(View v) {
			String usname2=user.getText().toString();
			Toast.makeText(getApplicationContext(), ""+usname2, 3000).show();
			if(usname2==null)
			{
				Toast.makeText(getApplicationContext(), "Provide username", 5000).show();
			}
			else
			{
		Intent epin=new Intent(Login.this,EditProfile.class);
		epin.putExtra("unm",usname2);
		startActivity(epin);
			}
		}
	});
	
}
}






