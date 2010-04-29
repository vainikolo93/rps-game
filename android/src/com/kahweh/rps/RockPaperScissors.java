package com.kahweh.rps;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.state.StateIdle;

public class RockPaperScissors extends Activity {
	private static final int DIALOG_COLOR_SELECT = 1;
	private static final int DIALOG_FLAG_SELECT = 2;
	private static final int DIALOG_TRAP_SELECT = 3;
	
	DisplayMetrics dm;
	private BoardView boardView;
	private Game game;
	private IPlayer player;
	
	private int selectedColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Full Screen show
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        dm = this.getApplicationContext().getResources().getDisplayMetrics();

        //create boardView
        boardView = new BoardView(this);
        ((LinearLayout)findViewById(R.id.root)).addView(boardView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean supRetVal = super.onCreateOptionsMenu(menu);
    	
    	menu.add(Menu.NONE, 0, Menu.NONE, getString(R.string.menu_newGame));
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
    	case 0:
    		//New Game
    		if (game == null || game.getState() instanceof StateIdle) {
    			game = new Game();
    			player = new LocalPlayer(this);
    			showDialog(DIALOG_COLOR_SELECT);
    			try {
    				if (selectedColor == 0) {
    					//User selected red
    					game.newGame(player, AIPlayerFactory.getAIPlayer());
    				} else if (selectedColor == 1) {
    					//User selected black/blue
    					game.newGame(AIPlayerFactory.getAIPlayer(), player);
    				}
    			} catch (IllegalGameStateException e) {
    				game = null;
    				Log.w(RockPaperScissors.class.getName(), e);
    			}
    		} else {
    			//TODO: renew game
    		}
    		return true;
    	case 1:
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
    	case DIALOG_COLOR_SELECT:
    		return new AlertDialog.Builder(RockPaperScissors.this)
    		.setIcon(R.drawable.dialog_icon_question)
    		.setTitle(R.string.dialog_select_color_title)
    		.setSingleChoiceItems(R.array.color_select_strings, 0, new DialogInterface.OnClickListener() {
    			@Override
    			public void onClick(DialogInterface dialog, int btn) {
    				if (btn == 0) {
    					selectedColor = 0;
    				} else if (btn == 1) {
    					selectedColor = 1;
    				}
    			}
    		}).setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(RockPaperScissors.this, "Test " + selectedColor, Toast.LENGTH_SHORT);
				}
			}).create();
    	case DIALOG_FLAG_SELECT:
    		break;
    	case DIALOG_TRAP_SELECT:
    		break;
    	}
    	return null;
    }
}
