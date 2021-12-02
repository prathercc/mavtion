package mavtion.service;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.util.Random;

/**
 * @author Aaron This class will handle all screen location gathering.
 */
public class LocationService {
	
	Robot robot;
	
	public LocationService() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public Point getPositionFromBox(Point one, Point two) {
		Point p = new Point();
		p.setLocation(new Random().ints((int) one.getX(), (int)two.getX()).findFirst().getAsInt(), 
				new Random().ints((int) one.getY(), (int)two.getY()).findFirst().getAsInt());
		return p;
	}
}
