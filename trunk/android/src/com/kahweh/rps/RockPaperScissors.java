package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
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
import android.widget.Toast;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.player.IPlayer;
import com.kahweh.rps.game.player.IllegalPlayerStateException;
import com.kahweh.rps.game.player.LocalPlayer;
import com.kahweh.rps.game.state.StateIdle;

public class RockPaperScissors extends Activity {
	public static final int DIALOG_COLOR_SELECT = 1;
	public static final int DIALOG_FLAG_SELECT = 2;
	public static final int DIALOG_TRAP_SELECT = 3;
	public static final int DIALOG_CONFLICT_SELECT = 4;
	public static final int DIALOG_ABOUT = 1000;

	private static final int MENU_NEWGAME_ID = 0;
	private static final int MENU_ABOUT_ID = 1;

	DisplayMetrics dm;
	private BoardView boardView;
	private Game game;
	private LocalPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Full Screen show
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	Toast.makeText(RockPaperScissors.this, "TEST in land", Toast.LENGTH_SHORT).show();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        	Toast.makeText(RockPaperScissors.this, "TEST in port", Toast.LENGTH_SHORT).show();
        }
        
        //create boardView
        if (boardView == null) {
        	boardView = new BoardView(this);
        }
        ((LinearLayout)findViewById(R.id.root)).addView(boardView);

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
    			game = new Game();
    			player = new LocalPlayer(this);
    			boardView.setPlayer(player);
    			showDialog(DIALOG_COLOR_SELECT);
//    			Toast.makeText(RockPaperScissors.this, "Test", Toast.LENGTH_LONG).show();
    		} else {
    			//TODO: renew game
    		}
    		return true;
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
    	case DIALOG_CONFLICT_SELECT:
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
    	case DIALOG_COLOR_SELECT:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_question)
    		.setTitle(R.string.dialog_select_color_title)
    		.setSingleChoiceItems(R.array.color_select_strings, 0, new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int btn) {
    				if (btn == 0) {
    					try {
							player.setColor(IPlayer.RED);
						} catch (IllegalPlayerStateException e) {
							Log.w(RockPaperScissors.class.getSimpleName(), "Red player state error..", e);
						}
    				} else if (btn == 1) {
    					try {
							player.setColor(IPlayer.BLACK);
						} catch (IllegalPlayerStateException e) {
							Log.w(RockPaperScissors.class.getSimpleName(), "Black player state error..", e);
						}
    				}
    			}
    		}).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
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
				}
			}).create();
    	case DIALOG_FLAG_SELECT:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_info)
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
    	case DIALOG_COLOR_SELECT:
    		break;
    	case DIALOG_FLAG_SELECT:
    		break;
    	case DIALOG_TRAP_SELECT:
    		break;
    	case DIALOG_ABOUT:
    		break;
    	}
    }
}
