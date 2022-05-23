/*
 * InputController.java
 *
 * This class buffers in input from the devices and converts it into its
 * semantic meaning. If your game had an option that allows the player to
 * remap the control keys, you would store this information in this class.
 * That way, the main GameMode does not have to keep track of the current
 * key mapping.
 *
 * This class is NOT a singleton. Each input device is its own instance,
 * and you may have multiple input devices attached to the game.
 *
 * Author: Walker M. White
 * Based on original GameX Ship Demo by Rama C. Hoetzlein, 2002
 * LibGDX version, 1/16/2015
 */
package edu.cornell.gdiac.shipdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.*;
import edu.cornell.gdiac.util.*;
import com.badlogic.gdx.controllers.Controller;

/**
 * Device-independent input manager.
 *
 * This class supports both a keyboard and an X-Box controller.  Each player is
 * assigned an ID.  When the class is created, we check to see if there is a 
 * controller for that ID.  If so, we use the controller.  Otherwise, we default
 * the the keyboard.
 */
public class InputController {
	
    /** Player id, to identify which keys map to this player */
	public int player;

	public int upExt;

	public int leftExt;

	public int downExt;

	public int rightExt;

	public int enterExt;

	public int unselectExt;

	public int endTurnExt;
	/**
	 * Creates a new input controller for the specified player.
	 * 
	 * The game supports two players working against each other in hot seat mode. 
	 * We need a separate input controller for each player. In keyboard, this is 
	 * WASD vs. Arrow keys.  We also support multiple X-Box game controllers.
	 * 
	 * @param id Player id number (0..4)
	 */
	public InputController(int id) {
		player = id;
	}

	public int leftRight() {
		return rightExt - leftExt;
	}

	/**
	 * Reads the input for this player and converts the result into game logic.
	 *
	 * This is an example of polling input.  Instead of registering a listener,
	 * we ask the controller about its current state.  When the game is running,
	 * it is typically best to poll input instead of using listeners.  Listeners
	 * are more appropriate for menus and buttons (like the loading screen). 
	 */
	public void readInput() {
		int up, left, right, down, confirm, unselect, endTurn;
		up    = Input.Keys.UP; 
		down  = Input.Keys.DOWN;
		left  = Input.Keys.LEFT; 
		right = Input.Keys.RIGHT;
		confirm = Input.Keys.ENTER;
		unselect = Input.Keys.ESCAPE;
		endTurn = Input.Keys.X;
		
		upExt = 0;
		downExt = 0;
		leftExt = 0;
		rightExt = 0;
		enterExt = 0;
		unselectExt = 0;
		endTurnExt = 0;

		// Movement forward/backward
		if (Gdx.input.isKeyJustPressed(up)) {
			upExt = 1;
		} else if (Gdx.input.isKeyJustPressed(down)) {
			downExt = 1;
		}
		
		// Movement left/right
		if (Gdx.input.isKeyJustPressed(left)) {
			leftExt = 1;
		} else if (Gdx.input.isKeyJustPressed(right)) {
			rightExt = 1;
		}

		// Shooting
		if (Gdx.input.isKeyJustPressed(confirm)) {
			enterExt = 1;
		} else if (Gdx.input.isKeyJustPressed(unselect)) {
			unselectExt = 1;
		}	
		
		if (Gdx.input.isKeyJustPressed(endTurn)) {
			endTurnExt = 1;
		}
    }
}