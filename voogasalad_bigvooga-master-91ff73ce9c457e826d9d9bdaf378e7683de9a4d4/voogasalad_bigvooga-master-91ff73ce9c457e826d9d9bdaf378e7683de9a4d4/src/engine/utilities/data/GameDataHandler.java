package engine.utilities.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.function.Supplier;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import authoring.CutScene.SuperlayerSequence;
import authoring.DialogSprite.AuthoringDialogSequence;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteObject;
import authoring_UI.DraggableGrid;
import authoring_UI.MapDataConverter;
import authoring_UI.SpriteDataConverter;
import engine.EngineController;
import engine.VoogaException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This class saves and loads all the game data to the local system. The first
 * time it is used, it gets the directory to be used as the workspace. This is
 * assumed to be empty. On subsequent runs, it will use this workspace based on
 * the local.properties file. Unfortunately, eclipse does not update the files
 * quickly, so to avoid prompting multiple times during the same run there is a
 * static variable used to access the working directory of the program.
 * 
 * @author Ian Eldridge-Allegra and others
 *
 */
public class GameDataHandler {
	// An unfortunate concession to the slowness of the file system
	private static String root;

	private static final String DIRECTORY_PATH = "Directory Path";
	private static final XStream SERIALIZER = setupXStream();
	public static final String RESOURCES = "resources/";
	private static final String KNOWN_PROJECTS = RESOURCES + "KnownProjectNames.txt";
	private static final String ENGINE_PATH = "engine/";
	private static final String CONTROLLER_FILE = "Engine_Controller_Save_File";
	private static final String CONTINUE_FILE = "Engine_Controller_Load_File";

	private static final String AUTHORING_PATH = "authoring/";
	private static final String PROJECT_USER_SPRITE_PATH = AUTHORING_PATH + "Sprites/";
	private static final String PROJECT_WORLD_PATH = AUTHORING_PATH + "Worlds/";
	private static final String PROJECT_LAYER_SPRITE_PATH = AUTHORING_PATH + "Sprites/";
	private static final String PROJECT_UICOMPONENT_SPRITE_PATH = AUTHORING_PATH + "UIComponents/";
	private static final String DEFAULT_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "DefaultSprites/";
	private static final String CUSTOM_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "CustomSprites/";
	private static final String INVENTORY_SPRITE_FOLDER = PROJECT_USER_SPRITE_PATH + "InventorySprites/";
	private static final String DEFAULT_CATEGORY = "General/";
	private static final String CUT_SCENE_SPRITE_FOLDER = PROJECT_UICOMPONENT_SPRITE_PATH + "CutSceneSprites/";
	private static final String DIALOG_SPRITE_FOLDER = PROJECT_UICOMPONENT_SPRITE_PATH + "DialogSprites/";
	private static final String INVENTORY_TEMPLATE_SPRITE_FOLDER = PROJECT_UICOMPONENT_SPRITE_PATH
			+ "InventoryTemplates/";

	private static final String LOCAL = "local";

	private static final String SELECTOR_TITLE = "Open Resource File";

	private static final ExtensionFilter[] imageFilters = new ExtensionFilter[] {
			new ExtensionFilter("Image Files (*.png)", "*.png"), new ExtensionFilter("Image Files (*.jpg)", "*.jpg"),
			new ExtensionFilter("Image Files (*.jpeg)", "*.jpeg"),
			new ExtensionFilter("Image Files (*.gif)", "*.gif") };

	private static final String SPRITE_EXTENSION = ".spr";

	private static final String WORLD_EXTENSION = ".wld";

	private static final String DIALOG_EXTENSION = ".dlg";

	private Map<String, Image> cache = new HashMap<>();
	private String myImportProjectPath;
	private String projectPath;
	private String projectName;

	private static XStream setupXStream() {
		XStream xstream = new XStream(new DomDriver());
		// xstream.addPermission(NoTypePermission.NONE);
		xstream.addPermission(NullPermission.NULL);
		xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
		xstream.allowTypes(new Class[] { Point2D.class });
		xstream.allowTypesByWildcard(new String[] { "engine.**", "java.**" });
		return xstream;
	}

	/**
	 * @param s
	 *            used to select a directory. This should not be used except when no
	 *            saving/loading will occur.
	 */
	public GameDataHandler(Stage s) {
		this(() -> selectDirectory(s).getAbsolutePath());
	}

