package mavtion;
import javax.swing.JLabel;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class GlobalMouseListener implements NativeMouseInputListener {
	JLabel label;
	
	public GlobalMouseListener(JLabel l) {
		label = l;
	}
	
	public void nativeMouseClicked(NativeMouseEvent e) {
//		System.out.println("Mouse Clicked: " + e.getClickCount());
	}

	public void nativeMousePressed(NativeMouseEvent e) {
//		System.out.println("Mouse Pressed: " + e.getButton());
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
//		System.out.println("Mouse Released: " + e.getButton());
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		label.setText("(" + e.getX() + ", " + e.getY() + ")");
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
//		System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}
}