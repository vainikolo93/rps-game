/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

/**
 * @author michael
 *
 */
public class HelpActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.help);
        WebView help_web = (WebView)findViewById(R.id.help_web);
        
        help_web.loadUrl("file:///android_asset/help.html");
        
        findViewById(R.id.btn_help_about).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				about();
			}

			private void about() {
			}
        });
        
        findViewById(R.id.btn_help_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
        });
    }

    public static void actionHelp(Activity from) {
    	Intent intent = new Intent(from, HelpActivity.class);
    	from.startActivity(intent);
    }
}
