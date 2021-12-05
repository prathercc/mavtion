package mavtion;

import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

	JButton startButton;

	public GlobalKeyListener(JButton btn) {
		startButton = btn;
	}

	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK && startButton.isEnabled()) {
			startButton.doClick();
		}
		if(e.getKeyCode() == NativeKeyEvent.VC_F2) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(new StringSelection("(" + (int) MouseInfo.getPointerInfo().getLocation().getX() + ","
					+ (int) MouseInfo.getPointerInfo().getLocation().getY() + ")"), null);
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
//		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
//		System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
	}
}
