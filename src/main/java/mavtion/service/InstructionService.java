package mavtion.service;

import java.awt.Point;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;

import mavtion.domain.Instruction;
import mavtion.domain.InstructionSet;

/**
 * @author Aaron - this class handles all instruction related tasks
 *
 */
public class InstructionService {

	InstructionSet instructionSet;
	JTextArea textArea;
	JCheckBox _stopJobBox;
	LocationService ls = new LocationService();
	MouseService ms = new MouseService();
	KeyboardService ks = new KeyboardService();

	public InstructionSet getInstructionSet() {
		return instructionSet;
	}

	public void setInstructionSet(InstructionSet instructionSet) {
		this.instructionSet = instructionSet;
	}

	private Point parsePoint(String rawPoints) {
		// Box Coordinates
		if (rawPoints.contains("->")) {
			Point point1 = new Point(Integer.parseInt(rawPoints.split("->")[0].split(",")[0].replaceAll("[^\\d.]", "")),
					Integer.parseInt(rawPoints.split("->")[0].split(",")[1].replaceAll("[^\\d.]", "")));
			Point point2 = new Point(Integer.parseInt(rawPoints.split("->")[1].split(",")[0].replaceAll("[^\\d.]", "")),
					Integer.parseInt(rawPoints.split("->")[1].split(",")[1].replaceAll("[^\\d.]", "")));
			return ls.getPositionFromBox(point1, point2);
		}

		// Normal Coordinates
		else {
			Point point = new Point(Integer.parseInt(rawPoints.split(",")[0].replaceAll("[^\\d.]", "")),
					Integer.parseInt(rawPoints.split(",")[1].replaceAll("[^\\d.]", "")));
			return point;
		}
	}

	/**
	 * Runs all instructions in the InstructionSet
	 */
	public void executeInstructions() {
		if (instructionSet != null) {
			textArea.setText("");
			for (int i = 0; i < instructionSet.getQuantity(); i++) {
				if (i % 100 == 0)
					textArea.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": Log cleared.");
				for (Instruction instruction : instructionSet.getInstructions()) {
					if (_stopJobBox.isSelected())
						Thread.currentThread().stop();
					String command = instruction.getValue();

					if (command.contains("MOVEMOUSETO:BOXPOINT")) {
						Point p = parsePoint(command);
						updateTextArea("Moving mouse to (" + (int) p.getX() + ", " + (int) p.getY() + ").");
						ms.moveMouse((int) p.getX(), (int) p.getY());
					} else if (command.contains("MOVEMOUSETO:")) {
						Point p = parsePoint(command);
						updateTextArea("Moving mouse to (" + (int) p.getX() + ", " + (int) p.getY() + ").");
						ms.moveMouse((int) p.getX(), (int) p.getY());
					} else if (command.equalsIgnoreCase("CLICKLEFTMOUSE")) {
						updateTextArea("Sending left mouse-click.");
						ms.sendLeftMouseClick();
					} else if (command.equalsIgnoreCase("CLICKRIGHTMOUSE")) {
						updateTextArea("Sending right mouse-click.");
						ms.sendRightMouseClick();
					} else if (command.contains("WAIT:BETWEEN")) {
						try {
							int time = new Random()
									.ints(Integer.parseInt(command.split(",")[0].replaceAll("[^\\d.]", "")),
											Integer.parseInt(command.split(",")[1].replaceAll("[^\\d.]", "")))
									.findFirst().getAsInt();
							updateTextArea("Pausing for " + time + "ms.");
							Thread.sleep(time);
						} catch (InterruptedException e) {
						}
					} else if (command.contains("WAIT:")) {
						try {
							int time = Integer.parseInt(command.split(":")[1].replaceAll("[^\\d.]", ""));
							updateTextArea("Pausing for " + time + "ms.");
							Thread.sleep(time);
						} catch (InterruptedException e) {
						}
					} else if (command.contains("PRESSKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Sending key-press with key: " + key);
						ks.sendKeyPress(key);

					} else if (command.contains("HOLDKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Holding key: " + key);
						ks.holdKey(key);

					} else if (command.contains("RELEASEKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Releasing key: " + key);
						ks.releaseKey(key);
					}
					else if(command.contains("HOLDLEFTMOUSE")) {
						updateTextArea("Holding left-mouse button.");
						ms.holdLeftMouse();
					}
					else if(command.contains("RELEASELEFTMOUSE")) {
						updateTextArea("Releasing left-mouse button.");
						ms.releaseLeftMouse();
					}
				}
			}
		}
	}

	private void updateTextArea(String msg) {
		String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
		textArea.setText(timestamp + ": " + msg + "\n" + textArea.getText());
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JCheckBox get_stopJobBox() {
		return _stopJobBox;
	}

	public void set_stopJobBox(JCheckBox _stopJobBox) {
		this._stopJobBox = _stopJobBox;
	}

}
