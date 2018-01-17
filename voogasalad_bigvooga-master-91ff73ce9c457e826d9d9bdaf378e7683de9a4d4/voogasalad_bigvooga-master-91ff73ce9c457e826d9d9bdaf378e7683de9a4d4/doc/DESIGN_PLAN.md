# Design Plan

## Introduction

* Game genre - RPG *

Our team's goal is to make a game development engine for RPG games that offers the flexibility for users to create their own custom maps, conditions, and objects. The authoring environment will allow the user to create many navigable maps that contain game objects. Users can define objects and their behaviors by assigning them variables and lists of built-in actions for each chosen built-in condition, save these objects for future use, and place them on the map. Users can also define global variables within the game to measure overall game state and progress.

RPGs require a great amount of flexibility in the player's ability to make decisions and deviate from any one specific timeline/gameflow. We believe that our condition-action structure of objects and our use of global variables allow the user to have the freedom to restrict or permit the player to making specific decisions at any point in the game.

We hope that our design will offer the flexibility for users to make extremely customizable games with the power to define objects and goals, similar to how users can define their own commands and variables in SLogo.

## Overview

The program is divided into four broad parts: authoring, engine, player, and data.  The authoring subsection provides a game designer a user interface to create a custom game and environment.  This authoring environment allows a game designer to add in all game objects, including nonplayer characters (NPCs) and player characters (PCs), in a game as well as give these objects different functionality (actions) which is dependent on conditions.

The engine encapsulates all the logic of the defined game.  The engine contains all the information about a game; its game objects and all their associated possible actions and states.  As the player plays a game (that has already been created), the engine performs all the logic and updates the state of the game. The Engine is primarily accessed through the EngineController, but each component must be instantiated and injected into the other components by an outside source as the game is being written.

The player allows a user to actually play a game that has already been created.  The player may give the user a choice of different predefined games, then the user can choose what game they want to play.  Then, the player will interface with the engine via listeners in the EngineController in order to constantly update the game UI based on their actions. The a controller class will contain instances of the game engine and player in order to communicate user interactions and any changes in game object.

The data section is data resources and files that will store raw data for games for saving and reloading.


## User Interface

### Welcome screen:

When the program is initially run, a welcome screen will appear and display icons that the user may click on (play, create, learn, settings). When the user selects the Play icon, the program will display a list of games from which the user can choose a game to play. If the user selects a game, the game will run. When the user selects the Create icon, the user will be taken to the game authoring environment. When the user selects the Learn icon, instructions will be displayed about how to use the program's features. Selecting the Settings icon, will display settings that the user can adjust.

Game Authoring Environment:

The game authoring environment will consist of a left side tab with menu options and item properties, a top tab with defaultSprites/mySprites tabs and their imageviews below, and a map display (images of UI in doc folder). This map display will be a grid of customizable size, with each square being able to be occupied by one or more object. One can view and edit the properties of objects by clicking them. He/she can add them to the gridpane by dragging and dropping them either onto a square, which it will snap into, and can remove them from the gridpane by dragging the object into the trashcan. 



## Design Details

### Authoring

The authoring environment is split into two components: frontend and backend. The frontend is the driving force behind creating, editing, and placing sprite objects onto the screen. Different windows with checkboxes, dropdowns menus, and text boxes (only for typing in names of things) will be utilized to make the optimal UI experience for the user. There will be a default choice of sprite objects that the user can choose from to make their game as building blocks for developing their game. The backend is responsible for passing these updates in the front end to the engine. For example, if the user adds or edits the properties for a particular sprite object or for an entire class of sprite objects, the engine needs to be aware of this so that they can run the game with these new features. 

### Engine

The engine's structure is divided into the following submodules:

* EngineController: handles basic logic for the engine -- time-steps, looping over objects, sending data to the Player, etc.
	* On each game step, the controller loops over the objects in the world, then calls step on each of them. At the end of this process, it notifies listeners (the Player) that the image changed. Stores any global game variables. 
* World -- Stores the GameObjects and represents a single level/screen in the game. 
	*  An Action can change Worlds
* GameObject -- A component in the user-created game. Stores variables and behavior specific to a game entity. 
	* When step is called, it will check each condition it stores and execute any Actions to which the true conditions map. 
	
* Operation -- An Object designed to return a specific data type
	* Examples of Operations include things like KeyPressed("Left"), or Sum(2, 2). They take in values an return a new value. They can also be nested, such as Sum(Sin(2), 2).
* Condition -- A Predicate that is linked to some list of actions
	*Conditions have a Boolean Operation composed into them, and a priority number. Each GameObject evaluates its Conditions in order of priority. The condition simply returns the case of its Boolean Operation.
