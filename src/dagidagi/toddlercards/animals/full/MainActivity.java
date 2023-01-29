package dagidagi.toddlercards.animals.full;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import dagidagi.studio.toddlercards.animals.full.R;

public class MainActivity extends Activity implements OnTouchListener {

	private TextView mainWindowStatusText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ChangeLanguage.loadLocale(this);
		setContentView(R.layout.activity_main);
		MyUtilits.setToddlersMode(this);

		mainWindowStatusText = (TextView) findViewById(R.id.mainWindowStatusText);

		// set OnTouch Listeners
		findViewById(R.id.imageViewSetFarm).setOnTouchListener(this);
		findViewById(R.id.imageViewSetForest).setOnTouchListener(this);
		findViewById(R.id.imageViewSetPets).setOnTouchListener(this);
		findViewById(R.id.imageViewSetAfrica).setOnTouchListener(this);
		findViewById(R.id.imageViewSetAsia).setOnTouchListener(this);
		findViewById(R.id.imageViewSetAustralia).setOnTouchListener(this);
		findViewById(R.id.imageViewSetPolar).setOnTouchListener(this);
		
		findViewById(R.id.imageViewSetNext).setOnTouchListener(this);
		findViewById(R.id.iconAbout).setOnTouchListener(this);
		findViewById(R.id.iconRun).setOnTouchListener(this);
		findViewById(R.id.iconSettings).setOnTouchListener(this);
	}

	public void onClickCardSet(View v) {
		Intent intent;
		// Calling CardsView activity
		switch (v.getId()) {
		case R.id.imageViewSetPets:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 0);
			startActivity(intent);
			break;
		case R.id.imageViewSetFarm:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 1);
			startActivity(intent);
			break;
		case R.id.imageViewSetForest:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 2);
			startActivity(intent);
			break;
		case R.id.imageViewSetAfrica:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 3);
			startActivity(intent);
			break;
			
		case R.id.imageViewSetAsia:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 4);
			startActivity(intent);
			break;
			
		case R.id.imageViewSetAustralia:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 5);
			startActivity(intent);
			break;
			
		case R.id.imageViewSetPolar:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("CardSetIndex", 6);
			startActivity(intent);
			break;
			
		case R.id.iconRun:
			intent = new Intent(this, CardsViewActivity.class);
			intent.putExtra("Mode", CardsViewAdapter.SLIDSHOW_MODE);
			startActivity(intent);
			break;
		case R.id.iconSettings:
			if (MyUtilits.tryToQuit(this, mainWindowStatusText)) {
				startActivity(new Intent(this, PreferencesActivity.class));
			}
			break;
		case R.id.iconAbout:
			if (MyUtilits.tryToQuit(this, mainWindowStatusText)) {
				startActivity(new Intent(this, AboutActivity.class));
			}
			break;
		}
	}

	boolean holdOn = false;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			holdOn = true;
			switch (v.getId()) {
			case R.id.imageViewSetFarm:
				mainWindowStatusText.setText(R.string.farm);
				break;
			case R.id.imageViewSetForest:
				mainWindowStatusText.setText(R.string.forest);
				break;
			case R.id.imageViewSetPets:
				mainWindowStatusText.setText(R.string.pets);
				break;
			case R.id.imageViewSetAfrica:
				mainWindowStatusText.setText(R.string.africa);
				break;
			case R.id.imageViewSetAsia:
				mainWindowStatusText.setText(R.string.asia);
				break;
			case R.id.imageViewSetAustralia:
				mainWindowStatusText.setText(R.string.australia);
				break;
			case R.id.imageViewSetPolar:
				mainWindowStatusText.setText(R.string.polar);
				break;
			case R.id.imageViewSetNext:
				mainWindowStatusText.setText(R.string.set_next);
				break;
			case R.id.iconRun:
				mainWindowStatusText.setText(R.string.icon_run);
				break;
			case R.id.iconSettings:
				mainWindowStatusText.setText(R.string.icon_settings);
			case R.id.iconAbout:
				mainWindowStatusText.setText(R.string.icon_about);
				break;

			}
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			mainWindowStatusText.setText("");
			holdOn = false;
		}
		return false;
	}
	
	public TextView getMainWindowStatusText(){
		return mainWindowStatusText;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return MyUtilits.onKeyDown(keyCode, event, this, mainWindowStatusText);
	}
}
