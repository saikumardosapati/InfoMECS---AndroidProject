package com.example.infomecs;
import java.net.URLEncoder;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Discussion extends Activity{
	Button poseqstnbut; 
	ListView questions;
	public static String[] qstnlistitems;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discussion);
		poseqstnbut=(Button) findViewById(R.id.questbut);
		questions=(ListView) findViewById(R.id.postlist);
		poseqstnbut.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				
				Intent in=new Intent(Discussion.this,EnterQuestion.class);
				Toast.makeText(getApplicationContext(), "Directing to pose", 3000).show();
				startActivity(in);

			}
		});
		
		String request=ConnectionUtil.req_url+"DiscussionServlet?action=questionslist";
		System.out.println("_____"+request);
		String response=ConnectionUtil.send(request);
		System.out.println("____"+response);
		
		try {
			
			JSONArray arr=new JSONArray(response);
			
			qstnlistitems=new String[arr.length()];
			for (int i = 0; i < arr.length(); i++) {
				
				qstnlistitems[i]=arr.getJSONObject(i).getString("qstnd");
				
			}	
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	
		ArrayAdapter adapter=new ArrayAdapter(Discussion.this, android.R.layout.simple_list_item_1,qstnlistitems);
		questions.setAdapter(adapter);
		
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
		
		
		questions.setOnItemClickListener(new OnItemClickListener() {

			
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
				
				String selectedqstn=qstnlistitems[position];
				System.out.println(selectedqstn);
				
				
				String req123=ConnectionUtil.req_url+"DiscussionServlet?action=desc&selectqstnvalue="+URLEncoder.encode(selectedqstn)+"";
				System.out.println(req123);
				
				String res123=ConnectionUtil.send(req123);
				System.out.println("______"+res123);
				
				Toast.makeText(getApplicationContext(), ""+res123, 2000).show();
				
				Intent i=new Intent(Discussion.this,ViewQuestions.class);
				i.putExtra("res", res123);
				startActivity(i);
				
				
				
				
			
				
				
				
				
				
				
			}
		});
		
		
	}

}
