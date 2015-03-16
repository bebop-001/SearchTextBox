package com.example.searchtextbox;

import android.app.Activity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchTextBox extends Activity {
	private static String logTag = "SearchTextBox";
    public OnSearchReady searchReadyListener;
    public interface OnSearchReady {
        public void searchDone (TextView v);
    }
    public SearchTextBox setOnSearchReadyListener(OnSearchReady l) {
    	searchReadyListener = l;
    	return this;
    }
    private final class NotifyListener implements Runnable {
    	private TextView view = null;
    	public NotifyListener(TextView v) {
    		this.view = v;
		}
    	public void run () {
    		if (null != searchReadyListener) {
    			searchReadyListener
    				.searchDone(view);
    		}
    	}
    }
	private EditText search;
	private View parentView;
	public SearchTextBox (Activity activity, int resourceId) {
		parentView = activity.findViewById(resourceId);
		search = (EditText)parentView.findViewById(R.id.search_text);
		search.setOnEditorActionListener(editorAction());
		Button b = (Button)parentView.findViewById(R.id.clear_search);
		b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	search.setText("");
            }
		});
	}
	public SearchTextBox setHint(String hint) {
		search.setHint(hint);
		return this;
	}
	public SearchTextBox setTag(Object o) {
		search.setTag(o);
		return this;
	}
	public Object getTag() {
		return search.getTag();
	}
	public SearchTextBox setMinWidth(int minWidth) {
		double dp = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP
				, minWidth
				, parentView.getContext().getResources()
					.getDisplayMetrics());
		search.setMinWidth((int)(dp + 0.5));
		return this;
	}
	public String getText() {
		return search.getText().toString();
	}
	private EditText.OnEditorActionListener editorAction () {
		return new EditText.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean rv = false;
				Log.i(logTag, "onEditorAction...");

				if (null != event && (
						event.getAction() == KeyEvent.ACTION_DOWN 
						&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					Log.i(logTag, "onEditorAction... key enter");
                    rv = true;
				}
				else if (actionId == EditorInfo.IME_ACTION_SEARCH
						|| actionId == EditorInfo.IME_ACTION_DONE
			            || actionId == EditorInfo.IME_ACTION_NEXT) {
			    	rv = true;
			    }
				if (rv) {
					new NotifyListener(v).run();
				}
			    return rv;
			}
		};
	}
}
