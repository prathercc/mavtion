package mavtion.domain;

import java.util.ArrayList;
import java.util.List;

public class InstructionSet {
	
	List<Instruction> instructions;
	
	int quantity;
	
	public InstructionSet(List<Object> i, int q) {
		instructions = new ArrayList<Instruction>();
		i.stream().forEach(x -> instructions.add((Instruction) x));
		quantity = q;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
