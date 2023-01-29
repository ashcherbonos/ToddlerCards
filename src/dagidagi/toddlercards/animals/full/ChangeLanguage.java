package dagidagi.toddlercards.animals.full;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;

public class ChangeLanguage {

	static final String EN = "en";
	static final String RU = "ru";
	private static Locale myLocale;
	private static Activity activity;

	public static void changeLang(String lang, Activity activity) {
		if (lang.equalsIgnoreCase(""))
			return;
		myLocale = new Locale(lang);
		saveLocale(lang, activity);
		Locale.setDefault(myLocale);
		android.content.res.Configuration config = new android.content.res.Configuration();
		config.locale = myLocale;
		activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
	}

	private static void reloadActivity(Activity activity) {
		Intent intent = activity.getIntent();
		activity.finish();
		activity.startActivity(intent);
	}

	public static void saveLocale(String lang, Activity activity) {
		String langPref = "Language";
		SharedPreferences prefs = activity.getSharedPreferences("LanguagePrefs", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
	}

	public static void loadLocale(Activity activity) {
		String langPref = "Language";
		SharedPreferences prefs = activity.getSharedPreferences("LanguagePrefs", Activity.MODE_PRIVATE);
		String language = prefs.getString(langPref, defaultLocale());
		changeLang(language, activity);
	}

	public static int localeID(Activity activity) {
		String langPref = "Language";
		SharedPreferences prefs = activity.getSharedPreferences("LanguagePrefs", Activity.MODE_PRIVATE);
		String lang = prefs.getString(langPref, defaultLocale());
		if (lang.equals("en"))
			return 0;
		if (lang.equals("ru"))
			return 1;
		return 0;
	}

	public static Dialog onCreateDialog(Activity activity) {
		String data[] = { "English" };
		ChangeLanguage.activity = activity;
		AlertDialog.Builder adb = new AlertDialog.Builder(activity);
		adb.setSingleChoiceItems(data, localeID(activity), myClickListener);
		adb.setPositiveButton("OK", myClickListener);
		return adb.create();
	}

	private static String defaultLocale(){	
		return Locale.getDefault().getLanguage();
	}
	
	static OnClickListener myClickListener = new OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			ListView lv = ((AlertDialog) dialog).getListView();
			if (which == Dialog.BUTTON_POSITIVE) {
				switch (lv.getCheckedItemPosition()) {
				case 0:
					ChangeLanguage.changeLang(ChangeLanguage.EN, activity);
					reloadActivity(activity);
					break;
				case 1:
					ChangeLanguage.changeLang(ChangeLanguage.RU, activity);
					reloadActivity(activity);
					break;
				}
			}
		}
	};
}
