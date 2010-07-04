/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.player.LocalPlayer;
import com.kahweh.rps.game.state.StateIdle;

/**
 * @author michael
 *
 */
public class GameActivity extends Activity {
	public static final String TAG = "com.kahweh.rps.GameActivity";
	
	public static final int DIALOG_FLAG_SELECT = 2;
	public static final int DIALOG_TRAP_SELECT = 3;
	private static final int DIALOG_CONFLICT_SELECT1 = 4;
	private static final int DIALOG_CONFLICT_SELECT2 = 5;
	public static final int DIALOG_WIN = 6;
	public static final int DIALOG_LOSE = 7;

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

        //get game preferences
        sharedPreferences = getSharedPreferences(GameSettings.SETTINGS_NAME, 0);
        
        newGame();
    }

    private long last_backey_time = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				//User press the BACK key twice in 3 seconds, then the real BACK action occurs.
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

	/**
	 * Display and edit game settings.
	 * @param from
	 * @param accountId
	 */
	public static void actionGame(Activity from) {
        Intent i = new Intent(from, GameActivity.class);
        from.startActivity(i);
	}
	
    protected void newGame() {
		if (game == null || game.getState() instanceof StateIdle) {
	        //set boardView
			setContentView(R.layout.main);
			boardView = (BoardView)findViewById(R.id.board_view);

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
				Log.w(HomeActivity.class.getName(), e);
			} catch (IllegalPlayerStateException e) {
				game = null;
				Log.w(HomeActivity.class.getName(), e);
			}
			boardView.setBoard(game.getBoard());
			boardView.invalidate();
		} else {
			//TODO: renew game
		}

		return;
	}

    @Override
    protected Dialog onCreateDialog(int id) {
    	switch (id) {
    	case DIALOG_CONFLICT_SELECT1:
    		return new AlertDialog.Builder(GameActivity.this)
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
	    				Log.e(HomeActivity.class.getName(), "LocalPlayer State Error..",e);
					}
				}
			}).create();
    	case DIALOG_CONFLICT_SELECT2:
    		return new AlertDialog.Builder(GameActivity.this)
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
	    				Log.e(HomeActivity.class.getName(), "LocalPlayer State Error..",e);
					}
				}
			}).create();
    	case DIALOG_FLAG_SELECT:
    		return new AlertDialog.Builder(GameActivity.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_select_flag_title)
    		.setMessage(R.string.dialog_select_flag_prompt)
    		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).create();
    	case DIALOG_TRAP_SELECT:
    		return new AlertDialog.Builder(GameActivity.this)
    		.setIcon(R.drawable.dialog_icon_info)
    		.setTitle(R.string.dialog_select_trap_title)
    		.setMessage(R.string.dialog_select_trap_prompt)
    		.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			}).create();
    	case DIALOG_WIN:
    		return new AlertDialog.Builder(GameActivity.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_gameover)
    		.setMessage(R.string.dialog_win_prompt)
    		.setNeutralButton(R.string.dialog_gameover_return, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).setPositiveButton(R.string.dialog_gameover_renew, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					game.renew();
					newGame();
				}
			}).create();
    	case DIALOG_LOSE:
    		return new AlertDialog.Builder(GameActivity.this)
    		.setIcon(R.drawable.app)
    		.setTitle(R.string.dialog_gameover)
    		.setMessage(R.string.dialog_lose_prompt)
    		.setNeutralButton(R.string.dialog_gameover_return, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).setPositiveButton(R.string.dialog_gameover_renew, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					game.renew();
					newGame();
				}
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
		return game;
	}

	public BoardView getBoardView() {
		return boardView;
	}
}
