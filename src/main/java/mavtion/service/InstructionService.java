package mavtion.service;

import java.awt.Point;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.stream.Collectors;

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
		long timeAtStart = System.currentTimeMillis();
		if (instructionSet != null) {
			for (int i = 0; i < instructionSet.getQuantity(); i++) {
				if (i % 20 == 0 && i != 0)
					textArea.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()) + ": Log cleared.");
				for (Instruction instruction : instructionSet.getInstructions()) {
					if (_stopJobBox.isSelected())
						Thread.currentThread().stop();
					String command = instruction.getValue();

					if (command.contains("AFTER")) {
						long afterTimeMillis = new Random()
								.ints(Integer.parseInt(command.split(",")[0].replaceAll("[^\\d.]", "")),
										Integer.parseInt(command.split(",")[1].replaceAll("[^\\d.]", "")))
								.findFirst().getAsInt();
						int loopQty = Integer.parseInt(command.split(",")[2].replaceAll("[^\\d.]", ""));
						long currentTime = System.currentTimeMillis();
						if (currentTime - timeAtStart >= afterTimeMillis) {
							List<Object> tempInstructions = new ArrayList<Object>();
							for (Instruction _i : instructionSet.getInstructions().stream()
									.filter(x -> x.getValue().startsWith("_A:")).collect(Collectors.toList())) {
								tempInstructions.add(new Instruction(_i.getValue().replace("_A:", "")));
							}
							InstructionService tempService = new InstructionService();
							tempService.set_stopJobBox(_stopJobBox);
							tempService.setInstructionSet(new InstructionSet(tempInstructions, loopQty));
							tempService.setTextArea(textArea);
							tempService.executeInstructions();
							timeAtStart = System.currentTimeMillis();
						}

					} else if (command.startsWith("MOVEMOUSETO:BOXPOINT")) {
						Point p = parsePoint(command);
						updateTextArea("Moving mouse to (" + (int) p.getX() + ", " + (int) p.getY() + ").");
						ms.moveMouse((int) p.getX(), (int) p.getY());
					} else if (command.startsWith("MOVEMOUSETO:")) {
						Point p = parsePoint(command);
						updateTextArea("Moving mouse to (" + (int) p.getX() + ", " + (int) p.getY() + ").");
						ms.moveMouse((int) p.getX(), (int) p.getY());
					} else if (command.equalsIgnoreCase("CLICKLEFTMOUSE")) {
						updateTextArea("Sending left mouse-click.");
						ms.sendLeftMouseClick();
					} else if (command.equalsIgnoreCase("CLICKRIGHTMOUSE")) {
						updateTextArea("Sending right mouse-click.");
						ms.sendRightMouseClick();
					} else if (command.startsWith("WAIT:BETWEEN")) {
						try {
							int time = new Random()
									.ints(Integer.parseInt(command.split(",")[0].replaceAll("[^\\d.]", "")),
											Integer.parseInt(command.split(",")[1].replaceAll("[^\\d.]", "")))
									.findFirst().getAsInt();
							updateTextArea("Pausing for " + time + "ms.");
							Thread.sleep(time);
						} catch (InterruptedException e) {
						}
					} else if (command.startsWith("WAIT:")) {
						try {
							int time = Integer.parseInt(command.split(":")[1].replaceAll("[^\\d.]", ""));
							updateTextArea("Pausing for " + time + "ms.");
							Thread.sleep(time);
						} catch (InterruptedException e) {
						}
					} else if (command.startsWith("PRESSKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Sending key-press with key: " + key);
						ks.sendKeyPress(key);

					} else if (command.startsWith("HOLDKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Holding key: " + key);
						ks.holdKey(key);

					} else if (command.startsWith("RELEASEKEY:")) {
						String key = command.split(":")[1];
						updateTextArea("Releasing key: " + key);
						ks.releaseKey(key);
					} else if (command.equalsIgnoreCase("HOLDLEFTMOUSE")) {
						updateTextArea("Holding left-mouse button.");
						ms.holdLeftMouse();
					} else if (command.equalsIgnoreCase("RELEASELEFTMOUSE")) {
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
