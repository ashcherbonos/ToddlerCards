package dagidagi.toddlercards.animals.full;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import dagidagi.studio.toddlercards.animals.full.R;

public class CardsViewActivity extends Activity {

	private CardsViewAdapter adapter;
	private ViewPagerWithStop viewPager;
	private boolean isRuning = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cards_view);
		MyUtilits.setToddlersMode(this);

		// read "card set name" from Main activity
		Intent intent = getIntent();
		int cardSetIndex = intent.getIntExtra("CardSetIndex", 0);
		int showMode = intent.getIntExtra("Mode", CardsViewAdapter.STANDART_MODE);

		// Create ViewPager
		viewPager = (ViewPagerWithStop) findViewById(R.id.pager);
		adapter = new CardsViewAdapter(cardSetIndex, this, viewPager, showMode);
		viewPager.setAdapter(adapter);
		viewPager.pageChangeListener.onPageSelected(0);
	}

	public void imageViewCardOnClick(View v) {
		if (viewPager.getState())
			adapter.playVoice();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageQuickTest:
		case R.id.testStartBtn:
			startTest();
			break;
		case R.id.imageNextCard:
			adapter.showNextSlide();
			break;
		case R.id.imagePrevCard:
			adapter.showPrevSlide();
			break;
		case R.id.copyright:
			Toast toast = Toast.makeText(getApplicationContext(), R.string.copyright, Toast.LENGTH_SHORT);
			toast.show();
			break;
		}
	}

	private void startTest() {
		Intent intent = getIntent();
		int cardSetIndex = intent.getIntExtra("CardSetIndex", 0);
		intent = new Intent(this, TestActivity.class);
		intent.putExtra("CardSetIndex", cardSetIndex);
		startActivity(intent);
	}

	public void guiPrepared(int position, int showMode) {
		if (showMode == CardsViewAdapter.SLIDSHOW_MODE) {
			findViewById(R.id.imagePrevCard).setVisibility(View.INVISIBLE);
			findViewById(R.id.imageNextCard).setVisibility(View.INVISIBLE);
			findViewById(R.id.imageQuickTest).setVisibility(View.INVISIBLE);
		} else if (position == 0) {
			findViewById(R.id.imagePrevCard).setVisibility(View.INVISIBLE);
		} else if (position == adapter.getCount() - 1) {
			findViewById(R.id.imageNextCard).setVisibility(View.INVISIBLE);
			findViewById(R.id.imageQuickTest).setVisibility(View.INVISIBLE);
			findViewById(R.id.copyright).setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.imageQuickTest).setVisibility(View.VISIBLE);
			findViewById(R.id.imageNextCard).setVisibility(View.VISIBLE);
			findViewById(R.id.imagePrevCard).setVisibility(View.VISIBLE);
			findViewById(R.id.copyright).setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		isRuning = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		adapter = null;
		viewPager = null;
		isRuning = false;
	}

	public boolean isRuning() {
		return isRuning;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return MyUtilits.onKeyDown(keyCode, event, this);
	}
}
