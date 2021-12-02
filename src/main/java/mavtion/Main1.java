package mavtion;

import java.awt.Point;
import java.util.Random;

import mavtion.service.LocationService;
import mavtion.service.MouseService;

public class Main1 {

	public static void main(String[] args) throws InterruptedException {
		MouseService ms = new MouseService();
		LocationService ls = new LocationService();
		
		for(int i = 0; i < 5; i++) {
//			Point point = ls.getPositionFromBox(new Point(500,500), new Point(550,550));
//			ms.moveMouse((int) point.getX(), (int) point.getY());
			ms.moveMouse(new Random().ints(500, 900).findFirst().getAsInt(), new Random().ints(400, 900).findFirst().getAsInt());
//		ms.sendLeftMouseClick();
		}
	}

}