* Action -- A general representation of any change in the game state
	*Action is primarily focused on the execution of a single command. Actions will take in an Operation and use the value given by that Operation to effect some change on the game state. For example, Move() would be passed a Vector Operation like "TowardsPoint" or "InDirection". So the full command might look like Move(TowardsPoint(5, 6, 7)), which moves it 7 units towards the point (5,6).

### Player

The Player essentially takes the role of the View in the MVC design pattern. Thus, the controller will act as a bridge between the EngineController (Model) and the Player (View). The primary inputs from the user that need to be communicated to the controller are key/mouse inputs, buttons, and other front-end components that handle user-triggered events. For example, when a key is pressed, the keycode gets passed to controller and then to model. Model will process that keycode and call any appropriate methods on the controller. This gets communicated to the View which may take in an immutable game object and update the view object accordingly. This would require a "delta" class approach that detects changes of game objects. Also, objects would need to have unique IDs in order to map back-end objects to front-end objects accurately.

## Example games

### Legend of Zelda

Legend of Zelda is not grid based, has an inventory that progresses as the game continues, has several stats with specific displays associated with them, and has relatively complex maps with NPCs and enemies. It is also not turn-based, and relies heavily on collisions. Cutscenes are also prevalent in these games. 

The complex worlds can be broken up (as the actual game does) into separate maps, which would be instances of World. Specific GameObjects would represent the entrances to new maps, and collisions would send the player to the new map. 

The User could make a template for how NPCs interact with other GameObjects, then modify these slightly to make different NPCs by changing the image, speed, size, etc. 

Because the menus/displays are not hardcoded into the engine, there is enough flexibility to create almost any menu. The added difficulty of making them from scratch can be alleviated by example objects that can be modified/tweaked in authoring.

### Pokemon

Pokemon is grid-based, has a complex inventory that stores elements with stats (Pokemon), and has complicated menus. There are many interactions between the player and the environment (other GameObjects). 

The most difficult aspect of this game is the inventory. It is crucial that a list of GameObjects be stored and accessible so that each pokemon can be fully represented within a menu -- which is part of why we added that to our API. Similarly, items could have complex properties, and need to be storable within an inventory. 

Surprisingly, this may be a better test of our collision detection than the seemingly more complex Legend of Zelda games. In some cases, collisions occur differently based on how you approach an object, and there are more possible collisions due to grass and line-of-sight NPCs. Most of this will be handled by keeping our code flexible enough to allow the user to set these properties. For example, to check if you are approaching from the left or right during a collision, one could just use the x and y coordinates of the objects and the predefined IfAction, LessThan, etc to choose what happens. 

### Undertale

This game includes many cutscenes, minigames that are unusual to RPGs, and a ton of dialogue. 

This game tests how flexible our display is, since parts of it do not follow the conventional displays of most 2D RPGs. Smoothly handling cutscenes will be crucial, which in our case just means making animations flexible enough to nearly stand on their own. It becomes crucial with games of this scale, with this much custom dialogue, to make it easy to present text and menus. The biggest part of this will be making GameObjects easily reusable/clonable by users in the authoring environment. 

## Design Considerations

*Permanency of Objects*: When transitioning from one world to another, certain characteristics (like HP and level and items) should be maintained.

*Events vs Game Steps*: Originally, we had it so that events were global objects like LeftKeyPress. Each Event had a list of actions, and those actions had GameObjects which they associated with. So when the left key was pressed, LeftKeyPress called its actions, which in turn operated on all of their relevant GameObjects. This turned out to be incredibly suboptimal because we had no way of establishing unique order of actions (i.e., one object wants to turn right and then move, while the other object wants to move and then turn right). So now, actions and action orders are kept within each GameObject, instead of vice-versa, and we have Conditions, which are tested for each object on each step of the game.

*Saving current state of World*: Let's say that we finish killing everything in a dungeon, and we leave the dungeon. The saved state of that world should be updated so that the game knows everything is dead. But if you start a new game, you want that game to have its own saved state, because things wouldn't be dead in the new game. One option would be to give each save a name, and load up the files associated with the name. If there is not associated world, load the default files.

*Responsibilities of Authoring vs Engine*: Certain actions/conditions could be built into the engine that are essentially compositions of other actions/conditions to simplify the structure of the authoring code. The question is how much of that should be the responsibility of the engine, and how much should be handled by the authoring environment. For example, if the user wants to make a game where everything snaps to a grid, the authoring environment should support that, but should the responsibility for interpreting that be in the authoring environment (converting everything to pixel measurements) or in engine (working with GameObject motion on a grid less dense than pixels)?
