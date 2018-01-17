package engine.operations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This gives a descriptive name for a parameter to Actions/Conditions (used in
 * authoring) and the VoogaType, which is used by the factories.
 * 
 * @author Ian Eldridge-Allegra
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface VoogaAnnotation {

	public String name();

	public VoogaType type();

}
