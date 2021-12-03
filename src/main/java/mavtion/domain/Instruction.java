package mavtion.domain;

public class Instruction {
	
	String value;
	
	public Instruction(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
