package mavtion.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import mavtion.domain.Instruction;
import mavtion.domain.InstructionSet;

/**
 * @author Aaron - This class handles file related tasks.
 *
 */
public class FileService {

	/**
	 * Generates a set of instructions with the given file
	 */
	public InstructionSet parseInstructions(File file) {
		List<Instruction> instructions = new ArrayList<Instruction>();
		if (file != null && file.getName() != null && file.getName().contains(".mvsf")) {
			try {
				Scanner sc = new Scanner(file);
				int count = 0;
				int loopVal = -1;
				while(sc.hasNextLine()) {
					if(count == 0)
						loopVal = getLoopInterval(sc.nextLine());
					else
						instructions.add(new Instruction(sc.nextLine().split("//")[0].trim()));
					count++;
				}
				sc.close();
				return new InstructionSet(instructions.stream().filter(x -> !x.getValue().isEmpty()).collect(Collectors.toList()), loopVal);
			} catch (Exception e) {
				return null;
			}
			
		} else
			return null;
	}
	
	private int getLoopInterval(String str) {
		String loopStr = str.split("//")[0].trim(); //Remove comments
		String loopValue = loopStr.split("LOOP ")[1]; //Get value
		if(loopValue.equals("FOREVER"))
			return Integer.MAX_VALUE;
		else
			return Integer.parseInt(loopValue);
	}
}
