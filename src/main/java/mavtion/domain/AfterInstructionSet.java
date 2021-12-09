package mavtion.domain;

import java.util.List;
import java.util.Random;

public class AfterInstructionSet extends InstructionSet {

	long rangeLow, rangeHigh, nextExecution;

	public AfterInstructionSet(List<Object> i, int q, long rangeLow, long rangeHigh) {
		super(i, q);
		this.rangeLow = rangeLow;
		this.rangeHigh = rangeHigh;
		this.nextExecution = generateNextExecution();
	}
	
	public long generateNextExecution() {
		return new Random().longs(rangeLow, rangeHigh).findFirst().getAsLong() + System.currentTimeMillis();
	}

	public boolean shouldExecute() {
		return System.currentTimeMillis() >= nextExecution;
	}

	public long getRangeLow() {
		return rangeLow;
	}

	public void setRangeLow(long rangeLow) {
		this.rangeLow = rangeLow;
	}

	public long getRangeHigh() {
		return rangeHigh;
	}

	public void setRangeHigh(long rangeHigh) {
		this.rangeHigh = rangeHigh;
	}
	
	public long getNextExecution() {
		return nextExecution;
	}

	public void setNextExecution(long nextExecution) {
		this.nextExecution = nextExecution;
	}

}
