package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.player.LocalPlayer;

public class HomeActivity extends Activity {
	private static String TAG = "com.kahweh.rps.HomeActivity";

	public static final int DIALOG_ABOUT = 1000;
	public static final int DIALOG_SELECT_NET = 1001;

	private static final int MENU_NEWGAME_ID = 0;
	private static final int MENU_ABOUT_ID = 1;
	private static final int MENU_GAME_SETTING_ID = 2;
	
	DisplayMetrics dm;

    private Button btn_start;
    private Button btn_bt;
    private Button btn_preference;
    private Button btn_help;
	
	private BoardView boardView;
	private Game game;
	private LocalPlayer player;
	private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Config.DEBUG) {
        	Log.d(TAG, "In HomeAction, starting Game of RPS...");
        }
        
        //Full Screen show
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //init the Main Menu view
        initHome();
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
//    				GameActivity.actionGame(HomeActivity.this);
    				Intent intent = new Intent(HomeActivity.this, GameActivity.class);
    				intent.putExtra(GameActivity.GAME_TYPE, GameActivity.SINGLE_GAME);
    				HomeActivity.this.startActivity(intent);
    			}
            });
        }

        if (btn_bt == null) {
        	btn_bt = (Button)findViewById(R.id.btn_remote);
            btn_bt.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				HomeActivity.this.showDialog(DIALOG_SELECT_NET);
//    				BtActivity.actionGame(HomeActivity.this);
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

    }

	@Override
    public void onStart() {
    	super.onStart();

    	//get game preferences
        sharedPreferences = getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
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

    private long last_backey_time = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				long current = System.currentTimeMillis();
				if (current - last_backey_time > 3000) {
					last_backey_time = current;
					Toast.makeText(this, R.string.toast_press_again, Toast.LENGTH_SHORT).show();
					return true;
				}
				break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
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
    		HelpActivity.about(this);
    		break;
    	}
    	return false;
    }

    public BoardView getBoardView() {
		return boardView;
	}

    @Override
    protected Dialog onCreateDialog(int id) {
    	Dialog dlg = null;
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	switch (id) {
    		case DIALOG_SELECT_NET:
    			dlg = builder.setTitle(R.string.dialog_connection_method_title)
    			.setSingleChoiceItems(R.array.dialog_connection_method, 0, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
							case 0:
								//Use the Internet for the connection
								NetActivity.actionGame(HomeActivity.this);
								dialog.dismiss();
								break;
							case 1:
								//Use Bluetooth for the connection
								BtActivity.actionBtActivity(HomeActivity.this);
								dialog.dismiss();
								break;
							default:
								Toast.makeText(HomeActivity.this, "Go to HELL!", Toast.LENGTH_LONG).show();
								break;
						}
					}
				})
    			.setNegativeButton(R.string.dialog_connection_method_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).create();
    			break;
    		default:
    			break;
    	}
    	return dlg;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dlg) {
    	switch (id) {
    	case DIALOG_SELECT_NET:
    		break;
    	}
    }

	public Game getGame() {
		return this.game;
	}
}
