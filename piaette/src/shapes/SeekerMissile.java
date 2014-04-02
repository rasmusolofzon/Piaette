package shapes;

import main.Main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SeekerMissile {
	private float direction,movementSpeed,height,width;
	public Color color;
	public Animation missileAnimation;
	
	private SeekerMissile(float x, float y) throws SlickException {
		direction = 0f;
		movementSpeed = 5; // Tweak
		width = Main.width;
		height = Main.height;
		missileAnimation = new Animation(new SpriteSheet("animations/runningPlayer.png",64,64),250);
	}
	
	public SeekerMissile(float x, float y,Color color) throws SlickException{
		this(x,y);
		this.color = color;
	}
	
}
