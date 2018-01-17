# Use Cases

## Engine

1. A GameObject named “rock” is created with no associated actions. 
* The “rock” will take in the x y and image values from the front-end, which enable it to exist correctly in the world. But it will not do anything during the game step.
2. A GameObject named “Player” is created. It is given the Conditional KeyPress(Up) and has the action “move x: 0, y: 5” associated with that Conditional.  
* Player will be passed a List of Actions which only contains the “move” action. It then maps that action with the Conditional as its key.
3. The above “Player” is placed into the current world as the only object, and the Up Key is pressed. 
* Each Game Step (some arbitrary amount of time) the Controller will call a method on the current World which tells the World to make each GameObject (in this case only Player) execute the Actions associated with fulfilled Conditions. Player checks through its conditionals, and find that KeyPress(Up) is true, so it executes all the actions associated with that conditional, and thus moves up five pixels.
4. A door to a building is created.
	* The Door would be its own GameObject with a Collision(PlayerCharacter) Condition added. That conditional would have an associated action which tells the controller to change the current World from the “outside” map to the “inside” map.
5. The author wants to create a “Monster spawner”
	* The Author creates “spawner” object in the Authoring environment which is passed to the backend, with a variable “timer = 100”. For the conditional onEveryStep, there is a “timer -= 1” command. The spawner also has a “timer == 0” Condition, which has the associated actions “createObject(Monster)” and “timer = 100”. Thus, every 100 steps, a new Monster GameObject would be created.
6. The player dies
	* The GameObject representing player would have a health variable in its map. This would be checked against 0 by a LessThanOrEqualsCondition of the GameObject, and the associated action would switch Worlds to a game-over screen. 
7. A dialogue option is presented
	* The GameObject representing a choice would have an IfClicked Condition, and the associated Action would replace the old dialog box with a new one with different text based on the choice. These GameObjects could be overlayed on top of the rest of the dialog if desired. 
8. A Condition checks for a collision
	* When the Condition, in this case Collision, is checked, it gets the relevant GameObjects from the World based on tags, and uses a utility to check the passed GameObject against each of those objects. From there, it can add the objects that are colliding with it to a list stored in the object so that later actions can find them. This list will be cleared every game step. 
9. A chest generates a weapon with random stats
	* The chest would be represented by a GameObject. The first time its inventory is accessed (probably from a mouseclick) the GameObject would execute an Action changing the properties of its stored weapon GameObject before making it available. This would involve the user creating a variable to indicate whether the chest has been clicked, then having an IfAction to change the variables of the weapon.
10. A player steps on lava
	* The GameObject representing a player moves onto a position occupied by a GameObject representing lava. The Condition associated with a player object standing on a lava object (likely a Collision between player and lava) returns true, and the Actions to which it maps execute (e.g. Actions decrement player GameObject’s health variable by one every second).
11. The user pauses the game
	* A Condition that checks whether the game is paused (represented by a global boolean variable) every game step returns true, and the Controller switches the active World to a World representing the pause screen with different GameObjects that represent pause option buttons that react to clicks.
12. User adds a “pet” GameObject that follows the player GameObject everywhere it moves
	* When a Condition that checks whether a player GameObject has moved returns true, the “pet” GameObject executes a Follow Action that takes in a GameObject tagged as a player from World and sets the “pet’s” position equal to a location directly behind the player GameObject (according to its heading) and sets the “pet’s” heading equal to the “player’s” heading.
13. The player object puts on shoes that leave a trail wherever it moves
	* A Condition that checks whether the player is wearing trail-leaving shoes returns true, and when the player object moves a createObject(FootStep) Action is called that creates a new GameObject representing a footstep in the previous location of the player object.
14. The user pans the camera to the right by pressing the ‘R’ key
	* The RKeyPressed Condition returns true, and the camera GameObject increments its x_minimum and x_maximum variables.



## Authoring
15. User drags a tree object onto the map
    * New instance of Tree object is created and its location is updated with row/col on map
16. User requests to view the properties of a sprite object on the screen
    * External backend API method is called that gets the property information from the 
engine to display on the screen, of the form of an arraylist of sprite parameters
17. User edits properties of a default game sprite
    * Properties are edited via UI and “apply” button is pressed, which makes a call to the backend update() method for that object which will update any changed properties
18. User creates new sprite
    * New window displays in the front end- populated with default editable parameters from 
