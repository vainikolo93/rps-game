package com.kahweh.rps;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kahweh.rps.ai.AIPlayerFactory;
import com.kahweh.rps.game.Game;
import com.kahweh.rps.game.IPlayer;
import com.kahweh.rps.game.IllegalGameStateException;
import com.kahweh.rps.game.state.StateIdle;

public class RockPaperScissors extends Activity {
    /** Called when the activity is first created. */
	DisplayMetrics dm;
	private BoardView boardView;
	private Game game;
	private IPlayer player;

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
    			player = new LocalPlayer();
    			game = new Game();
    			try {
    				game.newGame(player, AIPlayerFactory.getAIPlayer());
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
}
