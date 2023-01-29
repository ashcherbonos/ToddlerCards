package dagidagi.toddlercards.animals.full;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import dagidagi.studio.toddlercards.animals.full.R;

public class CardsViewAdapter extends PagerAdapter implements ViewPagerWithStopAdapter {

	public static final int STANDART_MODE = 0;
	public static final int SLIDSHOW_MODE = 1;

	private CardsViewActivity activity;
	private CardSet cardSet;
	private LayoutInflater inflater;
	private ViewPagerWithStop viewPager;
	private MediaPlayer mediaPlayer;
	private ImageView imgCard;
	private int showMode;
	private View viewLayout;
	private SharedPreferences sharedPreferences;
	private int currentPosition = 0;
	private int[] voiceResourse;
	private int[] soundResourse;


	// constructor
	public CardsViewAdapter(int cardSetIndex, CardsViewActivity activity, ViewPagerWithStop viewPager, int showMode) {
		this.activity = activity;
		this.cardSet = new CardSet(cardSetIndex, activity, showMode);
		this.viewPager = viewPager;
		this.showMode = showMode;

		// Preparing sound resources
		voiceResourse = new int[CardSetConfig.getTotalSize()];
		soundResourse = new int[CardSetConfig.getTotalSize()];
		voiceResourse[0] = cardSet.getVoiceResource(0);
		soundResourse[0] = cardSet.getSoundResource(0);

		sharedPreferences = activity.getSharedPreferences(CardSetConfig.APP_PREFERENCES, activity.MODE_PRIVATE);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// Showing card
		if (position < getCount() - 1) {
			// CARDS SHOW
			// Preparing
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewLayout = inflater.inflate(R.layout.card_layout, null, false);

			// show label
			((TextView) viewLayout.findViewById(R.id.cardLable)).setText(cardSet.getStringResource(position));

			// Showing Card image
			imgCard = (ImageView) viewLayout.findViewById(R.id.imageViewCard);
			
			Picasso.with(activity).load(cardSet.getImageResource(position)).into(imgCard);
			
			imgCard.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (viewPager.getState())
						playVoice();
					return true;
				}
			});

			// Preparing sound resources
			voiceResourse[position] = cardSet.getVoiceResource(position);
			soundResourse[position] = cardSet.getSoundResource(position);

		} else {
			// TEST start
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewLayout = inflater.inflate(R.layout.test_start, null, false);
		}

		// Add View to ViewPager
		((ViewPager) container).addView(viewLayout);

		return viewLayout;

	}

	@Override
	public int getCount() {
		if (showMode == STANDART_MODE)
			return cardSet.getSize() + 1;
		else {
			return CardSetConfig.getTotalSize() + 1;
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((ViewGroup) arg1);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((ViewGroup) object);
	}

	public void onPageSelected(int position) {
		if (position < getCount() - 1) {
			currentPosition = position;
			playVoice();
		}else playStartTest();
		activity.guiPrepared(position, showMode);
	}

	public void playStartTest(){
		mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), R.raw.lets_play_together_push_the_button);
		mediaPlayer.start();
	}
	
	public void playVoice() {
		// int soundResourse = cardSet.getVoiceResource(currentPosition);
		if (voiceResourse[currentPosition] != 0) {
			mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), voiceResourse[currentPosition]);
			// playSound() when sound stop playing
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					mediaPlayer.release();
					playSound();
				}
			});

			if (sharedPreferences.getBoolean(PreferencesActivity.PREF_BLOCK_SCROLLING, PreferencesActivity.DEFAULT_BLOCK_SCROLLING) || showMode == SLIDSHOW_MODE) {
				viewPager.stop(); // block page swiping when sound start playing
			}
			mediaPlayer.start();
		} else
			playSound();
	}

	public void playSound() {
		if (soundResourse[currentPosition] != 0) {
			mediaPlayer = MediaPlayer.create(activity.getApplicationContext(), soundResourse[currentPosition]);
			// unblock page swiping when sound stop playing
			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {
					viewPager.go(); // unblock page swiping
					mediaPlayer.release();
					slideshowNext();
				}
			});
			mediaPlayer.start();
		} else {
			viewPager.go(); // unblock page swiping
			slideshowNext();
		}
	}

	private void slideshowNext() {
		// if in slide show mode - show next slide
		if (showMode == SLIDSHOW_MODE && activity.isRuning()) {
			if (currentPosition >= getCount() - 2) { // end all sets -> start
													 // from the first
				CardSetConfig.next();
				viewPager.setCurrentItem(0, false);
			} else {
				viewPager.setCurrentItem(currentPosition + 1, true);
			}
		}
	}

	public void showNextSlide() {
		if (viewPager.getState()) {
			viewPager.setCurrentItem(currentPosition + 1, true);
		}
	}

	public void showPrevSlide() {
		if (viewPager.getState()) {
			viewPager.setCurrentItem(currentPosition - 1, true);
		}
	}
}
