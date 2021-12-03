package mavtion.service;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

/**
 * @author Aaron - This class handles all mouse related actions that the
 *         program needs.
 *
 */
public class MouseService {

	Robot robot;

	public MouseService() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Simulate a left mouse click
	 */
	public void sendLeftMouseClick() {
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	/**
	 * Simulate a right mouse click
	 */
	public void sendRightMouseClick() {
		robot.mousePress(InputEvent.BUTTON3_MASK);
		robot.mouseRelease(InputEvent.BUTTON3_MASK);
	}

	/**
	 * This function will set the mouse cursor position to the point specified.
	 */
	public void putMouse(int x, int y) {
		robot.mouseMove(x, y);
	}

	/**
	 * This function will gracefully move the mouse cursor to the specified
	 *      position
	 */
	public void moveMouse(int x, int y) {
		int activeXCoord = (int) MouseInfo.getPointerInfo().getLocation().getX();
		int activeYCoord = (int) MouseInfo.getPointerInfo().getLocation().getY();
		while (activeXCoord != x || activeYCoord != y) {
			try {
				Thread.sleep(new Random().ints(1, 4).findFirst().getAsInt());
			} catch (Exception e) {
			}
			if (activeXCoord != x) {
				if (activeXCoord < x)
					activeXCoord++;
				else
					activeXCoord--;
			}
			if (activeYCoord != y) {
				if (activeYCoord < y)
					activeYCoord++;
				else
					activeYCoord--;
			}
			putMouse(activeXCoord, activeYCoord);
			
			int slope = 0;
			try {
				slope = ((y - activeYCoord) / (x - activeXCoord));
			}catch(Exception e) {}
			
			int xOffset = new Random().ints(1, 4).findFirst().getAsInt();
			int yOffset = new Random().ints(1, 4).findFirst().getAsInt();
			if (xOffset == yOffset)
				yOffset++;
			
			if (activeXCoord == x && activeYCoord != y) {
				for (int i = 0; i < xOffset; i++) {
					activeXCoord++;
					putMouse(activeXCoord, activeYCoord);
				}
			} else if (activeXCoord != x && activeYCoord == y) {
				for (int i = 0; i < yOffset; i++) {
					activeYCoord++;
					putMouse(activeXCoord, activeYCoord);
				}
			}
			else if(Math.abs(slope) == 1) {
				activeXCoord++;
				putMouse(activeXCoord, activeYCoord);
			}
		}
	}
}
