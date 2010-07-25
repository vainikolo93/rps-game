/**
 * 
 */
package com.kahweh.rps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;

/**
 * @author Michael
 *
 */
public class GameSettings extends PreferenceActivity {
	
	public static final String SETTINGS_NAME = "RpsGameSettings";
	
	public static final String PLAYER_NAME = "local_player_name";
	public static final String PLAYER_COLOR = "localplayer_color";
	public static final String BOARD_SIZE = "preference_board_size";
	public static final String GAME_LEVEL = "preference_game_level";
	public static final String GAME_SOUND = "preference_game_sound";
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		//Load preference from XML resource
		addPreferencesFromResource(R.xml.game_settings);
		
		//Tell activity where to read/write the preferences
		PreferenceManager pm = getPreferenceManager();
		pm.setSharedPreferencesName(SETTINGS_NAME);
		pm.setSharedPreferencesMode(0);

		//Get the shared preferences Obj.
		SharedPreferences sp = getSharedPreferences(SETTINGS_NAME, 0);

		EditTextPreference player_name = (EditTextPreference)findPreference(PLAYER_NAME);
		ListPreference player_color = (ListPreference)findPreference(PLAYER_COLOR);
		ListPreference board_size = (ListPreference)findPreference(BOARD_SIZE);
		ListPreference game_level = (ListPreference)findPreference(GAME_LEVEL);
		CheckBoxPreference sound = (CheckBoxPreference)findPreference(GAME_SOUND);

		player_name.setDefaultValue(R.string.preference_player_default_name);
		player_name.setSummary(sp.getString(PLAYER_NAME, getString(R.string.preference_player_default_name)));
		player_name.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary((String)newValue);
				return true;
			}
		});

		final String[] color_des = getResources().getStringArray(R.array.color_select_strings);
		final String[] color_val = getResources().getStringArray(R.array.color_select_values);
		player_color.setSummary(sp.getString(PLAYER_COLOR, color_val[0]).equals(color_val[0])? color_des[0] : color_des[1]);
		player_color.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String newColor = (String)newValue;
				if (newColor.equals(color_val[0])) {
					preference.setSummary(color_des[0]);
				} else {
					preference.setSummary(color_des[1]);
				}
				return true;
			}
		});

		final String[] boardSizes = getResources().getStringArray(R.array.preference_boardsize_des);
		final String[] boardSizes_val = getResources().getStringArray(R.array.preference_boardsize_values);
		board_size.setSummary(sp.getString(BOARD_SIZE, boardSizes_val[0]).equals(boardSizes_val[0])? boardSizes[0] : boardSizes[1]);
		board_size.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String newSize = (String)newValue;
				if (newSize.equals(boardSizes_val[0])) {
					preference.setSummary(boardSizes[0]);
				} else {
					preference.setSummary(boardSizes[1]);
				}
				return true;
			}
		});

		final String[] gameLevelDes = getResources().getStringArray(R.array.preference_game_level_des);
		final String[] gameLevelVal = getResources().getStringArray(R.array.preference_game_level_values);
		String gl = sp.getString(GAME_LEVEL, gameLevelVal[0]);
		game_level.setSummary(gl.equals(gameLevelVal[0])?gameLevelDes[0] 
		                                                :gl.equals(gameLevelVal[1])?gameLevelDes[1]
		                                                                           :gameLevelDes[2]);
		game_level.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String newLevel = (String)newValue;
				if (newLevel.equals(gameLevelVal[0])) {
					preference.setSummary(gameLevelDes[0]);
				} else if (newLevel.equals(gameLevelVal[1])) {
					preference.setSummary(gameLevelDes[1]);
				} else {
					preference.setSummary(gameLevelDes[2]);
				}
				return true;
			}
		});
	}

	/**
	 * Display and edit game settings.
	 * @param from
	 * @param accountId
	 */
	public static void actionSetting(Activity from) {
        Intent i = new Intent(from, GameSettings.class);
        from.startActivity(i);
	}
}