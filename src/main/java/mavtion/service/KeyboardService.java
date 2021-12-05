package mavtion.service;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * @author Aaron - This service handles all keyboard actions.
 *
 */
public class KeyboardService {
	Robot robot;

	public KeyboardService() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Performs a standard keypress
	 */
	public void sendKeyPress(String key) {
		holdKey(key);
		releaseKey(key);
	}
	
	/**
	 * Holds down the specified key
	 */
	public void holdKey(String key) {
		robot.keyPress(parseKeyCode(key));
	}
	
	/**
	 * Releases the specified key
	 */
	public void releaseKey(String key) {
		robot.keyRelease(parseKeyCode(key));
	}
	
	private int parseKeyCode(String key) {
		switch(key) {
		case "0":
			return KeyEvent.VK_0;
		case "1":
			return KeyEvent.VK_1;
		case "2":
			return KeyEvent.VK_2;
		case "3":
			return KeyEvent.VK_3;
		case "4":
			return KeyEvent.VK_4;
		case "5":
			return KeyEvent.VK_5;
		case "6":
			return KeyEvent.VK_6;
		case "7":
			return KeyEvent.VK_7;
		case "8":
			return KeyEvent.VK_8;
		case "9":
			return KeyEvent.VK_9;
		case "SPACE":
			return KeyEvent.VK_SPACE;
		case "SHIFT":
			return KeyEvent.VK_SHIFT;
		default:
			return -1;
		}
	}
}
