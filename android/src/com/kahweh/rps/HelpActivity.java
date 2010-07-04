/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
				about(HelpActivity.this);
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

    /**
     * Shows the "about" dialog.
     * 
     * TODO: Add a menu option for showing the same thing.
     */
    public static void about(Context ctx) {
      LayoutInflater li = LayoutInflater.from(ctx);
      View view = li.inflate(R.layout.about, null);
      AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
      builder.setView(view);
      builder.setPositiveButton(R.string.button_ok, null);
      builder.setIcon(R.drawable.app);
      AlertDialog dialog = builder.create();
      dialog.show();
    }
}
