package dagidagi.toddlercards.animals.full;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;

public class CardSet {
	private Activity activity;
	private int showMode;
	private ArrayList<Integer> cards;

	public CardSet(int cardSetIndex, Activity activity, int showMode) {
		CardSetConfig.setCurrent(cardSetIndex);
		this.activity = activity;
		this.showMode = showMode;		
		
		// shuffle cards if needed:
		cards = new ArrayList<Integer>();
		for (int i = 0; i < getSize(); i++) {
			cards.add(i);
		}		
		if(activity.getSharedPreferences(CardSetConfig.APP_PREFERENCES, activity.MODE_PRIVATE).getBoolean(PreferencesActivity.PREF_SHUFFLE_CARDS, PreferencesActivity.DEFAULT_SHUFFLE_CARDS)){
			Collections.shuffle(cards);	
		}				
	}
	
	private int position(int position){
		return cards.get(position);
	}
	
	/// return dynamically generated card image id
	/// card image should have name cardSetName#
	/// for example: dog0 dog1 dog2 dog3...
	public int getImageResource(int position) {
		position = correctPosition(position);
		return activity.getResources().getIdentifier(getName() + position(position), "drawable", activity.getPackageName());
	}
	
	/// return dynamically generated card sound id
	/// card sound should have name cardSetName#
	/// for example: dog0 dog1 dog2 dog3...
	public int getSoundResource(int position) {
		 position=correctPosition(position);
		 return activity.getResources().getIdentifier(getName() + "_sound" + position(position),
		 "raw", activity.getPackageName());
	}

	/// return dynamically generated card sound id
	/// card sound should have name cardSetName#
	/// for example: dog0 dog1 dog2 dog3...
	public int getVoiceResource(int position) {
		 position=correctPosition(position);
		 return activity.getResources().getIdentifier(getName() + position(position) + "_en",
		 "raw", activity.getPackageName());
	}
	
	/// return dynamically generated card sound id
	/// card sound should have name cardSetName#
	/// for example: dog0 dog1 dog2 dog3...	
	public int getQuestionResource(int position) {
		 return activity.getResources().getIdentifier(getName()+ "_where" + position(position) + "_en",
		 "raw", activity.getPackageName());
	}
	
	/// return dynamically generated card string id
	/// card string should have name cardSetName#
	/// for example: dog0 dog1 dog2 dog3...
	public int getStringResource(int position) {
		 position=correctPosition(position);
		 return activity.getResources().getIdentifier(getName() + position(position),
		 "string", activity.getPackageName());
	}
	
	public int getSize() {
		if (showMode == CardsViewAdapter.SLIDSHOW_MODE){
			return CardSetConfig.getTotalSize();
		}else
			return CardSetConfig.getSize();
	}

	private String getName() {
		return CardSetConfig.getName();
	}

	private int correctPosition(int position) {
		// convert position in total list(all sets) to position in current set
		if (showMode == CardsViewAdapter.SLIDSHOW_MODE) {		
			for (int i = 0; i < CardSetConfig.getCurrent(); i++) {
				position -= CardSetConfig.getSize(i);				
			}
			if (position >= CardSetConfig.getSize()) { // go to next card set
				position = 0;
				CardSetConfig.next();
			}
		}
		return position;
	}
}