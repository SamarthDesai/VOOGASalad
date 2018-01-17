# Extra Use Cases
	
## Engine

1. Define an object to move forward 50 units (dependent on the “heading” variable) upon holding the “Up” key.
	* Map a KeyHeld Condition, which checks if “Up” is held, to a Move Action that takes in a Heading VectorOperation, which takes in a Get DoubleOperation, which takes in String “heading,” and another Get DoubleOperation, which takes in Double 50.
	* KeyHeld(1, “Up”) → Move(Heading(Get(“heading”), Get(50)))
2. Define an object to destroy another object tagged “rock” upon collision.
	* Map a Collision Condition, which takes in a String “rock,” to a Destroy Action, which takes in a Nearest GameObjectOperation, which takes in a String “rock.”
	* Collision(1, “rock”) → Destroy(Nearest(“rock”))
3. Define an object to double its “xSpeed” and “ySpeed” when the user holds down the “Shift” key.
	* Map a KeyHeld Condition, which checks if “Shift” is held, to a ChangeDouble Action, which takes in String “xSpeed” and a Product DoubleOperation, which takes in a Get DoubleOperation, which takes in Double 2, and another Get DoubleOperation, which takes in String “xSpeed,” and another ChangeDouble Action which does the same for “ySpeed.”
	* KeyHeld(1, “Shift”) → ChangeDouble(“xSpeed”, Product(Get(2), Get(“xSpeed”)), ChangeDouble(“ySpeed”, Product(Get(2), Get(“ySpeed”)))
4. Define an object to add an exclamation point (“!”) to its “catchPhrase” whenever the user presses “9.”
	* Map a KeyPressed Condition, which checks if “9” is pressed, to a ChangeString Action, which takes in String “catchPhrase” and a Concatenate StringOperation, which takes in a GetVar StringOperation, which takes in String “catchPhrase,” and a Get StringOperation, which takes in String “!”.
	* KeyPressed(1, “9”) → ChangeString(GetVar(“catchPhrase”), Get(“!”))
5. A user wants to simulate the effects of gravity on an object
	* This requires 2 actions. The first would be to call setVariable and pass it ySpeed, .1, and true, which increases the value of ySpeed by .1. The second would be to call Move and pass it a Vector that takes 0 and ySpeed as its x and y parameters. This creates a y-velocity with constant downward acceleration.
	* Code:
		* SetVariable(“ySpeed”, .1, true);
		* Move(DummyVector(0, “ySpeed”);
6. A user wants to display a message upon pressing A, but only when the player is colliding with a sign.
	* new And(new KeyPressed(“A”), new Collision(“Sign”))
		* This establishes the necessary conditions.
	* New Create(“MessageObjectName”)
		* The user will have the message as a gameobject, and the Create command allows you to display that message.
	* All that’s left is to add these into the player’s Condition/Action mapping.
7. A user wants to make a sprite change to the next image in its AnimationSequence
	* Simply create a ProgressSprite action in the Step event, and the sprite will change every step.
8. A user wants to move the player 5 units towards a point.
	* Use the Towards operation to create a Vector which can be used by move.
	* Putting Towards(EmptyVector(100, 200), 5) in the player class, creates a vector of magnitude 5, originating at the player, in the direction of (100, 200).
	* So the final code would be new Move(new Towards(new EmptyVector(100, 200), 5))
9. A user wants to add a “Death” condition for the player, destroying it when its hp is less than 1.
	* The condition would be LessThan(“Health”, 1). This returns true when the player’s health is less than 1.
	* The paired Action would be Destroy(new Nearest(“PlayerName”)). This destroys the nearest player, which is just...the player.
10. A new Operation is later added to the project
	* A new class would be written, extending the appropriate operation type, then the properties file giving the operations would be modified to include it, along with its parameter names. The OperationFactory would be reading from that file, so no other change would be required. 
11. A menu is displayed
	* The menu would be represented by a GameLayer, and each “button” would actually be GameObjects with conditions checking for mouse clicks. The actions associated with those conditions could bring up other layers representing other parts of the menu, or use basic actions like saving the game, or modify global variables as “settings.”

	## Authoring
12. A user wants to create a completely new custom sprite object
	* The user can click the button “Create sprite”, which will create a tab under which the user can select the image for the sprite, type its name and add string, double, or boolean parameters.
	* After this, the user can add different categories of parameters that they can create on their own. Then they can save their changes by clicking a save sprite button.The user wants to edit multiple sprites that have different parameters.
13. A user wants to create a new sprite based off an already created sprite (either default or one of their own)
	* The user can click the “Create sprite” button, and then the “import sprite” button and select a sprite from their files
	* Once the sprite is imported, all of its properties (image, parameters, actions, etc) will appear. The user can then edit or add anything they want.
14. A user wants to edit sprite objects that have different parameters.
	* The user selects the several sprite objects currently on the screen that they want to edit.
	* The sprite object manager class will use the sprite grid handler class to throw an error saying that two sprite objects of different types cannot be edited at the same time
15. A user wants to edit same sprite objects with some shared parameters.
	* The user selects the same sprite objects that they want to edit.
	* The shared parameters will show in state panel. Shared parameters with the same value will show the value. Shared parameters with different value will show a ‘-’. Parameters that are not shared by both sprites will not be displayed. 
	* If user wants to change value of a shared parameter, user can simply change the value, and the new value will apply to all selected sprites.
16. The user wants to create a main menu
	* The user will select the Panels tab. This will show the grid that is essentially a layer for the game. Buttons like ‘Save’, ‘Quit’, etc. will actually just be sprites that have actions under certain conditions. The user will create Sprites that look like buttons (their choice). They will change the parameters of the Sprite and add conditions and actions like normal. Thus the menu is made.
17. The user wants to fill an entire portion of the map with a specific type of sprite (say trees)
	* The user selects as many cells as they want on the grid, then selects a sprite on the side panel. The cells will then populate. This way the user does not have to drag and drop every sprite object individually.
18. The user wants to remove a sprite from the map
	* The user selects a sprite on the map, then selects the ‘delete’ button. The user can also select multiple sprites at a time and press delete and multiple will be removed.
19. The user wants to import maps/dialog/cutscenes from another project
	* The user can go to a respective tab (we will have one for map, one for dialog, etc), and can press ‘import’ on any tab, then select the appropriate files to upload into the current project
20. The user wants to import maps/dialog/cutscenes from another project
	* The user can go to a respective tab (we will have one for map, one for dialog, etc), and can press ‘import’ on any tab, then select the appropriate files to upload into the current project
21. The user wants to view all of the created sprites in a separate, more spacious view, than the map editor environment
	* The user can select the ‘Sprites’ tab and all of the created sprites will be shown in a grid view. User can put sprites into different categories if they wish. A user can select a sprite and see its image size and information about the sprite. Within this tab the user will NOT be able to edit a sprite, just view it.
22. The user wants to create a new map (level) and name it
	* Within the map editor tab, the user can select the top ‘Add map’ tab. Another map will appear on which any created sprites can be dragged onto. The user can then type in the ‘map name’ text area and once that is entered that map will be given that name, as well as its tab will be updated with the name.
23. The user wants to create a view to show inventory
	* The user will create a new panel. They will create Sprites that look like how they want inventory to be displayed. They will add an action to this sprite to get the inventory of the character. They will have a parameter to keep track of the coordinates beneath them. They will loop through the inventory. For each inventory object they will create a new sprite that looks identical and add that inventory to the sprite. They will do this until the inventory is empty or the bottom of the layer is soon to be reached. They will have a condition to check if the bottom of the layer is soon to be reached. If so, they will add a different sprite. When this sprite is clicked the layer will be replaced with a new layer that will continue to show inventory. Thus inventory can dynamically be shown. 
24. The user wants to see all imported maps for this project
	* The user will select ‘object editor’ in the main window.
	* On the top of the object editor, select tab that says ‘maps’ and in the maps tab, select ‘imported’ tab on the right. The user will be able to see all imported maps for this project.
25. The user wants to create a new dialogue.
	* The user goes to the dialogue tab, from which they can specify exactly what text they want and what order they want it to go in. They can specify for which sprite objects they want to associate each dialogue with. Then they can click the apply or save button.
26. The user wants to see all his dialogues (maybe hundreds) in a bigger, user-friendly window.
	* The user will select ‘object editor’ button from main window.
	* On the top of the object editor, select tab that says ‘dialogues’ and user will be able to see all dialogues created for this project, categorized by default, user (created), and imported (from another project).
27. The user wants to make an object turn around every time they encounter a wall.
	* The user goes to the actions and conditions tab. For the condition, the user selects a collision. For the action, the user selects a rotation.
	* The selects the kind of other sprite to satisfy the collision. It can be a specific sprite object wall on the map or it can apply to all wall sprite objects, which would already be saved.
	* The user specifies a 180 degree rotation upon collision with the wall. Then the user will click apply to save these changes for the sprite object. This sprite object can be a specific one on the map or an already made sprite object.
28. The user wants to add inventory to an object. 
	* The user will click on the sprite and choose the inventory tab. This lets user select other Sprite objects. These objects will now be part of the original Sprite object’s inventory. 
29. The user wants to add a cutscene to the game
	* The user goes to the cutscene tab, which changes the view to the right to be specific to cutscene creating operations. This involves conditions and actions that specify how the cutscene will be executed, along with several parameters for the items within the cutscene. These parameters would include duration for any images and text, the inclusion of a transition operation, and the ability to customize the cutscene background and size among other features.
30. The user wants to create a heads up display
	* To do this, in the authoring environment, the user selects the HUD subtab. From there, the user will have the option to add any sprites they’ve created to populate the HUD with global variables such as health, points, etc.. They also have the options to change the background and size of the HUD.
The user wants to create a custom panel
To do this, in the authoring environment, the user selects the custom subtab. Here, the user can add any parameters and actions/conditions they want to associate with the tab, making it flexible enough to include functionality as a Quest bar, mini-games, etc.
31. The user wants to add many types of inventory at once. 
	* They will go to the inventory panel. Without regard to layers for the actual game, they can create Sprite objects that are inventory. They can create as many as they want using the SpriteCreator environment. They can select a category for the inventory. The inventory object will save as a serialized data file.
32. The user wants to create a custom panel
	* To do this, in the authoring environment, the user selects the custom subtab. Here, the user can add any parameters and actions/conditions they want to associate with the tab, making it flexible enough to include functionality as a Quest bar, mini-games, etc.
33. The user wants to set how a sprite is animated. 
	* The user will click on the sprite and choose the animations tab. They can choose to make a new animation or modify an existing. The animations consist of a set of images. The animation will by default loop through each image in the engine on a set time interval.
34. The user wants to let the image of a Sprite change on the result of an action.
	* This is distinct from the animation sequence, since the user will use conditions and actions to change the image rather than an animation sequence. This will effectively be achieved by creating many animation sequences and having actions change the animation sequence dynamically. Obviously, each animation sequence can also have its own set of images if desired. 
35. The user wants to add categories to Sprite parameters. 
	* They will click the Sprite and choose the tab to add a new category. They can then create new parameters in this category. 
36. The user decides to change the map on which he/she wants to edit or add new sprite objects.
	* The user switches to another map, indicated as another tab, which he/she can save using the save button. The user can also load in a previously saved map and edit that.
37. The user wants to give a SpriteObject a condition containing a boolean operator
	* The UI will display two possible parameters for the boolean operator.
	* These parameters may be other operators, such as lessThan operator or the And/Or operator, or actual sprite parameters, such as health.
38. The user wants to give a SpriteObject an action
	* getCategories() is called to obtain a list of categories to display
	* getActions(String category) is called to obtain a list of actions to display
	* getParameters(String actionName) is called to obtain a list parameters that the user can input to the action.
	* getOperations(String operationType) is called to display operations that the user can choose from.
	* getParameters(String operationName) is called to display parameters that the user can input to the operation.

	## Player
38. A user wants to continue a game that has been previously played
	* All games that have been played or created have their corresponding files stored in the UserCreatedGames folder. To separate the original game files from the files corresponding to already played games, those files may be stored to a separate folder.
	* The GameController will pass the game name to the GameDataHandler, which will then call loadGame(). Since GameDataHandler has the game name, it thus has the folder containing the game file to load.
39. A user wants to save a game’s progress
	* A SpriteObject with a save action would need to be created in order for the game to support saving.
	* The engine will detect whenever the save action is triggered by a condition defined in the authoring environment.
	* Engine will call GameDataHandler.saveGame() to save the game into an XML file. 
40. A user wants to switch to a different game without stopping and restarting the program
	* A SpriteObject must be made that has an action that switches to the Game Selector UI.
	* Engine will detect whenever the action needs to be executed.
	* Engine will call SceneController.switchScene(GAME_SELECTOR_KEY) to change the UI to the game selector screen.

