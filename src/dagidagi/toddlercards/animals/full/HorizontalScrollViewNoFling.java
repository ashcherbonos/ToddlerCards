package dagidagi.toddlercards.animals.full;

import android.content.Context;
import android.util.AttributeSet;

public class HorizontalScrollViewNoFling extends android.widget.HorizontalScrollView {

	private static final int MAX_V=1000;
	
	public HorizontalScrollViewNoFling(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HorizontalScrollViewNoFling(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalScrollViewNoFling(Context context) {
		super(context);
	}
	
	@Override
	public void fling (int velocity)
	{
		if (velocity>MAX_V) velocity=MAX_V;
		else if (velocity<-MAX_V) velocity=-MAX_V;
		super.fling(velocity);
	}

}
