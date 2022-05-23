package edu.cornell.gdiac.shipdemo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import edu.cornell.gdiac.util.FilmStrip;

public class CardModel {
    // Private constants to avoid use of "magic numbers"
//    /** The size of the card in pixels */
//    private static final int CARD_SIZE_X  = 100;
//    /** The size of the card in pixels */
//    private static final int CARD_SIZE_Y  = 200;
    /** The amount to offset the shadow image by */
    private static final float SHADOW_OFFSET = 10.0f;
    /** Amount to scale the card size */
    private static final float DEFAULT_SCALE = .40f;


    //Private Variables
    /** Position of the card */
    private Vector2 pos;

    /** Is the card faceup */
    private boolean faceup;

    /** Color to tint this card (red or blue) */
    private Color tint;
    /** Color of the card shadow (cached) */
    private Color stint;


    //Protected because have no accessors
    /** Current angle of the card */
    protected float ang;

    /** Default cost of the card  */
    public int electricity;
    /** Default cost of the card  */
    public int metal;

    private String roboType;

    /** Current Health of a card */
    public int health;

    /** Default damage of the card  */
    public int dmg;

    /** Reference to cards playing animation (SHOULD IT HAVE ONE) */
    private FilmStrip cardSprite;

    /** Texture for the card face down */
    private TextureRegion cardTextureFD;

    /** Texture for the card face up */
    private TextureRegion cardTextureFU;





    // GETTER SECTION
    /**
     * Returns the position of this card.
     *
     * This is location of the center pixel of the ship on the screen.
     *
     * @return the position of this card
     */
    public Vector2 getPosition() {
        return pos;
    }

    /**
     * @return the current health of the card
     */
    public float getHealth() {
        return health;
    }

    /**
     * @return the current dmg of the card
     */
    public float getDmg() {
        return dmg;
    }

    /**
     * @return the current dmg of the card
     */
    public boolean getFaceUp() {
        return faceup;
    }

    /**
     */
    public void setFaceUp(boolean k) {
        faceup = k;
    }




    // SETTER SECTION

    /**
     * Sets the angle that this ship is facing.
     *
     * The angle is specified in degrees, not radians.
     *
     * @param value the angle of the ship
     */
    public void setAngle(float value) {
        ang = value;
    }


    // CONSTRUCTOR SECTION
    /**
     * Creates a new ship at the given location with the given facing.
     *
     * @param x The initial x-coordinate of the center
     * @param y The initial y-coordinate of the center
     * @param ang The initial angle of rotation
     */
    public CardModel(float x, float y, float ang, JsonValue cardValues, TextureRegion Up, TextureRegion Dn) {
        // Set the position of this ship.
        this.pos = new Vector2(x,y);
        this.ang = ang;


        this.health = cardValues.get("health").getInt(0);
        this.dmg = cardValues.get("dmg").getInt(0);
        this.electricity = cardValues.get("elec").getInt(0);
        this.metal = cardValues.get("steel").getInt(0);
        this.cardTextureFU = Up;
        this.cardTextureFD = Dn;

        //Set current ship image
        tint  = new Color(Color.WHITE);
        stint = new Color(0.0f,0.0f,0.0f,0.5f);
        faceup = false;
    }


    public CardModel(float x, float y, float ang, int health, int dmg, int elect, int metal, String roboType, TextureRegion Up, TextureRegion Dn) {
        // Set the position of this ship.
        this.pos = new Vector2(x,y);
        this.ang = ang;


        this.health = health;
        this.dmg = dmg;
        this.electricity = elect;
        this.metal = metal;
        this.cardTextureFU = Up;
        this.cardTextureFD = Dn;
        this.roboType = roboType;


        //Set current ship image
        tint  = new Color(Color.WHITE);
        stint = new Color(0.0f,0.0f,0.0f,0.5f);
        faceup = false;
    }


    // DRAW FUNCTIONS:
    /**
     * Draws the ship (and its related images) to the given GameCanvas.
     *
     * You will want to modify this method for Exercise 4.
     *
     * This method uses alpha blending, which is set before this method is
     * called (in GameMode).
     *
     * @param canvas The drawing canvas.
     */
    public void drawCard(GameCanvas canvas) {
        if (cardTextureFD == null || cardTextureFU == null) {
            return;
        }
//        // For placement purposes, put origin in center.
        float ox = 0.5f * cardTextureFU.getRegionWidth();
        float oy = 0.5f * cardTextureFU.getRegionHeight();

        // How much to rotate the image
        float rotate = -(90+ang);

        // Draw the shadow.  Make a translucent color.
        // Position it offset by 10 so it can be seen.
        float sx = pos.x+SHADOW_OFFSET;
        float sy = pos.y+SHADOW_OFFSET;

        if (faceup) {
            // Need to negate y scale because of coordinate access flip.
            // Draw the shadow first
            canvas.draw(cardTextureFU, stint, ox, oy, sx, sy, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
            // Then draw the ship
            canvas.draw(cardTextureFU, tint, ox, oy, pos.x, pos.y, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
        } else {
            // Need to negate y scale because of coordinate access flip.
            // Draw the shadow first
            canvas.draw(cardTextureFD, stint, ox, oy, sx, sy, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
            // Then draw the ship
            canvas.draw(cardTextureFD, tint, ox, oy, pos.x, pos.y, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
        }
    }
    /**
     * Draws the ship (and its related images) to the given GameCanvas.
     *
     * You will want to modify this method for Exercise 4.
     *
     * This method uses alpha blending, which is set before this method is
     * called (in GameMode).
     *
     * @param canvas The drawing canvas.
     */
    public void drawCard(GameCanvas canvas, Boolean current) {
        if (cardTextureFD == null || cardTextureFU == null) {
            return;
        }
//        // For placement purposes, put origin in center.
        float ox = 0.5f * cardTextureFU.getRegionWidth();
        float oy = 0.5f * cardTextureFU.getRegionHeight();

        // How much to rotate the image
        float rotate = -(90+ang);

        // Draw the shadow.  Make a translucent color.
        // Position it offset by 10 so it can be seen.
        float sx = pos.x+SHADOW_OFFSET;
        float sy = pos.y+SHADOW_OFFSET;

        if(current){
            stint = new Color(255.0f,0.0f,0.0f,0.5f);
        }else{
            stint = new Color(0.0f,0.0f,0.0f,0.5f);
        }
        if (faceup) {
            // Need to negate y scale because of coordinate access flip.
            // Draw the shadow first
            canvas.draw(cardTextureFU, stint, ox, oy, sx, sy, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
            // Then draw the ship
            canvas.draw(cardTextureFU, tint, ox, oy, pos.x, pos.y, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
        } else {
            // Need to negate y scale because of coordinate access flip.
            // Draw the shadow first
            canvas.draw(cardTextureFD, stint, ox, oy, sx, sy, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
            // Then draw the ship
            canvas.draw(cardTextureFD, tint, ox, oy, pos.x, pos.y, rotate, DEFAULT_SCALE, DEFAULT_SCALE);
        }
    }


    // MOVE FUNCTION

}