	/**
	 * @param s
	 *            Used to select a directory.
	 * @param projectName The name of the project to save/load from.
	 */
	public GameDataHandler(Stage s, String projectName) {
		this(() -> selectDirectory(s).getAbsolutePath(), projectName);
	}

	/**
	 * @param pathSupplier Supplies path to working directory, if needed.
	 */
	public GameDataHandler(Supplier<String> pathSupplier) {
		this(pathSupplier, "Test Project");
	}

	/**
	 * @param pathSupplier supplies path to working directory, if needed.
	 * @param projectName 
	 */
	public GameDataHandler(Supplier<String> pathSupplier, String projectName) {
		this.projectName = projectName;
		this.projectPath = getPath(pathSupplier) + projectName + "/";
		makeDirectories();
	}

	private Object getObjectFromFile(File file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new VoogaException(e);
		}
		String fileContents = scanner.useDelimiter("\\Z").next();
		scanner.close();
		return SERIALIZER.fromXML(fileContents);
	}

	private Object getObjectFromFile(String path) {
		return getObjectFromFile(new File(path));
	}

	private void saveToFile(Object object, String filePath) {
		String toSave = SERIALIZER.toXML(object);
		FileWriter writer;
		try {
			writer = new FileWriter(filePath);
			writer.write(toSave);
			writer.close();
		} catch (IOException e) {
			throw new VoogaException("SaveFail");
		}
	}

	private void makeDirectories() {
		String[] pathsToMake = new String[] { ENGINE_PATH, RESOURCES, PROJECT_WORLD_PATH, PROJECT_WORLD_PATH,
				PROJECT_LAYER_SPRITE_PATH, DEFAULT_SPRITE_FOLDER, CUSTOM_SPRITE_FOLDER, INVENTORY_SPRITE_FOLDER,
				PROJECT_UICOMPONENT_SPRITE_PATH, CUT_SCENE_SPRITE_FOLDER, DIALOG_SPRITE_FOLDER,
				INVENTORY_TEMPLATE_SPRITE_FOLDER };
		for (String s : pathsToMake) {
			makeDirectory(projectPath + s);
		}
		makeDirectory(root + RESOURCES);
	}

	private String getPath(Supplier<String> pathSupplier) {
		if (root != null)
			return root;
		try {
			ResourceBundle local = ResourceBundle.getBundle(LOCAL);
			String s = local.getString(DIRECTORY_PATH);
			System.out.println(s);
			if (!isValidDirectory(new File(s)))
				throw new FileNotFoundException();
			root = s;
		} catch (Exception e) {
			root = pathSupplier.get() + "/";
			setDirectoryPath(root);
			makeDirectory(root + RESOURCES);
		}
		return root;
	}

	/**
	 * @param path The new absolute location of the working directory.
	 */
	public void setDirectoryPath(String path) {
		Properties prop = new Properties();
		try {
			FileInputStream in = new FileInputStream(RESOURCES + LOCAL + ".properties");
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		prop.put(DIRECTORY_PATH, path);
		try {
			FileOutputStream out = new FileOutputStream(RESOURCES + LOCAL + ".properties");
			prop.store(out, null);
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param controller
	 *            to serialize
	 */
	public void saveGame(EngineController controller) {
		saveGame(controller, CONTROLLER_FILE);
		try {
			addToKnown();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void addToKnown() throws IOException {
		List<String> known = knownProjects();
		if (!known.contains(projectName))
			known.add(projectName);
		FileWriter fileWriter = new FileWriter(root + KNOWN_PROJECTS);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		for (String s : known) {
			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
		bufferedWriter.close();
	}

	/**
	 * @return The known exported games.
	 */
	public List<String> knownProjects() {
		List<String> known = new ArrayList<>();
		try {
			FileReader fileReader = new FileReader(root + KNOWN_PROJECTS);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				known.add(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			// Intentionally Blank
		}
		return known;
	}

	/**
	 * @param controller Saves the game under a different file than saveGame.
	 */
	public void saveForContinue(EngineController controller) {
		saveGame(controller, CONTINUE_FILE);
	}

	private void saveGame(EngineController controller, String gameName) {
		saveToFile(controller, projectPath + ENGINE_PATH + gameName);
	}

	/**
	 * @return The loaded EngineController from the project
	 */
	public EngineController loadGame() {
		return loadGame(CONTROLLER_FILE);
	}

	/**
	 * @return The game to continue, or the original if none are found.
	 */
	public EngineController loadContinueGame() {
		try {
			return loadGame(CONTINUE_FILE);
		} catch (VoogaException e) {
			return loadGame();
		}
	}

	private EngineController loadGame(String saveGameName) {
		return (EngineController) getObjectFromFile(projectPath + ENGINE_PATH + saveGameName);
	}

	/**
	 * @param fileName
	 *            simple file name of file in the project's resources directory
	 * @return The Image
	 */
	public Image getImage(String fileName) {
		if (cache.containsKey(fileName)) {
			return cache.get(fileName);
		}
		String path = new File(projectPath + RESOURCES + fileName).toURI().toString();
		Image i = new Image(path);
		cache.put(fileName, i);
		return i;
	}

	/**
	 * Adds the image to the project's resources directory and returns it.
	 * 
	 * @param file 
	 * @return The image from the file
	 */
	public Image getImage(File file) {
		addFileToProject(file);
		Image im = getImage(file.getName());
		cache.put(file.getName(), im);
		return im;
	}

	/**
	 * @param file
	 *            Adds the file to the project
	 */
	public void addFileToProject(File file) {
		if (file != null) {
			try {
				Files.copy(file.toPath(), Paths.get(projectPath + RESOURCES + file.getName()),
						StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new VoogaException(e);
			}
		}
	}

	public static Image toImage(File file) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			return new Image(fis);
		} catch (FileNotFoundException e) {
			return new Image("");
		}
	}

	/**
	 * Forces a choice of directory to be made by the user.
	 * 
	 * @param stage Context in which to display the chooser.
	 * @return The selected directory.
	 */
	public static File selectDirectory(Stage stage) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION,
				"Select an Empty Working Directory for use by the Program");
		alert.showAndWait();
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select Empty Working Directory for Workspace");
		File ret = null;
		while (ret == null)
			ret = chooser.showDialog(stage);
		return ret;
	}

	/**
	 * @param stage
	 *            To present dialog
	 * @return File Chosen
	 */
	public static File chooseFileForImageLoad(Window window) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(SELECTOR_TITLE);
		fileChooser.getExtensionFilters().addAll(imageFilters);
		return fileChooser.showOpenDialog(window);
	}

	// public String getImageURIAndCopyToResources(File file) {
	// try {
	// Files.copy(file.toPath(), Paths.get(root + RESOURCES + file.getName()),
	// StandardCopyOption.REPLACE_EXISTING);
	// addFileToProject(file);
	// } catch (IOException e) {
	// throw new VoogaException(e);
	// }
	// String URI = file.toURI().toString();
	// return URI;
	// }

	private static void makeDirectory(String path) {
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	private boolean directoryExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	private void removeDirectory(String path) {
		File f = new File(path);
		if (f.exists() && f.listFiles().length > 0) {
			for (File child : f.listFiles()) {
				child.delete();
			}
		}
		f.delete();
	}

	public void saveSprite(AbstractSpriteObject SO, String categoryPath, String name) throws Exception {
		if (!directoryExists(categoryPath)) {
			makeDirectory(categoryPath);
		}
		saveSprite(SO, categoryPath + name);
	}

	public void saveSprite(AbstractSpriteObject SO, String path) throws Exception {
		if (SO.getSavePath() == null) {
			SO.setSavePath(path);
		}
		SpriteDataConverter SDC = new SpriteDataConverter(SO);
		saveSprite(SDC, path);
	}

	private void saveSprite(SpriteDataConverter SO, String path) {
		saveToFile(SO, path + SPRITE_EXTENSION);
	}

	public void saveDefaultSprite(AbstractSpriteObject SO) throws Exception {
		String BasicPath = getDefaultSpriteDirectoryPath() + DEFAULT_CATEGORY + SO.getName();
		saveSprite(SO, BasicPath);
	}

	public String makeValidFileName(String path, String ext) {
		path = path + "/";
		if (!directoryExists(path)) {
			makeDirectory(path);
		}
		int counter = 1;
		File temp = new File(path + counter + ext);
		while (temp.exists()) {
			counter++;
			temp = new File(path + counter + ext);
		}
		return path;
	}

	public void saveUserCreatedSprite(SpriteObject SO) throws Exception {
		String newSpritePath = makeValidFileName(getCustomSpriteDirectoryPath() + DEFAULT_CATEGORY + SO.getName(),
				SPRITE_EXTENSION);
		saveSprite(SO, newSpritePath);
	}

	public AbstractSpriteObject loadSprite(File spriteFile) throws VoogaException {
		if (!isValidFile(spriteFile)) {
			throw new VoogaException("Invalid file to load");
		}
		SpriteDataConverter SDC = (SpriteDataConverter) getObjectFromFile(spriteFile);
		// 12/15/17 DEPENDENCY: setGameHandler Before creating Sprite.
		SDC.setGameDataHandler(this);
		AbstractSpriteObject ret = SDC.createSprite();
		return ret;
	}

	public SuperlayerSequence loadSuperlayerSequence(File dFile) throws VoogaException {
		if (!isValidFile(dFile)) {
			throw new VoogaException("Invalid file to load");
		}
		return (SuperlayerSequence) getObjectFromFile(dFile);
	}

	public File chooseSpriteFile(Stage stage) throws FileNotFoundException {
		FileChooser imageChooser = new FileChooser();
		File startDirectory = new File(getCustomSpriteDirectoryPath());
		imageChooser.setInitialDirectory(startDirectory);
		imageChooser.setTitle("Choose Sprite");
		imageChooser.getExtensionFilters().add(new ExtensionFilter("Sprite File", "*" + SPRITE_EXTENSION));
		File file = imageChooser.showOpenDialog(stage);
		return file;
	}

	private boolean isValidDirectory(File directory) {
		return directory.isDirectory() && !directory.getName().startsWith(".");
	}

	private boolean isValidFile(File in) {
		return in.exists() && !in.getName().startsWith(".");
	}

	public String getDialogSpriteDirectoryPath() {
		return projectPath + DIALOG_SPRITE_FOLDER;
	}

	public String getCutSceneSpriteDirectoryPath() {
		return projectPath + CUT_SCENE_SPRITE_FOLDER;
	}

	public String getInventoryTemplateSpriteDirectoryPath() {
		return projectPath + INVENTORY_TEMPLATE_SPRITE_FOLDER;
	}

	public String getDefaultSpriteDirectoryPath() {
		return projectPath + DEFAULT_SPRITE_FOLDER;
	}

	public String getInventorySpriteDirectoryPath() {
		return projectPath + INVENTORY_SPRITE_FOLDER;
	}

	public String getCustomSpriteDirectoryPath() {
		return projectPath + CUSTOM_SPRITE_FOLDER;
	}

	public String getInitializingWorldDirectoryPath(String worldName) {
		return projectPath + PROJECT_WORLD_PATH + worldName + "/";
	}

	public String getWorldDirectoryPath(int worldCount) {
		return projectPath + PROJECT_WORLD_PATH + worldCount + "/"; // TODO
	}

	public void saveWorld(MapDataConverter MDC, String path) {
		saveToFile(MDC, path + WORLD_EXTENSION);
	}

	private void removeExistingSave() {
		System.out.println("REMOVING previously saved: " + projectPath + PROJECT_WORLD_PATH);
		removeDirectory(projectPath + PROJECT_WORLD_PATH);
	}

	/**
	 * Entry point to save a world to data. Requires an authoring DraggableGrid
	 * (i.e., single map)
	 * 
	 * @param worldDraggableGrid
	 *            - the DraggableGrid (Map) from the authoring view to save
	 * @throws Exception
	 * @author Archana, Samuel
	 */
	public void saveWorlds(List<DraggableGrid> worldDraggableGrids) {
		String worldPath = projectPath + PROJECT_WORLD_PATH;
		removeExistingSave();
		makeDirectory(worldPath);
		worldDraggableGrids.forEach(world -> {
			String savePath = worldPath + world.getName();
			MapDataConverter MDC = new MapDataConverter(world, this);
			try {
				saveWorld(MDC, savePath);
			} catch (Exception e) {
				// Intentionally Blank
			}
		});

	}

	public List<DraggableGrid> loadWorldsFromWorldDirectory(String importProjectName) { // ONLY CALLED when importing
		myImportProjectPath = importProjectName;
		List<DraggableGrid> DG_LIST = new ArrayList<DraggableGrid>();
		String importFilePath = root + importProjectName + "/" + PROJECT_WORLD_PATH;
		System.out.println(importFilePath + " this is IMPORT FILE PATH");

		File worldDirFile = new File(importFilePath);
		try {
			DG_LIST = addToDraggableGridLoadingList(worldDirFile, DG_LIST, importProjectName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("all the IMPORTED draggable grids we return " + DG_LIST.size());
		return DG_LIST;
	}

	public List<DraggableGrid> addToDraggableGridLoadingList(File directory, List<DraggableGrid> currentDGList,
			String importName) throws Exception {
		if (directory.exists()) {
			System.out.println("World directory: " + directory.toString());
			for (File f : directory.listFiles()) {
				MapDataConverter MDC = (MapDataConverter) getObjectFromFile(f);
				DraggableGrid DG = MDC.createDraggableGrid();
				DG.setName(DG.getName() + " (imported)");
				currentDGList.add(DG);
			}
		}
		return currentDGList;
	}

	private List<AbstractSpriteObject> loadSpritesFromDirectory(File directory) throws Exception {

		if (!isValidDirectory(directory)) {
			throw new Exception("Not a directory");
		}
		File[] files = directory.listFiles();
		List<AbstractSpriteObject> ret = new ArrayList<AbstractSpriteObject>();
		for (File f : files) {
			try {
				AbstractSpriteObject dummy = loadSprite(f);
				ret.add(dummy);

				System.out.println(dummy);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * Entry point to load worlds from data
	 * 
	 * @return List<DraggableGrids>, i.e., the Authoring worlds
	 * @author Archana, Samuel
	 * @throws Exception
	 */
	public List<DraggableGrid> loadWorldsFromWorldDirectory() {
		List<DraggableGrid> DG_LIST = new ArrayList<DraggableGrid>();
		String worldDirectory = projectPath + PROJECT_WORLD_PATH;
		File worldDirFile = new File(worldDirectory);
		if (worldDirFile.exists()) {
			for (File f : worldDirFile.listFiles()) {
				MapDataConverter MDC = (MapDataConverter) getObjectFromFile(f);
				MDC.setGameDataHandler(this);
				DraggableGrid DG_toAdd = MDC.createDraggableGrid();

				DG_LIST.add(DG_toAdd);
			}
		}

		System.out.println("all the draggable grids we return " + DG_LIST.size());
		return DG_LIST;
	}

	public Map<String, List<AbstractSpriteObject>> loadSpritesFromNestedDirectories(String rootDirectory) {
		File file = new File(rootDirectory);
		;
		if (!isValidDirectory(file)) {
			return null;
		}
		;
		Map<String, List<AbstractSpriteObject>> ret = new HashMap<String, List<AbstractSpriteObject>>();
		File[] files = file.listFiles();
		for (File f : files) {
			if (isValidDirectory(f)) {
				try {
					List<AbstractSpriteObject> val = loadSpritesFromDirectory(f);
					String name = f.getName();
					ret.put(name, val);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	public List<SuperlayerSequence> loadSuperlayerSequencesFromDirectory(String folderToLoad) {
		List<SuperlayerSequence> ret = new ArrayList<SuperlayerSequence>();
		File file = new File(folderToLoad);
		if (file.listFiles() != null) {
			File[] files = file.listFiles();
			ret = new ArrayList<SuperlayerSequence>();
			for (File f : files) {
				ret.add(loadSuperlayerSequence(f));
			}
		}
		return ret;
	}

	public String getImportedInventorySpritesPath() {
		String path = "";
		if (myImportProjectPath != null) {
			path = root + myImportProjectPath + "/" + PROJECT_USER_SPRITE_PATH + INVENTORY_SPRITE_FOLDER;
		}
		return path;
	}

	public String getImportedSpritesPath() {
		String path = "";
		if (myImportProjectPath != null) {
			path = root + myImportProjectPath + "/" + PROJECT_USER_SPRITE_PATH + CUSTOM_SPRITE_FOLDER;
		}
		return path;
	}

	public void saveSuperlayerSequence(SuperlayerSequence dS, String folderToSaveTo) {
		saveToFile(dS, folderToSaveTo + DIALOG_EXTENSION);
	}

	/**
	 * Saves the image to the resources directory of the project under the file name 
	 * supplied.
	 * 
	 * @param image
	 * @param fileName
	 */
	public void saveTo(Image image, String fileName) {
		if (image == null)
			return;
		File loc = new File(projectPath + RESOURCES + fileName);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", loc);
		} catch (IOException e) {
			throw new VoogaException("IllegalFile", loc.getAbsolutePath());
		}
	}

	/**
	 * @return The working directory path.
	 */
	public String getRoot() {
		return root;
	}
}
