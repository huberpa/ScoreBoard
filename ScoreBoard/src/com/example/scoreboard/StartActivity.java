package com.example.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        final ImageView iv = (ImageView) findViewById(R.id.imageView1);
        
        iv.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {

				 Intent myIntent = new Intent(v.getContext(), LoginActivity.class);
	               startActivityForResult(myIntent, 0);
				
			}});   
    }
}
