package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.player.LocalPlayer;
import com.kahweh.rps.game.state.StateIdle;

public class HomeActivity extends Activity {
	public static final int DIALOG_ABOUT = 1000;

	private static final int MENU_NEWGAME_ID = 0;
	private static final int MENU_ABOUT_ID = 1;
	private static final int MENU_GAME_SETTING_ID = 2;
	
	DisplayMetrics dm;

    private Button btn_start;
    private Button btn_preference;
    private Button btn_help;
    private Button btn_about;
	
	private BoardView boardView;
	private Game game;
	private LocalPlayer player;
	private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full Screen show
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //init the Main Menu view
        initHome();

        //get game preferences
        sharedPreferences = getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
    }

    /**
     * This function is used to initialize the Home view(main menu) of RPS game.
     */
    private void initHome() {
        setContentView(R.layout.home);

        if (btn_start == null) {
        	btn_start = (Button)findViewById(R.id.btn_start);
            btn_start.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				GameActivity.actionGame(HomeActivity.this);
    			}
            });
        }

        if (btn_preference == null) {
        	btn_preference = (Button)findViewById(R.id.btn_preference);
            btn_preference.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				GameSettings.actionSetting(HomeActivity.this);
    			}
            });
        }

        if (btn_help == null) {
        	btn_help = (Button)findViewById(R.id.btn_help);
            btn_help.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				HelpActivity.actionHelp(HomeActivity.this);
    			}
            });
        }

        if (btn_about == null) {
        	btn_about = (Button)findViewById(R.id.btn_about);
            btn_about.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    	    		showDialog(DIALOG_ABOUT);
    			}
            });
        }
    }

	@Override
    public void onStart() {
    	super.onStart();
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
    
    @Override
    public void onRestart() {
    	super.onRestart();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean supRetVal = super.onCreateOptionsMenu(menu);
    	menu.add(Menu.NONE, MENU_NEWGAME_ID, Menu.NONE, getString(R.string.menu_newGame));
    	menu.add(Menu.NONE, MENU_ABOUT_ID, Menu.NONE, getString(R.string.menu_about));
    	menu.add(Menu.NONE, MENU_GAME_SETTING_ID, Menu.NONE, getString(R.string.menu_setting));
    	return supRetVal;
    }
    
    /**
     * This function used to dynamically change the option menu.
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	
    	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case MENU_NEWGAME_ID:
    		//New Game
    		return true;
    	case MENU_GAME_SETTING_ID:
    		GameSettings.actionSetting(this);
    		break;
    	case MENU_ABOUT_ID:
    		//About
    		showDialog(DIALOG_ABOUT);
    		break;
    	}
    	return false;
    }

    public BoardView getBoardView() {
		return boardView;
	}

    @Override
    protected Dialog onCreateDialog(int id) {
    	switch (id) {
    	case DIALOG_ABOUT:
    		TextView view = new TextView(HomeActivity.this);
    		view.setText(Html.fromHtml(getString(R.string.dialog_about_content)));
    		view.setMovementMethod(LinkMovementMethod.getInstance());

    		return new AlertDialog.Builder(HomeActivity.this)
    		.setIcon(R.drawable.dialog_icon_info)
    		.setTitle(R.string.dialog_about_title)
    		.setView(view)
    		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).create();
    	}
    	return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dlg) {
    	switch (id) {
    	case DIALOG_ABOUT:
    		break;
    	}
    }

	public Game getGame() {
		return this.game;
	}
}
