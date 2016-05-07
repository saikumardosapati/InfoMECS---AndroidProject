package com.example.infomecs;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EnterInfo extends Activity {
	EditText info,anoname,filepath1;
	Button butupfile,infosub;
	String uname=null;
	public static String information123,anonyname123;
    
	
	private static int RESULT_LOAD_IMAGE = 1;
	

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enterinfo);
        info=(EditText) findViewById(R.id.information);
    	//butupfile=(Button) findViewById(R.id.upfile);
    	anoname=(EditText) findViewById(R.id.anoname);
    	 uname=Login.uname;
    	 String uname1=uname.replace("+"," ");
    	anoname.setText(uname1);
    	infosub=(Button)findViewById(R.id.infosubmit);
     
        
        infosub.setOnClickListener(new View.OnClickListener() 
        {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				information123=URLEncoder.encode(info.getText().toString());
				anonyname123=URLEncoder.encode(anoname.getText().toString());
			
				System.out.println(uname);
/*				String filepathval=filepath1.getText().toString();
 * 				
*/
				
				
				
				String request1=ConnectionUtil.req_url+"EnterInfoServlet?action=EnterInfo&information="+information123+"&anonyname="+anonyname123+"&uname="+URLEncoder.encode(uname)+"";
				System.out.println("______"+request1);
				String response=ConnectionUtil.send(request1);
				System.out.println("*******response is **************"+response);
				if (response.trim().equals("InformationSubmitted")) {
				Toast.makeText(EnterInfo.this,"Post has been submitted",2000).show();
				/* String request=ConnectionUtil.req_url+"EnterInfoServlet?action=ConNumbers";
				 * 						 * 
						 * android.telephony.SmsManager smsManager=android.telephony.SmsManager.getDefault();
								        smsManager.sendTextMessage(number, null, "Dear customer:"+"reserved", null, null);
								          
								        Toast.makeText(ReserveTableDetailsActivity.this, "sms sent", 3000).show();*/
								    
				
				Intent in=new Intent(EnterInfo.this,Home.class);
				startActivity(in);
				} 
				else if(response.trim().equals("SubmissionFailed")) {
				Toast.makeText(EnterInfo.this,"Submission failed", 2000).show();
				}	
			}
		});
        
        
        
    }
    
    
  
	
			
		
}
