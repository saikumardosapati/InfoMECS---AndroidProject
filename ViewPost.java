package com.example.infomecs;

import java.net.URLEncoder;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPost extends Activity {
	Button like, raiseissue, comment, vcmnts;
	ListView vclistview;
	EditText cmntname,cmnttext;
	TextView name;
	EditText post;
	TextView liketextView12;
	ImageButton imgcom;
	public static  String desc=null, anname=null,cmname=null;
	public static String[] cmntslist;
	public static String[] cmntnameslist;
	public static String[] commentarray;
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpost);
		name=(TextView) findViewById(R.id.uposttext);
		post=(EditText) findViewById(R.id.posteditText1);
		like=(Button)findViewById(R.id.like);
		liketextView12=(TextView) findViewById(R.id.liketextView1);
		
		comment=(Button)findViewById(R.id.comment);
		vcmnts=(Button)findViewById(R.id.viewcmnts);
		cmnttext=(EditText)findViewById(R.id.cmnttext);
		cmntname=(EditText)findViewById(R.id.cmmntanname);
		 cmname=Login.uname;
		cmntname.setText(cmname);
		imgcom=(ImageButton) findViewById(R.id.imgcomplaint);
		vclistview=(ListView) findViewById(R.id.vclistView1);
	String response=getIntent().getStringExtra("res");
	
	try {
		
		
		JSONArray arrD=new JSONArray(response);
		
	desc=arrD.getJSONObject(0).getString("postdesc").toString();
	anname=arrD.getJSONObject(0).getString("anoname").toString();	
	System.out.println(desc);
	System.out.println(anname);
	
	//Toast.makeText(getApplicationContext(), "desc", 5000).show();
	//Toast.makeText(getApplicationContext(), "anname", 5000).show();
		
	
	name.setText(anname);
	post.setText(desc);
	
	post.setEnabled(false);
	
	
	comment.setOnClickListener(new OnClickListener() {
		
	
		public void onClick(View v) {
	

			String commentannamwvalue=cmntname.getText().toString();
			System.out.println(commentannamwvalue);
			
			String commentvalue=cmnttext.getText().toString();
			String req1=ConnectionUtil.req_url+"ViewPostServlet?action=comment&cmuname="+URLEncoder.encode(cmname)+"&cmntvalue="+URLEncoder.encode(commentvalue)+"&cmanname="+URLEncoder.encode(anname)+"&postdesc="+URLEncoder.encode(desc)+"";
			System.out.println(req1);
			String res1=ConnectionUtil.send(req1);
				
			System.out.println(res1);
			
			
			Toast.makeText(getApplicationContext(), "Submitted"+res1, 2000).show();
		}
	});
	
	
	
	String r1=ConnectionUtil.req_url+"ViewPostServlet?action=likes&postidvalue="+URLEncoder.encode(post.getText().toString())+"";
	System.out.println(r1);
	
	String resp=ConnectionUtil.send(r1);
	System.out.println(resp);
	liketextView12.setText(resp);
	liketextView12.setTextColor(Color.YELLOW);
	

	
	
	
	
     imgcom.setOnClickListener(new OnClickListener() {
		
		
		public void onClick(View v) {
		
			Intent in=new Intent(ViewPost.this,RaiseIssue.class);
			startActivity(in);			
			Toast.makeText(getApplicationContext(), "Filing issue on post", 6000).show();
			
		}
	});
     
     
     like.setOnClickListener(new OnClickListener() {
		
	
		public void onClick(View v) {
			
			String req2=ConnectionUtil.req_url+"ViewPostServlet?action=like&lname="+URLEncoder.encode(cmname)+"&postdesc="+URLEncoder.encode(desc)+"";
			System.out.println(req2);
			String res2=ConnectionUtil.send(req2);
			System.out.println(res2);
			if(res2.trim().equals("Liked")){
				Toast.makeText(getApplicationContext(), "Liked the post", 3000).show();
			}
		}
	});
     
     
    
     vcmnts.setOnClickListener(new OnClickListener() {
		
		
		public void onClick(View v) {
			
				String request=ConnectionUtil.req_url+"ViewPostServlet?action=vcmnts&postdescrval="+URLEncoder.encode(desc)+"";
				System.out.println("_____@@@@@@"+request);
				String response=ConnectionUtil.send(request);
				System.out.println("____!!!!!!"+response);
				
				try {
					
					JSONArray arr=new JSONArray(response);
					
					commentarray=new String[arr.length()];
					cmntnameslist=new String[arr.length()];
					cmntslist=new String[arr.length()];
					for (int i = 0; i < arr.length(); i++) {
						
						
						
						
						cmntnameslist[i]=arr.getJSONObject(i).getString("cmntnames");
						cmntslist[i]=arr.getJSONObject(i).getString("comments");
						
						
						commentarray[i]=cmntnameslist[i]+"@:"+cmntslist[i];
						
						
					}
					
					
					
					ArrayAdapter<String> a1=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,commentarray);
					vclistview.setAdapter(a1);
					
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			
				
			
		}
	});
     
     
     vclistview.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			System.out.println("!!!!!!!!!!Cmnt clicked");
			String selectedcmnt=cmntslist[position];
			System.out.println(selectedcmnt);
			Intent in=new Intent(ViewPost.this,RiCmnt.class);
			in.putExtra("CMNTClicked", selectedcmnt);
			startActivity(in);
			
			
		}
    	 
    	 
    	 
	});
     
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
		
	
		
	}

}
