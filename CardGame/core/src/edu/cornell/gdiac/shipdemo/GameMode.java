/*
 * GameMode.java
 *
 * This is the primary class file for running the game.  You should study this file for
 * ideas on how to structure your own root class. This class follows a 
 * model-view-controller pattern fairly strictly.
 *
 * Author: Walker M. White
 * Based on original GameX Ship Demo by Rama C. Hoetzlein, 2002
 * LibGDX version, 1/16/2015
 */
package edu.cornell.gdiac.shipdemo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import org.w3c.dom.css.ElementCSSInlineStyle;

import edu.cornell.gdiac.assets.AssetDirectory;
import edu.cornell.gdiac.util.FilmStrip;

import java.util.ArrayList;

import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.*;

/**
 * The primary controller class for the game.
 *
 * While GDXRoot is the root class, it delegates all of the work to the player mode
 * classes. This is the player mode class for running the game. In initializes all 
 * of the other classes in the game and hooks them together.  It also provides the
 * basic game loop (update-draw).
 */
public class GameMode implements ModeController {
	/** Max Cards in play per player */
	private static final int FIELD_SIZE = 10;
	/** Max Cards in hand per player */
	private static final int HAND_SIZE = 10;
	
	/** The background image for the battle */
	private Texture background;

	private TextureRegion backTexture;
	private ArrayList<TextureRegion> robotTextures = new ArrayList<>();
	private TextureRegion robot1;
	private TextureRegion robot2;
	private TextureRegion robot3;
	private TextureRegion robot4;
	private TextureRegion robot5;
	private TextureRegion robot6;
	private TextureRegion robot7;


	// Instance variables
	/** Read input for blue player from keyboard or game pad (CONTROLLER CLASS) */
	protected InputController PlayerController;

	/** Blue Deck */
	protected ArrayList<CardModel> BlueDeck = new ArrayList<>();
	/** Blue Hand */
	protected ArrayList<CardModel> BlueHand = new ArrayList<>();
	/** Blue field */
	protected ArrayList<CardModel> BlueField;
	/** Blue Electricity */
	private int BlueElectricity;
	/** BlueUsedElectricity */
	private int BlueUsedElectricity;
	/** Blue Metal */
	private int BlueMetal;
	/** BlueHealth */
	private int BlueHealth;

	/** Red Deck */
	protected ArrayList<CardModel> RedDeck = new ArrayList<>();
	/** Red Hand (MODEL CLASS) */
	protected ArrayList<CardModel> RedHand = new ArrayList<>();
	/** Red field */
	protected ArrayList<CardModel> RedField;
	/** Red Electricity */
	private int RedElectricity;
	/** RedUsedElectricity */
	private int RedUsedElectricity;
	/** Red Metal */
	private int RedMetal;
	/** RedHealth */
	private int RedHealth;

	protected CardModel cardTest;


	/** CurrentTurn */
	private boolean isRedTurn;
	/** StartTurn */
	private boolean StartTurn;
	/** TurnHasEnded */
	private boolean TurnHasEnded;

	private boolean CardPlayed;
	private boolean AttackStarted;
	private boolean DefenseDeclared;

	private int DeclaredAttacking;
	private int DeclaredDefending;

	private boolean redWon;
	private boolean blueWon;

	/** Current */
	private int cur;

	/** Metal Gain Rate */
	private int MetalGain;
	/** ElectricityGain */
	private int ElectricityGain;

	/** Store the bounds to enforce the playing region */	
	private Rectangle bounds;

	private ArrayList<JsonValue> blueCardJsons = new ArrayList<>();

	private ArrayList<JsonValue> redCardJsons = new ArrayList<>();

