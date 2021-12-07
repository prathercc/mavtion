package mavtion.domain;

import java.util.List;
import java.util.Random;

public class AfterInstructionSet extends InstructionSet {

	long lastExecution, rangeLow, rangeHigh;

	public AfterInstructionSet(List<Object> i, int q, long rangeLow, long rangeHigh) {
		super(i, q);
		this.rangeLow = rangeLow;
		this.rangeHigh = rangeHigh;
		this.lastExecution = System.currentTimeMillis();
	}

	public boolean shouldExecute() {
		long afterTimeMillis = new Random().longs(rangeLow, rangeHigh).findFirst().getAsLong();
		return System.currentTimeMillis() - this.lastExecution >= afterTimeMillis;
	}

	public long getLastExecution() {
		return lastExecution;
	}

	public void setLastExecution(long lastExecution) {
		this.lastExecution = lastExecution;
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

}
