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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewQuestions extends Activity{

	TextView name;
	EditText questionet,ansannamet,anset;
	Button ansbut,viewansbut;
	ListView vanslist;
	public static  String qstndesc=null, anname=null,ansname=null;
	public static String[] anslist;
	public static String ansvalue=null;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewquestions);
		name=(TextView) findViewById(R.id.uqstntext);
		questionet=(EditText) findViewById(R.id.posteditText1);
		ansannamet=(EditText) findViewById(R.id.answeranname);
		anset=(EditText) findViewById(R.id.answertext);
		ansbut=(Button) findViewById(R.id.answerbut);
		viewansbut=(Button) findViewById(R.id.viewans);
		vanslist=(ListView) findViewById(R.id.vanslistView1);
		ansname=Login.uname;
		ansannamet.setText(ansname);
		
		
		
		String response=getIntent().getStringExtra("res");
		
		try {
			JSONArray arr=new JSONArray(response);
			qstndesc=arr.getJSONObject(0).getString("qstndesc").toString();
			anname=arr.getJSONObject(0).getString("anoname").toString();	
			System.out.println(qstndesc);
			System.out.println(anname);
			name.setText(anname);
			questionet.setText(qstndesc);
			questionet.setEnabled(false);
			
		
			
			ansbut.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					ansvalue=URLEncoder.encode(anset.getText().toString());
					System.out.println("####Answer Value is#####"+ansvalue);
					
					
					String ansnamvalue=ansannamet.getText().toString();
					System.out.println(ansnamvalue);
					
					
					String req1=ConnectionUtil.req_url+"ViewQuestionsServlet?action=answer&ansuname="+URLEncoder.encode(ansname)+"&answervalue="+ansvalue+"&ansanname="+URLEncoder.encode(anname)+"&questiondesc="+URLEncoder.encode(qstndesc)+"";
					System.out.println(req1);
					String res1=ConnectionUtil.send(req1);
						
					System.out.println(res1);
					Toast.makeText(getApplicationContext(), "Submitted"+res1, 2000).show();
					
				}
			});
			
			vanslist.setOnItemClickListener(new OnItemClickListener() {

				
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String selectedansval=anslist[position];
					Intent in=new Intent(ViewQuestions.this,RatingActivity.class);
					in.putExtra("qstndescr", qstndesc);
					in.putExtra("ansdesc", selectedansval);
					
					startActivity(in);
					
				}
				
				
			});
			
			
			viewansbut.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					String request=ConnectionUtil.req_url+"ViewQuestionsServlet?action=vanss&qstndescrval="+URLEncoder.encode(qstndesc)+"";
					System.out.println("_____@@@@@@"+request);
					String response=ConnectionUtil.send(request);
					System.out.println("____!!!!!!"+response);
					
					try {
						
						JSONArray arr=new JSONArray(response);
						anslist=new String[arr.length()];
						for (int i = 0; i < arr.length(); i++) {
							
							
							
							
							
							anslist[i]=arr.getJSONObject(i).getString("answer");
							
							
							
							
							
						}
						
						
						
						ArrayAdapter<String> a1=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,anslist);
						vanslist.setAdapter(a1);
						
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
				}
			});
			
			
	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}
}