	/**
	 * Creates a new game with a playing field of the given size.
	 *
	 * This constructor initializes the models and controllers for the game.  The
	 * view has already been initialized by the root class.
	 *
	 * @param width 	The width of the game window
	 * @param height 	The height of the game window
	 * @param assets	The asset directory containing all the loaded assets
	 */
	public GameMode(float width, float height, AssetDirectory assets) {
		// Extract the assets from the asset directory.  All images are textures.
		background = assets.getEntry("background", Texture.class );

		backTexture = new TextureRegion(assets.getEntry("card",Texture.class));
		robot1 = new TextureRegion(assets.getEntry("r1",Texture.class));
		robot2 = new TextureRegion(assets.getEntry("r2",Texture.class));
		robot3 = new TextureRegion(assets.getEntry("r3",Texture.class));
		robot4 = new TextureRegion(assets.getEntry("r4",Texture.class));
		robot5 = new TextureRegion(assets.getEntry("r5",Texture.class));
		robot6 = new TextureRegion(assets.getEntry("r6",Texture.class));
		robotTextures.add(robot1);
		robotTextures.add(robot2);
		robotTextures.add(robot3);
		robotTextures.add(robot4);
		robotTextures.add(robot5);
		robotTextures.add(robot6);



//		for (int i = 0; i<30; i++){
//			String filename = "deck1/cBlue"+i+".json";
//			JsonValue temp = assets.get(filename, JsonValue.class);
//			blueCardJsons.add(temp);
//		}
//		for (int i = 0; i<30; i++){
//			String filename = "deck1/cRed"+i+".json";
//			JsonValue temp = assets.get(filename, JsonValue.class);
//			redCardJsons.add(temp);
//		}

		// Initialize Test Card
//		cardTest = new CardModel(500, 500, 90, 5,5,5,5, "Reach", backTexture, backTexture);

		for (int i = 0; i<15; i++){
			CardModel nCard = new CardModel(150+(i*2), 200+(i*2), 270,
					5,5,5,5, "Reach", backTexture, backTexture);
			BlueDeck.add(nCard);
		}
		for (int i = 0; i<5; i++){
				TextureRegion tempFront = robotTextures.get(i%robotTextures.size());
			CardModel nCard = new CardModel(500+(i*250), 300, 270,
					5,5,5,5, "Reach", tempFront, backTexture);
			nCard.setFaceUp(true);
			BlueHand.add(nCard);
		}

		for (int i = 0; i<15; i++){
			CardModel nCard = new CardModel(1500-(i*2), 780-(i*2), 90,
					5,5,5,5, "Reach", backTexture, backTexture);
			RedDeck.add(nCard);
		}
		for (int i = 0; i<5; i++){
			TextureRegion tempFront = robotTextures.get(i%robotTextures.size());
			CardModel nCard = new CardModel(200+(i*250), 700, 90,
					5,5,5,5, "Reach", tempFront, backTexture);
			nCard.setFaceUp(true);
			RedHand.add(nCard);
		}



//		TODO: WE NEED TO MAKE THIS WORK
//		RedDeck = initRedDeck();
//		BlueDeck = initBlueDeck();
//
//		RedHand = draw(5, RedDeck);
//		BlueDeck = draw(5, BlueDeck);

		//Initalize Metal and Electricity
		BlueElectricity = 50;
		RedElectricity = 50;
		BlueUsedElectricity = 0;
		RedUsedElectricity = 0;

		BlueMetal = 50;
		RedMetal = 50;

		RedHealth = 10;
		BlueHealth = 10;

		isRedTurn = true;
		StartTurn = true;
		TurnHasEnded = false;
		cur = 0;

		CardPlayed = false;
		AttackStarted = false;
		DefenseDeclared = false;

		DeclaredAttacking = -1;
		DeclaredDefending = -1;

		MetalGain = 50;
		ElectricityGain = 50;

		redWon = false;
		blueWon = false;

		bounds = new Rectangle(0,0,width,height);

		// Create the input controllers.
		PlayerController  = new InputController(0);
	}

