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
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Board55;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IBoard;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.player.LocalPlayer;
import com.kahweh.rps.game.state.StateIdle;

public class RockPaperScissors extends Activity {
	public static final int DIALOG_FLAG_SELECT = 2;
	public static final int DIALOG_TRAP_SELECT = 3;
	private static final int DIALOG_CONFLICT_SELECT1 = 4;
	private static final int DIALOG_CONFLICT_SELECT2 = 5;
	public static final int DIALOG_WIN = 6;
	public static final int DIALOG_LOSE = 7;
	public static final int DIALOG_ABOUT = 1000;

	private static final int MENU_NEWGAME_ID = 0;
	private static final int MENU_ABOUT_ID = 1;
	private static final int MENU_GAME_SETTING_ID = 2;
	
	DisplayMetrics dm;
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

        setContentView(R.layout.main);

        //create boardView
        if (boardView == null) {
        	boardView = new BoardView(this);
        }
        ((LinearLayout)findViewById(R.id.root)).addView(boardView);

        //get game preferences
        sharedPreferences = getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
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
    		if (game == null || game.getState() instanceof StateIdle) {
    			game = new Game(sharedPreferences.getString(GameSettings.BOARD_SIZE, "25"));
    			boardView.setBoardType(game.getBoardType());
    			player = new LocalPlayer(this);
    			if ("red".equals(sharedPreferences.getString(GameSettings.PLAYER_COLOR, "red"))) {
    				player.setColor(IPlayer.RED);
    			} else {
    				player.setColor(IPlayer.BLACK);
    			}
    			boardView.setPlayer(player);

    			try {
					player.colorSet();
    				if (player.getColor() == IPlayer.RED) {
    					//User selected red
    					game.newGame(player, AIPlayerFactory.getAIPlayer());
    				} else if (player.getColor() == IPlayer.BLACK) {
    					//User selected black/blue
    					game.newGame(AIPlayerFactory.getAIPlayer(), player);
    				}
    			} catch (IllegalGameStateException e) {
    				game = null;
    				Log.w(RockPaperScissors.class.getName(), e);
    			} catch (IllegalPlayerStateException e) {
    				game = null;
    				Log.w(RockPaperScissors.class.getName(), e);
    			}
    			boardView.setBoard(game.getBoard());
    			boardView.invalidate();

    		} else {
    			//TODO: renew game
    		}
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
    	case DIALOG_CONFLICT_SELECT1:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_question)
    		.setTitle(R.string.dialog_conflict_select_title)
    		.setSingleChoiceItems(R.array.gesture_select_strings, 0, new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int btn) {
    				if (btn == 0) {
    					//Rock selected
    					player.setDrawChoice(0);
    				} else if (btn == 1) {
    					//Paper
    					player.setDrawChoice(1);
    				} else if (btn == 2) {
    					//Scissors
    					player.setDrawChoice(2);
    				}
    			}
    		}).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						player.drawChoiceMade();
					} catch (IllegalGameStateException e) {
	    				game = null;
	    				Log.e(RockPaperScissors.class.getName(), "LocalPlayer State Error..",e);
					}
				}
			}).create();
    	case DIALOG_CONFLICT_SELECT2:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_question)
    		.setTitle(R.string.dialog_conflict_select_title)
    		.setSingleChoiceItems(R.array.gesture_select_strings, 0, new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int btn) {
    				if (btn == 0) {
    					//Rock selected
    					player.setDrawChoice(0);
    				} else if (btn == 1) {
    					//Paper
    					player.setDrawChoice(1);
    				} else if (btn == 2) {
    					//Scissors
    					player.setDrawChoice(2);
    				}
    			}
    		}).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						player.drawChoiceMade();
					} catch (IllegalGameStateException e) {
	    				game = null;
	    				Log.e(RockPaperScissors.class.getName(), "LocalPlayer State Error..",e);
					}
				}
			}).create();
    	case DIALOG_FLAG_SELECT:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_select_flag_title)
    		.setMessage(R.string.dialog_select_flag_prompt)
    		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).create();
    	case DIALOG_TRAP_SELECT:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_info)
    		.setTitle(R.string.dialog_select_trap_title)
    		.setMessage(R.string.dialog_select_trap_prompt)
    		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).create();
    	case DIALOG_WIN:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_gameover)
    		.setMessage(R.string.dialog_win_prompt)
    		.setNeutralButton(R.string.dialog_gameover_return, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			}).setPositiveButton(R.string.dialog_gameover_renew, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			}).create();
    	case DIALOG_LOSE:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_gameover)
    		.setMessage(R.string.dialog_lose_prompt)
    		.setNeutralButton(R.string.dialog_gameover_return, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			}).setPositiveButton(R.string.dialog_gameover_renew, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO
				}
			}).create();
    	case DIALOG_ABOUT:
    		TextView view = new TextView(RockPaperScissors.this);
    		view.setText(Html.fromHtml(getString(R.string.dialog_about_content)));
    		view.setMovementMethod(LinkMovementMethod.getInstance());

    		return new AlertDialog.Builder(RockPaperScissors.this)
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
    	case DIALOG_FLAG_SELECT:
    		break;
    	case DIALOG_TRAP_SELECT:
    		break;
    	case DIALOG_ABOUT:
    		break;
    	}
    }

    private boolean nextConfDialog = true;
    /**
     * When met continuous conflict, the one thread mode will cause the second dialog cannot show off.
     * Because the invoke of second dialog is in the onClick function of first one.
     * This function solved this problem with two polling dialogs, these two dialogs have same definition. 
     */
    public void showConfSelectDialog() {
    	if (nextConfDialog) {
        	showDialog(DIALOG_CONFLICT_SELECT1);
    	} else {
        	showDialog(DIALOG_CONFLICT_SELECT2);
    	}
    	nextConfDialog = !nextConfDialog;
    }

	public void showFinishDialog(boolean b) {
		if (b) {
			showDialog(DIALOG_WIN);
		} else {
			showDialog(DIALOG_LOSE);
		}
	}
	
	public Game getGame() {
		return this.game;
	}
}
