package engine.operations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import engine.Actions.ActionFactory;

class ActionOperationPropertyGenerator {
	public static void main(String[] args) {
		ActionFactory actFact = new ActionFactory();
		Properties prop = new Properties();
		for(String cat : actFact.getCategories()) {
			for(String name : actFact.getActions(cat)) {
				prop.put(name, actFact.makeAction(name, new Object[actFact.getParametersWithNames(name).size()]).getClass().getName());
			}
		}

		try {
			FileOutputStream out = new FileOutputStream("src/engine/operations/actions.properties");
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to Generate");
		}
	}
}
