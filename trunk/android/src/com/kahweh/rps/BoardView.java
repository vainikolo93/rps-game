/**
 * 
 */
package com.kahweh.rps;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author Michael
 *
 */
public class BoardView extends View {

	public BoardView(Context context) {
		super(context);
		setBackgroundResource(R.drawable.board320_480);
		
		setOnTouchListener(null);
	}

	@Override
	protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
	}

	@Override
	protected void onDraw(Canvas canv) {
		
	}
}
