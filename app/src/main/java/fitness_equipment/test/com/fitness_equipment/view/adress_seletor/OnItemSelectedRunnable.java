package fitness_equipment.test.com.fitness_equipment.view.adress_seletor;

final class OnItemSelectedRunnable implements Runnable {
	final WheelView loopView;

	OnItemSelectedRunnable(WheelView loopview) {
		loopView = loopview;
	}

	@Override
	public final void run() {
		loopView.onItemSelectedListener.onItemSelected(loopView
				.getCurrentItem());
	}
}