	/** 
	 * Read user input, calculate physics, and update the models.
	 *
	 * This method is HALF of the basic game loop.  Every graphics frame 
	 * calls the method update() and the method draw().  The method update()
	 * contains all of the calculations for updating the world, such as
	 * checking for collisions, gathering input, and playing audio.  It
	 * should not contain any calls for drawing to the screen.
	 */
	@Override
	public void update() {
		//Stages, Play Card -> Attack -> Defend -> End Turn
		// Read the keyboard for each controller.
		
		//Start of Turn
		if(isRedTurn && StartTurn) {
			RedMetal += MetalGain;
			RedElectricity += ElectricityGain;
//			RedHand.add(drawCard(1, RedDeck).get(0));
			StartTurn = false;
		} else if (!isRedTurn && StartTurn) {
			//More Balanced for Blue the metal gain rate increases on their turn
			//These cause metal and electricity gain rates to increase over time
			//Balance question
			MetalGain += 50;
			ElectricityGain += 0;
			BlueMetal += MetalGain;
			BlueElectricity += ElectricityGain;
//			BlueHand.add(drawCard(1, BlueDeck).get(0));
			StartTurn = false;
		}

		PlayerController.readInput();
		cur += PlayerController.leftRight();

		//Play Card
		if(!CardPlayed) {
			//Skip playing a card
			if(PlayerController.endTurnExt == 1) {
				CardPlayed = true;
			} else {
				if(isRedTurn) {
					if(PlayerController.enterExt == 1) {
						playCardRed(cur);
						cur = 0;
					}
					//set end of turn here if red ends turn
				} else if (!isRedTurn) {
					if(PlayerController.enterExt == 1) {
						playCardBlue(cur);
						cur = 0;
					}
				}
			}
		}

		//Attack
		if(CardPlayed && !AttackStarted) {
			//No Attack
			if(PlayerController.endTurnExt == 1) {
				TurnHasEnded = true;
			} else {
				if(isRedTurn) {
					if(PlayerController.enterExt == 1) {
						attackCardRed(cur);
						cur = 0;
					}	
				} else if (!isRedTurn) {
					if(PlayerController.enterExt == 1) {
						attackCardBlue(cur);
						cur = 0;
					}	
				}
			}
		}

		//Defend
		//Red and blue are flipped here because the defender has initative despite others turns
		if(AttackStarted && !DefenseDeclared) {
			//No Defender
			if(PlayerController.endTurnExt == 1) {
				DefenseDeclared = true;
				DeclaredDefending = -1;
			} else {
				if(isRedTurn) {
					if(PlayerController.enterExt == 1) {
						defendCardBlue(cur);
						cur = 0;
					}	
				} else if (!isRedTurn) {
					if(PlayerController.enterExt == 1) {
						defendCardRed(cur);
						cur = 0;
					}	
				}
			}
		}

		//Resolve the combat
		if(DefenseDeclared == true) {
			if(isRedTurn) {
				if(DeclaredDefending  == -1) {
					BlueHealth -= RedField.get(DeclaredAttacking).dmg;
				} else {
					CardModel attacker = RedField.get(DeclaredAttacking);
					CardModel defender = BlueField.get(DeclaredDefending);

					attacker.health = attacker.health - defender.dmg;
					defender.health = defender.health - attacker.dmg;

					if(attacker.health <= 0) {
						RedUsedElectricity -= RedField.get(DeclaredAttacking).electricity;
						RedField.remove(DeclaredAttacking);
					} else {
						BlueHealth -= attacker.dmg;
						if(BlueHealth <= 0) {
							redWon = true;
						}
					}
					if(defender.health <= 0) {
						BlueUsedElectricity -= BlueField.get(DeclaredDefending).electricity;
						BlueField.remove(DeclaredDefending);
					}
				}
			} else if (!isRedTurn) {
				if(DeclaredDefending  == -1) {
					BlueHealth -= RedField.get(DeclaredAttacking).dmg;
				} else {
					CardModel attacker = BlueField.get(DeclaredAttacking);
					CardModel defender = RedField.get(DeclaredDefending);

					attacker.health = attacker.health - defender.dmg;
					defender.health = defender.health - attacker.dmg;

					if(attacker.health <= 0) {
						BlueUsedElectricity -= BlueField.get(DeclaredAttacking).electricity;
						BlueField.remove(DeclaredAttacking);
					} else {
						RedHealth -= attacker.dmg;
						if(RedHealth <= 0) {
							blueWon = true;
						}
					}
					if(defender.health <= 0) {
						RedUsedElectricity -= RedField.get(DeclaredDefending).electricity;
						RedField.remove(DeclaredDefending);
					}
				}
			}
			TurnHasEnded = true;
		}

		// end of turn
		if(TurnHasEnded) {
			DeclaredAttacking = -1;
			DeclaredDefending = -1;
			CardPlayed = false;
			AttackStarted = false;
			DefenseDeclared = false;
			TurnHasEnded = false;
			isRedTurn = !isRedTurn;
			StartTurn = true;
		}
//		draw(canvas);
	}

