package dagidagi.toddlercards.animals.full;

import java.util.ArrayList;
import java.util.Collections;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import dagidagi.studio.toddlercards.animals.full.R;

public class TestActivity extends Activity {

	private CardSet cardSet;
	private ArrayList<Integer> cards;
	private int[] cardsInTestSet;
	private int roundCounter = 0;
	ImageView[] imageView;
	boolean[] viewAvaliable = { false, false, false, false };
	private int imageWidth;
	private int imageHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		MyUtilits.setToddlersMode(this);

		imageView = new ImageView[4];
		imageView[0] = (ImageView) findViewById(R.id.imageTest0);
		imageView[1] = (ImageView) findViewById(R.id.imageTest1);
		imageView[2] = (ImageView) findViewById(R.id.imageTest2);
		imageView[3] = (ImageView) findViewById(R.id.imageTest3);

		cardsInTestSet = new int[4]; // number of cards on test screen

		// sizing imageViews
		imageWidth = getWindowManager().getDefaultDisplay().getWidth() / 2;
		imageHeight = getWindowManager().getDefaultDisplay().getHeight() / 2;
		for (int i = 0; i <= 3; i++) {
			imageView[i].getLayoutParams().height = imageHeight;
			imageView[i].getLayoutParams().width = imageWidth;
			imageView[i].requestLayout();
		}

		// initializing cardSet
		Intent intent = getIntent();
		int cardSetIndex = intent.getIntExtra("CardSetIndex", 0);
		this.cardSet = new CardSet(cardSetIndex, this, CardsViewAdapter.STANDART_MODE);

		// shuffling cards
		cards = new ArrayList<Integer>();
		for (int i = 0; i < cardSet.getSize(); i++) {
			cards.add(i);
		}
		Collections.shuffle(cards);

		showNextRound();
	}

	private void showNextRound() {

		destroy();

		if (roundCounter >= cards.size()) {
			 startActivity(new Intent(this, WinActivity.class));
			 destroy();
			return;
		}

		ArrayList<Integer> cardsLeft = (ArrayList<Integer>) cards.clone();

		// adding 3 random cards to "round card"
		// and shuffling
		cardsLeft.remove(roundCounter);
		Collections.shuffle(cardsLeft);
		cardsLeft = new ArrayList<Integer>(cardsLeft.subList(0, 3));
		cardsLeft.add(cards.get(roundCounter));
		Collections.shuffle(cardsLeft);

		// Preparing bitmap options
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;

		// show 4 cards
		testImageShow(cardsLeft, options, 0, R.anim.runin0);
		testImageShow(cardsLeft, options, 1, R.anim.runin1);
		testImageShow(cardsLeft, options, 2, R.anim.runin2);
		testImageShow(cardsLeft, options, 3, R.anim.runin3);

		roundCounter++;

		// block imageViews till "run in" animation finish.
		imageView[0].postDelayed(new Runnable() {
			@Override
			public void run() {
				viewAvaliable[0] = viewAvaliable[1] = viewAvaliable[2] = viewAvaliable[3] = true;
				playQuestion(cards.get(roundCounter - 1));
			}
		}, 1400);
	}

	private void testImageShow(ArrayList<Integer> cardsLeft, BitmapFactory.Options options, int i, int anim) {
		// "right-answer" card shows in full size, all others in half size
		if (cardsLeft.get(i) == cards.get(roundCounter))
			imageView[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), cardSet.getImageResource(cardsLeft.get(i)),options));
		else {
			imageView[i].setImageBitmap(BitmapFactory.decodeResource(getResources(), cardSet.getImageResource(cardsLeft.get(i)), options));
		}
		imageView[i].startAnimation(AnimationUtils.loadAnimation(this, anim));
		cardsInTestSet[i] = cardsLeft.get(i);
	}

	private void playQuestion(int position) {
		MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), cardSet.getQuestionResource(position));
		mediaPlayer.start();
	}

	private void playVoice(final int position) {
		int soundResourse = cardSet.getVoiceResource(position);

		MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), soundResourse);
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				playSound(position);
			}
		});
		mediaPlayer.start();
	}

	private void playSound(int position) {
		int soundResourse = cardSet.getSoundResource(position);
		if (soundResourse != 0) {
			MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), soundResourse);
			mediaPlayer.start();
		}
	}

	public void onClickTest(View view) {
		// finding user answer
		int answer = -1;

		switch (view.getId()) {
		case R.id.imageTest0:
			answer = 0;
			break;
		case R.id.imageTest1:
			answer = 1;
			break;
		case R.id.imageTest2:
			answer = 2;
			break;
		case R.id.imageTest3:
			answer = 3;
		}

		if (viewAvaliable[answer]) {
			if (cardsInTestSet[answer] == cards.get(roundCounter - 1)) {
				rightAnswerShow(answer);
				playVoice(cardsInTestSet[answer]);
			} else {
				wrongAnswerShow(answer);
			}
		}
	}

	private void rightAnswerShow(int answer) {
		// run animation "scale" for selected imageView
		// run animation "runout" for all other imageViews
		// show next round after timedelay

		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				if (viewAvaliable[i]) {
					imageView[i].startAnimation(AnimationUtils.loadAnimation(this, (answer == i ? R.anim.scale0 : R.anim.runout0)));
				}
				break;
			case 1:
				if (viewAvaliable[i]) {
					imageView[i].startAnimation(AnimationUtils.loadAnimation(this, (answer == i ? R.anim.scale1 : R.anim.runout1)));
				}
				break;
			case 2:
				if (viewAvaliable[i]) {
					imageView[i].startAnimation(AnimationUtils.loadAnimation(this, (answer == i ? R.anim.scale2 : R.anim.runout2)));
				}
				break;
			case 3:
				if (viewAvaliable[i]) {
					imageView[i].startAnimation(AnimationUtils.loadAnimation(this, (answer == i ? R.anim.scale3 : R.anim.runout3)));
				}
				break;
			}
			viewAvaliable[i] = false;
		}

		// wait till animation finished
		imageView[answer].postDelayed(new Runnable() {
			@Override
			public void run() {
				showNextRound();
			}
		}, 4000);

	}

	private void wrongAnswerShow(int answer) {
		// run animation "runout" for all selected imageViews
		viewAvaliable[answer] = false;                                                   
		switch (answer) {
		case 0:
			imageView[0].startAnimation(AnimationUtils.loadAnimation(this, R.anim.runout0));
			break;
		case 1:
			imageView[1].startAnimation(AnimationUtils.loadAnimation(this, R.anim.runout1));
			break;
		case 2:
			imageView[2].startAnimation(AnimationUtils.loadAnimation(this, R.anim.runout2));
			break;
		case 3:
			imageView[3].startAnimation(AnimationUtils.loadAnimation(this, R.anim.runout3));
			break;
		}
	}

	private void destroy() {
		imageView[0].setImageBitmap(null);
		imageView[1].setImageBitmap(null);
		imageView[2].setImageBitmap(null);
		imageView[3].setImageBitmap(null);
		System.gc();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return MyUtilits.onKeyDown(keyCode, event, this);
	}
}
