package com.example.infomecs;

import java.net.URLEncoder;

import org.json.JSONArray;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends Activity {
	ListView posts;
	Button postbutton,discussform;
	public static String[] postistitems;
	
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		postbutton=(Button) findViewById(R.id.postbut);
		discussform=(Button) findViewById(R.id.dicussbut);
		posts=(ListView) findViewById(R.id.postlist);
		discussform.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent in=new Intent(Home.this,Discussion.class);
				Toast.makeText(getApplicationContext(), "Directing to discussion", 2000).show();
				startActivity(in);
			}
		});
		postbutton.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				Intent in=new Intent(Home.this,EnterInfo.class);
				Toast.makeText(getApplicationContext(), "Directing to post", 3000).show();
				startActivity(in);
			}
		});
		
		
		
		
		
		String request=ConnectionUtil.req_url+"Homeservlet?action=postlist";
		System.out.println("_____"+request);
		String response=ConnectionUtil.send(request);
		System.out.println("____"+response);
		
		try {
			
			JSONArray arr=new JSONArray(response);
			
			postistitems=new String[arr.length()];
			for (int i = 0; i < arr.length(); i++) {
				
				postistitems[i]=arr.getJSONObject(i).getString("postd");
				
			}	
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	
		ArrayAdapter adapter=new ArrayAdapter(Home.this, android.R.layout.simple_list_item_1,postistitems);
		posts.setAdapter(adapter);
		
		
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
		
		
		posts.setOnItemClickListener(new OnItemClickListener() {

		
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@");
				
				String selectedpost=postistitems[position];
				System.out.println(selectedpost);
				
				
				String req123=ConnectionUtil.req_url+"Homeservlet?action=desc&selectpostvalue="+URLEncoder.encode(selectedpost)+"";
				System.out.println(req123);
				
				String res123=ConnectionUtil.send(req123);
				System.out.println("______"+res123);
				
				Toast.makeText(getApplicationContext(), ""+res123, 3000).show();
				
				Intent i=new Intent(Home.this,ViewPost.class);
				i.putExtra("res", res123);
				startActivity(i);
				
				
				
				
			
				
				
				
				
				
				
			}
		});
		
	}

}
