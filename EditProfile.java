package com.example.infomecs;
import java.net.ResponseCache;
import java.net.URLEncoder;

import org.json.JSONArray;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditProfile extends Activity {
	public static String userconnum,usermailid,userpwd,userid;
	EditText uid,user,pwd,confirmpwd,contactno,emailid;
	Button subedpro;
	ImageButton uimage;
	public static String uname, upwd;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		uimage=(ImageButton) findViewById(R.id.userimage);
		uid=(EditText) findViewById(R.id.uidep);
		user=(EditText) findViewById(R.id.user_name);
		pwd=(EditText) findViewById(R.id.password);
		confirmpwd=(EditText) findViewById(R.id.confirm_pwd);
		contactno=(EditText) findViewById(R.id.contact_no);
		emailid=(EditText) findViewById(R.id.email_id);
		subedpro=(Button) findViewById(R.id.editSubmit);
		
		
	/*	uname=getIntent().getStringExtra("unm");
		 //upwd=Login.password;
		 String uname1=uname.replace("+"," ");
		 //String pwd1=upwd.replace("+"," "); 
*/		String uname1=Login.uname;
		user.setText(uname1);
		 //pwd.setText(pwd1);
		 //confirmpwd.setText(pwd1);
		
		 String requestE=ConnectionUtil.req_url+"EditProfileServlet?action=retreive&usrnam="+Login.uname+""; 
		 System.out.println(requestE);
		 String responseE=ConnectionUtil.send(requestE);
		 System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+responseE);
		 try {
			 JSONArray arr=new JSONArray(responseE);
			 userid=arr.getJSONObject(0).getString("uidval");
			 userconnum=arr.getJSONObject(0).getString("contnum");
			 usermailid=arr.getJSONObject(0).getString("mailid");
			 userpwd=arr.getJSONObject(0).getString("pwd");
			 System.out.println("!!!!!!!!!!!!!!"+userid);
			 System.out.println("@@@@@@@@@@@@"+userconnum);
			 System.out.println("###############"+usermailid);
			 System.out.println("%%%%%%%%%%%"+userpwd);
			 String userconnum2=userconnum.replace("+", " ");
			 String usermailid2=usermailid.replace("+", " ");
			 String userpwd2=userpwd.replace("+"," ");
			 String userid2=userid.replace("+", " ");
			 contactno.setText(userconnum2);
			 emailid.setText(usermailid2);
			 pwd.setText(userpwd2);
			 confirmpwd.setText(userpwd2);
			 uid.setText(userid2);
			 uid.setEnabled(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 subedpro.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				
				String password=pwd.getText().toString();
				System.out.println("@@@@@@@@@@@@@@@@@@@"+password);
				String confpwd=confirmpwd.getText().toString();
				System.out.println("??????????????????"+confpwd);
				String contactnum=contactno.getText().toString();
				String mailid=emailid.getText().toString();
				String uidvalue=uid.getText().toString();
				String usname=user.getText().toString();
				if(password.equals(confpwd))
				{
					
				String request1=ConnectionUtil.req_url+"EditProfileServlet?action=EditProfile&useridval="+URLEncoder.encode(uidvalue)+"&usernameval="+URLEncoder.encode(usname)+"&passwordval="+URLEncoder.encode(password)+"&contactnumval="+URLEncoder.encode(contactnum)+"&emailidval="+URLEncoder.encode(mailid)+"";
				System.out.println(request1);
				String respo=ConnectionUtil.send(request1);
					System.out.println("________!!!"+respo);
					if(respo.trim().equalsIgnoreCase("User name not available"))
					{
						Toast.makeText(getApplicationContext(), "User name not available", 4000).show();
					}
					else if(respo.trim().equals("EditSuccessful")){
						Toast.makeText(EditProfile.this, "Profile updated", 5000).show();
						Intent in=new Intent(EditProfile.this,Home.class);
						startActivity(in);
					}
					else if(respo.trim().equals("EditFailed")){
						Toast.makeText(EditProfile.this,"Please try again", 5000).show();
					}
					
					
					
				}
				else
				{
					
					Toast.makeText(EditProfile.this,"Passwords do not match", 5000).show();
			
				  }
			}
			
		});
	} 

}



