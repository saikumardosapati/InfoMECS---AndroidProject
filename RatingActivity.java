package com.example.infomecs;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends Activity{

	EditText answeret;
	TextView avgratevalue;
	Button ratesubmit;
	RatingBar ratebarval;
	public static String ans=null;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ratingactivity);
		answeret=(EditText) findViewById(R.id.anstext);
		avgratevalue=(TextView) findViewById(R.id.rateval);
		ratesubmit=(Button) findViewById(R.id.ratesub);
		ratebarval=(RatingBar) findViewById(R.id.ratingbar);
		ans=getIntent().getStringExtra("ansdesc");
		String qstn=getIntent().getStringExtra("qstndescr");
		System.out.println("!!!!!!!!!!!!!question value"+qstn);
		System.out.println("@@@answer value@@@"+ans);
		answeret.setText(ans);
		answeret.setEnabled(false);
		
		
		String request=ConnectionUtil.req_url+"RatingActivityServlet?action=rateretrieve&question="+URLEncoder.encode(qstn)+"&answer="+URLEncoder.encode(ans)+"";
		System.out.println(request);
		String response=ConnectionUtil.send(request);
		System.out.println("@@@@@@@@@@@@"+response);
		avgratevalue.setText(response);
		
		ratesubmit.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				float ratingvalue=ratebarval.getRating();
				String req=ConnectionUtil.req_url+"RatingActivityServlet?action=ratesubmit&answer="+URLEncoder.encode(ans)+"&rateval="+ratingvalue+"";
				System.out.println(req);
				String res=ConnectionUtil.send(req);
				System.out.println("!!!!!!!!!!!!!!!!!!!"+res);
				if(res.trim().equalsIgnoreCase("Rating submitted"))
				{
					System.out.println("Rating submitted");
					Toast.makeText(getApplicationContext(), "Rating submitted", 2000).show();
					Intent in=new Intent(RatingActivity.this,Discussion.class);
					startActivity(in);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Rating failed", 2000).show();
				}
				
			}
		});
		
		
		
	}
	
	
}
