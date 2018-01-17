package engine;

import java.util.ResourceBundle;

/**
 * @author Ian Eldridge-Allegra
 *
 */
public class VoogaException extends RuntimeException {
	private static final long serialVersionUID = -1967443635275762601L;
	private static final ResourceBundle ERROR_BUNDLE = ResourceBundle.getBundle("resources.exceptions.exceptions");

	public VoogaException(String key, Object... objects) {
		super(String.format(ERROR_BUNDLE.getString(key), objects));
	}

	public VoogaException(Exception e) {
		super(e.getMessage());
	}
}
