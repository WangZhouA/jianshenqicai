package fitness_equipment.test.com.fitness_equipment.view.adress_seletor;

import android.view.MotionEvent;

final class LoopViewGestureListener extends
		android.view.GestureDetector.SimpleOnGestureListener {

	final WheelView loopView;

	LoopViewGestureListener(WheelView loopview) {
		loopView = loopview;
	}

	@Override
	public final boolean onFling(MotionEvent e1, MotionEvent e2,
                                 float velocityX, float velocityY) {
		loopView.scrollBy(velocityY);
		return true;
	}
}
