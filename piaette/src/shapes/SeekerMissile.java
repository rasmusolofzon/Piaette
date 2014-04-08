package shapes;

import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Circle;

public class SeekerMissile {
	private float direction, movementSpeed, height, width;
	public Color color;
	public Shape circle;
	//public Animation missileAnimation;
	
	private SeekerMissile(float x, float y) throws SlickException {
		direction = 0f;
		movementSpeed = 5; // Tweak
		width = Main.width;
		height = Main.height;
		circle = new Circle(x, y, 50);
	}
	
	public SeekerMissile(float x, float y,Color color) throws SlickException{
		this(x,y);
		this.color = color;
	}
	
}
