package engine.operations;

/**
 * A parsed VoogaAnnotation.
 * 
 * @author Ian Eldridge-Allegra
 */
public class VoogaParameter {

	private VoogaType type;
	private String name;

	public VoogaParameter(String name, VoogaType type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public VoogaType getType() {
		return type;
	}
	
}