the backend. User fills out object properties and presses ‘create.’ A list of parameters is sent to the backend & a new object is created (generic subclass DummyObject)
19. User saves default sprite that he/she significantly edited
    * The properties of this sprite are saved and made into a new spriteObject class, which 
gets stored in mySprites, a list of current spriteObjects the user has chosen to save for 
possible reuse
20. User selects multiple sprites and edit a property
    * Backend checks sprites to see if they are of the same TYPE. If so, that object type 
properties will display in tab bar to edit. If not, error message will pop up telling the user 
to only select objects of same type.
21. User decides to make an event for an object given that an object’s state reaches a specific value
    * The user goes into the object’s properties and selects “add action”. They will be prompted to choose the state(s) they are conditioning on and what they will have to match in order to trigger that action. This action and condition pair will be passed to the backend via a passActionCondition method in front end external API
22. User drags a new instance of a personalized sprite they created onto the map
    * Front end will update ‘mysprites’ tab on the top of the map UI when a user creates a new
sprite. The image of that sprite will appear and can be dragged onto the map, which will 
create a new instance in the backend with its specified parameters.
23. User deletes object from map
    * User selects object and then selects the ‘trashcan.’ ImageView disappears & the Front 
end calls backend remove(row, col) method which removes object from grid array.
24.  User starts to edit a spriteObject, but does not click apply
    * The changes are not saved for that spriteObject, and no further action is done.
25. User places two objects on top of each other
    * Depending on what kind of sprite object it is, the user will could be thrown an error message if he/she places a terrain object on top of a player, NPC, or enemy object. However, placing animate objects on top of terrain and is perfectly legal and encouraged. Each grid square will retain all of the objects on top of it, and the one most recently added will be shown.
26. User clicks the save configuration button
    * The game will be saved into an XML file, with all of the objects and maps’ current states being saved along with it. Each component will be serialized into its subcomponents.
27. User clicks the load configuration button
    * The simulation will be loaded using the XML and game builder classes. There will be several XML files from which the game will be composed.
28. User clicks the button to open multiple games.
    * A second window will reappear, from which they can start a completely new game starting from the load/splash screen. Then they can choose their theme and so on.

 
	

	
	


## Game Player

29. The person playing the game presses the Left Arrow key
    * View has an instance of a controller class and calls passes the key input by calling controller.handleKeyInput(KeyCode). The controller passes this key input to the game engine by calling EngineController.handleKeyInput(KeyCode). The game engine processes this key input.
30. The game is initially booted but not yet displayed
    * The game engine will pass all (immutable) objects that need to be displayed to the controller. The controller will pass the objects to the View. The view will create the front-end objects (as nodes) and add them to the scene.
31. An object (ex. boulder) changes position
    * The game engine will pass an immutable boulder containing its updated states. The controller will pass this object to the View which searches through its object map and finds the key value equal to the immutable object’s ID. View calls myBoulder.update(ImmutableBoulder boulder). The front-end boulder sets its states equal to the states of the ImmutableBoulder.
32. The user wants to exit out of the game
    * Game author will define an exit key/mouse click. This key/click (just like any user input) will be passed to the controller which passes it to the game engine. The game engine processes the input and determines that the game will be exited. Game engine tells controller to end the game, and the screen is switched to the game list menu.

## Welcome Screen
33. Selecting “Play”
    * Upon selecting play in the Welcome Screen, the main menu will create an instance of the Play Menu. This menu would contain a list of games that have been created, and within each game the option to start a new game or continue if there is an existing game.
34. Selecting “Create”
    * Upon selecting create, the main menu will create an instance of the authoring environment.
35. Selecting “Learn”
    * Upon selecting learn, the main menu will create an instance of the Instructions Screen.
36. Selecting “Settings”
    * Upon selecting settings, the main menu will create an instance of the Settings Screen, which allows users to set their save/load file directory among other features.

## Data

37. The person playing the game wants to “save” their game.
    * When the “save” command is input, the GamePlayer passes data the Backend’s Controller, which is then serialized. Since the backend contains the entire current game state. It simply has to be deserialized when “load” is called, and all the relevant data will be there, so it could be run as though there were no interuption. 
38. The Front-end creates a new GameObject which isn’t added to the world.
    * When a new GameObject is created and the author hits “OK”, the Data will automatically serialize that GameObject and keep it available for access on the backend.
