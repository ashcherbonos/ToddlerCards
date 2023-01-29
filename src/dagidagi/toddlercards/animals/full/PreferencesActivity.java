package dagidagi.toddlercards.animals.full;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import dagidagi.studio.toddlercards.animals.full.R;

public class PreferencesActivity extends Activity {

	public static final String PREF_BLOCK_SCROLLING = "blockScrolling";
	public static final boolean DEFAULT_BLOCK_SCROLLING = true;
	public static final String PREF_BLOCK_SCREEN = "blockScreen";
	public static final boolean DEFAULT_BLOCK_SCREEN = true;
	public static final String PREF_DOUBLE_CLICK_TO_EXIT = "doubleClickToExit";
	public static final boolean DEFAULT_DOUBLE_CLICK_TO_EXIT = true;
	public static final String PREF_SHUFFLE_CARDS = "shuffleCards";
	public static final boolean DEFAULT_SHUFFLE_CARDS = false;		
	
	private static final int LANGUAGE_DIALOG = 1;

	private Editor editor;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preferences);
		MyUtilits.setToddlersMode(this);

		// load preferences
		sharedPreferences = getSharedPreferences(CardSetConfig.APP_PREFERENCES, MODE_PRIVATE);
		editor = sharedPreferences.edit();
		// switch CheckBoxes
		((CompoundButton) findViewById(R.id.delayScrolling)).setChecked(sharedPreferences.getBoolean(PreferencesActivity.PREF_BLOCK_SCROLLING, PreferencesActivity.DEFAULT_BLOCK_SCROLLING));
		((CompoundButton) findViewById(R.id.blockScreen)).setChecked(sharedPreferences.getBoolean(PreferencesActivity.PREF_BLOCK_SCREEN, PreferencesActivity.DEFAULT_BLOCK_SCREEN));
		((CompoundButton) findViewById(R.id.doubleClickToExit)).setChecked(sharedPreferences.getBoolean(PreferencesActivity.PREF_DOUBLE_CLICK_TO_EXIT, PreferencesActivity.DEFAULT_DOUBLE_CLICK_TO_EXIT));
		((CompoundButton) findViewById(R.id.shuffleCards)).setChecked(sharedPreferences.getBoolean(PreferencesActivity.PREF_SHUFFLE_CARDS, PreferencesActivity.DEFAULT_SHUFFLE_CARDS));
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.delayScrolling:
			editor.putBoolean(PREF_BLOCK_SCROLLING, ((CheckBox) view).isChecked());
			break;
		case R.id.blockScreen:
			editor.putBoolean(PREF_BLOCK_SCREEN, ((CheckBox) view).isChecked());
			break;
		case R.id.doubleClickToExit:
			editor.putBoolean(PREF_DOUBLE_CLICK_TO_EXIT, ((CheckBox) view).isChecked());
			break;
		case R.id.shuffleCards:
			editor.putBoolean(PREF_SHUFFLE_CARDS, ((CheckBox) view).isChecked());
			break;			
		case R.id.btnlanguage:
			showDialog(1);
			break;
		case R.id.btnBack:
			finish();
		}
		editor.commit();
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		    case LANGUAGE_DIALOG: 
				return ChangeLanguage.onCreateDialog(this);
		}
		return null;
	}
}
