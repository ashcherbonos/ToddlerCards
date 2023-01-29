package dagidagi.toddlercards.animals.full;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

public class MyUtilits {

	private static long back_pressed;
	private static AudioManager audio;

	static void setToddlersMode(Activity activity) {
		// do not allow to lock screen
		if (activity.getSharedPreferences(CardSetConfig.APP_PREFERENCES, activity.MODE_PRIVATE).getBoolean(PreferencesActivity.PREF_BLOCK_SCREEN, PreferencesActivity.DEFAULT_BLOCK_SCREEN)) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	static boolean tryToQuit(Activity activity, final TextView statusText) {
		if (!activity.getSharedPreferences(CardSetConfig.APP_PREFERENCES, activity.MODE_PRIVATE).getBoolean(PreferencesActivity.PREF_DOUBLE_CLICK_TO_EXIT,
				PreferencesActivity.DEFAULT_DOUBLE_CLICK_TO_EXIT))
			return true;

		if (back_pressed + 1000 > System.currentTimeMillis()) {
			return true;
		} else {
			statusText.setText("Make double click!");
			statusText.postDelayed(new Runnable() {
				@Override
				public void run() {
					statusText.setText("");
				}
			}, 1000);
		}
		back_pressed = System.currentTimeMillis();
		return false;

	}
	
	static boolean tryToQuit(Activity activity) {
		if (!activity.getSharedPreferences(CardSetConfig.APP_PREFERENCES, activity.MODE_PRIVATE).getBoolean(PreferencesActivity.PREF_DOUBLE_CLICK_TO_EXIT,
				PreferencesActivity.DEFAULT_DOUBLE_CLICK_TO_EXIT))
			return true;

		if (back_pressed + 1000 > System.currentTimeMillis()) {
			return true;
		} 
		back_pressed = System.currentTimeMillis();
		return false;

	}
	
	static boolean onKeyDown(int keyCode, KeyEvent event, Activity activity) {
		// adjust volume:
		audio = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_BACK:
			if (tryToQuit(activity))
				activity.finish();
			return true;
		default:
			return false;
		}
	}
	
	static boolean onKeyDown(int keyCode, KeyEvent event, Activity activity, TextView statusText) {
		// adjust volume:
		audio = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_BACK:
			if (tryToQuit(activity, statusText))
				activity.finish();
			return true;
		default:
			return false;
		}
	}
}
