package dagidagi.toddlercards.animals.full;

/**
 * Class extend ViewPager by adding possibility to block swiping and by
 * calling PagerAdapter.onPageSelected method in onPageSelected event.
 * 
 * To block swiping call: stop(); 
 * To unblock swiping call: go();
 * 
 * Default mode is unblocked;
 * 
 * Implementation of a PagerAdapter
 * must also implements ViewPagerWithStopAdapter interface,
 * for correctly work with  ViewPagerWithStop class.
 * 
 * 
 * UPD 1.1
 * realized smooth sliding
 */

import java.lang.reflect.Field;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

public class ViewPagerWithStop extends ViewPager {

	private boolean canSwipe;
	OnPageChangeListenerWhithSound pageChangeListener;

	public ViewPagerWithStop(Context context) {
		super(context);
		canSwipe = true;
		pageChangeListener = new OnPageChangeListenerWhithSound(this);
		setOnPageChangeListener(pageChangeListener);
		
		 setMyScroller();

	}

	public ViewPagerWithStop(Context context, AttributeSet attrs) {
		super(context, attrs);
		canSwipe = true;
		pageChangeListener = new OnPageChangeListenerWhithSound(this);
		setOnPageChangeListener(pageChangeListener);
		
		 setMyScroller();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (canSwipe) {
			return super.onInterceptTouchEvent(event);
		}
		return false;
	}

	public void stop() {
		/*
		 * Calling this method you prohibit swiping to switch between pages.
		 */
		canSwipe = false;
	}

	public void go() {
		/*
		 * Calling this method you allow swiping to switch between pages.
		 */
		canSwipe = true;
	}
	
	public boolean getState(){
		return canSwipe;		
	}

	/**
	 * Class OnPageChangeListenerWhithSound implements OnPageChangeListener and
	 * extend it by calling ViewPagerWithStopAdapter.onPageSelected in
	 * onPageSelected event.
	 */
	class OnPageChangeListenerWhithSound implements OnPageChangeListener {

		private ViewPagerWithStop viewPagerWithStop;

		public OnPageChangeListenerWhithSound(
				ViewPagerWithStop viewPagerWithStop) {
			this.viewPagerWithStop = viewPagerWithStop;
		}

		public void onPageScrollStateChanged(int state) {
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		public void onPageSelected(int position) {
			((ViewPagerWithStopAdapter) this.viewPagerWithStop.getAdapter())
					.onPageSelected(position);
		}
	}

// <making scrolling more smooth>
	private void setMyScroller() 
	  {
		
	    try 
	    {
	            Class<?> viewpager = ViewPager.class;
	            Field scroller = viewpager.getDeclaredField("mScroller");
	            scroller.setAccessible(true);
	            scroller.set(this, new MyScroller(getContext()));
	    } catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	  }

	  public class MyScroller extends Scroller 
	  {
	    public MyScroller(Context context) 
	    {
	        super(context, new DecelerateInterpolator());
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) 
	    {
	        super.startScroll(startX, startY, dx, dy, 700 /*mili sec*/);
	    }
	  }
// </making scrolling more smooth>
}

/**
 * Implementation of a PagerAdapter must also implements
 * ViewPagerWithStopAdapter interface, for correctly work with ViewPagerWithStop
 * class.
 */
interface ViewPagerWithStopAdapter {
	public void onPageSelected(int position);
}