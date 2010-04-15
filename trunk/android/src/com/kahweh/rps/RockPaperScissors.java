package com.kahweh.rps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class RockPaperScissors extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView tv = (TextView)findViewById(R.id.text1);
        DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics();
        tv.setText(String.valueOf(dm.widthPixels) + "X" + String.valueOf(dm.heightPixels));
    }
}
