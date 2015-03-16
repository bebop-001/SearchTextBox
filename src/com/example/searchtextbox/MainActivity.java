package com.example.searchtextbox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.searchtextbox.SearchTextBox.OnSearchReady;

public class MainActivity extends Activity {
	private static final String logTag = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setTheme(android.R.style.Theme_Holo);

		setContentView(R.layout.main);
		SearchTextBox sb1 
			= new SearchTextBox(this, R.id.first_searchbox)
				.setHint("first search")
				.setMinWidth(150)
				.setTag("first search tag")
				.setOnSearchReadyListener(new OnSearchReady() {
					@Override
					public void searchDone(TextView v) {
						Log.i(logTag, "first searchDone:" + v.getText());
					}
				});
		String tag = (String)sb1.getTag();
		Log.i(logTag, "sb1 tag:" + tag);
		SearchTextBox sb12 
			= new SearchTextBox(this, R.id.second_searchbox)
				.setHint("second search")
				.setMinWidth(250)
				.setOnSearchReadyListener(new OnSearchReady() {
					@Override
					public void searchDone(TextView v) {
						Log.i(logTag, "second searchDone:" + v.getText());
					}
				});
		
	}
}
