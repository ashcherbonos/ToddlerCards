package dagidagi.toddlercards.animals.full;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import dagidagi.studio.toddlercards.animals.full.R;

public class WinActivity extends Activity {

	private final int maxFireworks = 10;
	private ImageView[] imageFireworks = new ImageView[maxFireworks];
	private int currentFireImage;

	private Random random;

	private SoundPool soundPool;
	private final int maxSounds = 2;
	private int[] sound = new int[maxSounds];
	int currentSound = 0;
	private boolean isRuning;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.win_layout);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		currentFireImage = 0;
		currentSound = 0;
		random = new Random();
		isRuning = true;

		imageFireworks[0] = (ImageView) findViewById(R.id.imageFireworks0);
		imageFireworks[1] = (ImageView) findViewById(R.id.imageFireworks1);
		imageFireworks[2] = (ImageView) findViewById(R.id.imageFireworks2);
		imageFireworks[3] = (ImageView) findViewById(R.id.imageFireworks3);
		imageFireworks[4] = (ImageView) findViewById(R.id.imageFireworks4);
		imageFireworks[5] = (ImageView) findViewById(R.id.imageFireworks5);
		imageFireworks[6] = (ImageView) findViewById(R.id.imageFireworks6);
		imageFireworks[7] = (ImageView) findViewById(R.id.imageFireworks7);
		imageFireworks[8] = (ImageView) findViewById(R.id.imageFireworks8);
		imageFireworks[9] = (ImageView) findViewById(R.id.imageFireworks9);

		// setting imageFireworks size and visibility:
		for (int i = 0; i < maxFireworks; i++) {
			imageFireworks[i].setVisibility(View.INVISIBLE);
			setViewSize(imageFireworks[i], imageSquare() * 2 / 3);
		}

		// fireworks
		startFireworks();

		// medal run in
		setViewSize(findViewById(R.id.imageMedal), imageSquare() * 2 / 3);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.medal_runin);
		findViewById(R.id.imageMedal).startAnimation(anim);
		// left rossete run in
		setViewSize(findViewById(R.id.imageRosseteLeft), imageSquare() * 2 / 3);
		anim = AnimationUtils.loadAnimation(this, R.anim.rossete_left_runin);
		findViewById(R.id.imageRosseteLeft).startAnimation(anim);
		// right rossete run in
		setViewSize(findViewById(R.id.imageRosseteRight), imageSquare() * 2 / 3);
		anim = AnimationUtils.loadAnimation(this, R.anim.rossete_right_runin);
		findViewById(R.id.imageRosseteRight).startAnimation(anim);
		
		
		// play sound
		MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.cheers);
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				stopActivity();
			}
		});
		mediaPlayer.start();

	}

	private int imageSquare() {
		int imageSize = getWindowManager().getDefaultDisplay().getWidth();
		int imageHeight = getWindowManager().getDefaultDisplay().getHeight();
		if (imageSize > imageHeight)
			imageSize = imageHeight;
		return imageSize;
	}

	private void setViewSize(View view, int size) {
		FrameLayout.LayoutParams params = (LayoutParams) view.getLayoutParams();
		params.height = size;
		params.width = size;
		view.requestLayout();
	}

	private void startFireworks() {
		if (isRuning()) {
			fireBoom(random.nextInt(getWindowManager().getDefaultDisplay().getWidth()), random.nextInt(getWindowManager().getDefaultDisplay().getHeight()));
			imageFireworks[0].postDelayed(new Runnable() {
				@Override
				public void run() {
					startFireworks();
				}
			}, 50);
		}
	}

	private void fireBoom(int x, int y) {

		FrameLayout.LayoutParams params = (LayoutParams) imageFireworks[currentFireImage].getLayoutParams();

		params.topMargin = y - (imageFireworks[currentFireImage].getHeight() / 2);
		params.leftMargin = x - (imageFireworks[currentFireImage].getWidth() / 2);
		params.gravity = Gravity.NO_GRAVITY;// need to margins work on low
											// SdkVersions...
		imageFireworks[currentFireImage].setLayoutParams(params);

		imageFireworks[currentFireImage].setVisibility(View.VISIBLE);

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.fireworks);
		imageFireworks[currentFireImage].startAnimation(anim);
		nextCurrentFireImage();
	}


	public void onClick(View view) {
		stopActivity();
	}
	
	private void stopActivity(){
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
	
	private void nextCurrentFireImage() {
		currentFireImage++;
		if (currentFireImage >= maxFireworks) {
			currentFireImage = 0;
		}
	}

	private void nextCurrentSound() {
		currentSound++;
		if (currentSound >= maxSounds) {
			currentSound = 0;
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
		isRuning = false;
	}

	public boolean isRuning() {
		return isRuning;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return MyUtilits.onKeyDown(keyCode, event, this);
	}
}
