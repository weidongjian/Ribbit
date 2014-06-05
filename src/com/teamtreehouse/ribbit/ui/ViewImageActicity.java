package com.teamtreehouse.ribbit.ui;

import java.util.Timer;
import java.util.TimerTask;

import com.squareup.picasso.Picasso;
import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.R.id;
import com.teamtreehouse.ribbit.R.layout;
import com.teamtreehouse.ribbit.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

public class ViewImageActicity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_image_acticity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		

		ImageView imageView = (ImageView) findViewById(R.id.imageView);
		Uri imageUri = getIntent().getData();
		Picasso.with(this).load("http://www.leninimports.com/picasso_girl_mirror_postcard_1.jpg").into(imageView);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				finish();
			}
		}, 10*1000);
		
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getActionBar().isShowing()) {
					getActionBar().hide();
				}
				else
					getActionBar().show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_image_acticity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		else if (id == android.R.id.home)
			NavUtils.navigateUpFromSameTask(this);
		return super.onOptionsItemSelected(item);
	}

	

}