	/**
	 * Draw the game on the provided GameCanvas
	 *
	 * There should be no code in this method that alters the game state.  All 
	 * assignments should be to local variables or cache fields only.
	 *
	 * @param canvas The drawing context
	 */
	@Override
	public void draw(GameCanvas canvas) {
		canvas.drawOverlay(background, true);
//		cardTest.drawCard(canvas);
		for(int i = 0; i<BlueDeck.size(); i++){
			BlueDeck.get(i).drawCard(canvas);
		}
		for(int i = 0; i<BlueHand.size(); i++){
			BlueHand.get(i).drawCard(canvas,i==cur&&!isRedTurn);
		}

		for(int i = 0; i<RedDeck.size(); i++){
			RedDeck.get(i).drawCard(canvas);
		}
		for(int i = 0; i<RedHand.size(); i++){
			RedHand.get(i).drawCard(canvas,i==cur&&isRedTurn);
		}


		canvas.setBlendState(GameCanvas.BlendState.ADDITIVE);
		canvas.setBlendState(GameCanvas.BlendState.ALPHA_BLEND);
	}

	/**
	 * Dispose of all (non-static) resources allocated to this mode.
	 */
	public void dispose() {
		// Garbage collection here is sufficient.  Nothing to do
	}
	
	/**
	 * Resize the window for this player mode to the given dimensions.
	 *
	 * This method is not guaranteed to be called when the player mode
	 * starts.  If the window size is important to the player mode, then
	 * these values should be passed to the constructor at start.
	 *
	 * @param width The width of the game window
	 * @param height The height of the game window
	 */
	public void resize(int width, int height) {
		bounds.set(0,0,width,height);
	}
	
	/** Get red deck */
	private ArrayList<CardModel> initRedDeck() {
		//todo
		return null;
	}
	/** Get blue deck
	 * THIS FUNCTION IS CURRENTLY BROKEN */
	private ArrayList<CardModel> initBlueDeck() {
		ArrayList<CardModel> blueDeck = new ArrayList<CardModel>();
		for(int i = 0; i<30; i++){
			CardModel j = new CardModel(100+(i*5),100, 90,blueCardJsons.get(i),backTexture,backTexture );
			blueDeck.add(j);
		}
		return blueDeck;
		//todo
//		return null;
	}
	/** Removes the top num cards from draw source and returns them as an arraylist*/
	private ArrayList<CardModel> drawCard(int num, ArrayList<CardModel> drawSource) {
		ArrayList<CardModel> out = new ArrayList<>();
		for (int i = 0; i < num; i++) {
			if(drawSource.size() > 0) {
				out.add(drawSource.remove(0));
			}
		}
		return out;
	}

	private void playCardRed(int current) {
		try {
			CardModel card = RedHand.get(current);
			if(card.electricity + RedUsedElectricity < RedElectricity && RedMetal - card.metal >= 0) {
				RedHand.remove(current);
				RedField.add(card);
				CardPlayed = true;
			}
		} catch (Exception e) {}
	}
	private void playCardBlue(int current) {
		try {
			CardModel card = BlueHand.get(current);
			if(card.electricity + BlueUsedElectricity < BlueElectricity && BlueMetal - card.metal >= 0) {
				BlueHand.remove(current);
				BlueField.add(card);
			}
			CardPlayed = true;
		} catch (Exception e) {}
	}

	private void attackCardRed(int current) {
		try {
			CardModel card = RedField.get(current);
			DeclaredAttacking = current;
			AttackStarted = true;
		} catch (Exception e) {}
	}
	private void attackCardBlue(int current) {
		try {
			CardModel card = BlueField.get(current);
			DeclaredAttacking = current;
			AttackStarted = true;
		} catch (Exception e) {}
	}

	private void defendCardRed(int current) {
		try {
			CardModel card = RedField.get(current);
			DeclaredDefending = current;
			DefenseDeclared = true;
		} catch (Exception e) {}
	}
	private void defendCardBlue(int current) {
		try {
			CardModel card = BlueField.get(current);
			DeclaredDefending = current;
			DefenseDeclared = true;
		} catch (Exception e) {}
	}

}