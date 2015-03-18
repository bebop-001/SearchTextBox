package com.example.searchtextbox;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.searchtextbox.SearchTextBox.OnSearchReady;

public class MainActivity extends Activity implements OnNavigationListener {
	private static final String logTag = "MainActivity";
    private static ArAd abAdapt;
    private static SharedPreferences prefs;

	private class ArAd extends ArrayAdapter<ThemeInfo> {
		private boolean viewed = false;
	    private ArAd(Activity a
	            , int layoutId, int resId, ArrayList<ThemeInfo> themeinfo) {
	        super(a, layoutId, resId, themeinfo);
	    }
	    @Override
	    public View getDropDownView(int position, View convertView,
	            ViewGroup parent) {
	        viewed = true;
	        return super.getDropDownView(position, convertView, parent);
	    }
	    private boolean viewed() {return viewed; }
	}
	private static class ThemeInfo {
		private String name; private int resId;
		private ThemeInfo (String name, int resId) {
			this.name = name; this.resId = resId;
		}
		public String toString() {return name; }
	}
	private static final ArrayList<ThemeInfo> themeInfo;
	static {
		themeInfo = new ArrayList<ThemeInfo>();
		themeInfo.add(new ThemeInfo("Holo Dark", android.R.style.Theme_Holo));
		themeInfo.add(new ThemeInfo("Holo Light", android.R.style.Theme_Holo_Light));
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        /****************** Theme setup ********************************/
    	// Restore user's previous selected theme.
    	prefs = getSharedPreferences("user_prefs.txt", Context.MODE_PRIVATE);
    	int selectedTheme = prefs.getInt("selectedTheme", 0);

        setTheme(themeInfo.get(selectedTheme).resId);

        ActionBar ab = getActionBar();
        ab.setDisplayShowTitleEnabled(false);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setListNavigationCallbacks(null, null);

        abAdapt = new ArAd (this
            , android.R.layout.simple_list_item_1, android.R.id.text1, themeInfo);
        ab.setListNavigationCallbacks(abAdapt, MainActivity.this);
        ab.setSelectedNavigationItem(selectedTheme);

        /********************** search box stuff ***********************/
		setContentView(R.layout.main);
		SearchTextBox sb1 
			= new SearchTextBox(this, R.id.first_searchbox)
				.setHint("first search")
				.setMinWidth(150)
				.setTag((View)findViewById(R.id.first_edittext))
				.setOnSearchReadyListener(new OnSearchReady() {
					@Override
					public void searchDone(TextView v) {
						EditText et = (EditText)v.getTag();
						et.setText((String)v.getText().toString());
						Log.i(logTag, "first searchDone:" + v.getText());
					}
				});
		SearchTextBox sb2 
			= new SearchTextBox(this, R.id.second_searchbox)
				.setHint("second search")
				.setMinWidth(250)
				.setTag((View)findViewById(R.id.second_edittext))
				.setOnSearchReadyListener(new OnSearchReady() {
					@Override
					public void searchDone(TextView v) {
						EditText et = (EditText)v.getTag();
						et.setText((String)v.getText().toString());
						Log.i(logTag, "second searchDone:" + v.getText());
					}
				});
	}
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        if (abAdapt.viewed()) {
        	// save the user's selection.
        	Log.i(logTag, "onNavigationItemSelected:" + itemPosition);
        	Editor e = prefs.edit();
            e.putInt("selectedTheme", itemPosition);
            e.commit();
            finish();
            startActivity(new Intent(this, this.getClass()));
        }
		return false;
	}
}
