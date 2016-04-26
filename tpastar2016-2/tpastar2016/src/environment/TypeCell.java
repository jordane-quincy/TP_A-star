package environment;

public enum TypeCell {
	HERBE(1.0), 
	SABLE(1.2), 
	EAU(1.5),
	OBSTACLE(20);
	
	/** cost to walk on the environment*/
	private double value;
	
	private TypeCell(double _value){value = _value;}
	
	/** return the cost to walk on the environment*/
	public double getValue() {
		return value;
	}
}

