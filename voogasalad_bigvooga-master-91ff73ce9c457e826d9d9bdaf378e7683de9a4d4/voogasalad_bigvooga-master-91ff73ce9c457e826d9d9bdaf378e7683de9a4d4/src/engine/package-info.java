package engine;

/**
 * This package contains the major classes which are used in the engine.
 * Sub-packages like Actions and Conditions contain utilities which are used for
 * specific purposes.
 * 
 * The engine framework depends on the Controller->World->Layer->Object
 * hierarchy. Objects populate layers, which populate worlds, which populate the
 * controller. Objects contain a mapping of conditions and actions which govern
 * their behavior in a way that models a stimulus-response scenario (or just
 * if-then conditionals).
 * 
 * There are several assumptions applied throughout the engine that should be
 * mentioned:
 * 
 * 1. Positions are given as the center of the object.
 * 
 * 2. All absolute distances are given in pixels.
 * 
 * 3. Relative positions are given on coordinates from -.5 to .5, so that the
 * top left of an object would be (-.5, -.5). This makes sense since objects are
 * centered.
 * 
 * 4. Relative sizes are given as the width of the object divided by the width
 * of the parent.
 * 
 * 5. The location of a Displayable is the location it should be displayed, not
 * necessarily the position of the object it represents. As such, these
 * positions are often only changed when they are accessed.
 * 
 * 6. Everything in the engine is serializable except for two things in master:
 * the timeline, and the playermanager. As such, the entire engine can be saved
 * so long as it is first stopped and the playermanager is set to null. 
 * 
 */