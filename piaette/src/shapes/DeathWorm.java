package shapes;

import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Circle;

public class DeathWorm {
	private float direction, movementSpeed, height, width;
	public Color color;
	public Shape circle;
	public boolean alive;
	public Player target;
	
	private DeathWorm(float x, float y) throws SlickException {
		direction = 0f;
		movementSpeed = 5; // Tweak
		width = Main.width;
		height = Main.height;
		circle = new Circle(x, y, 50);
	}
	
	public DeathWorm(float x, float y,Color color) throws SlickException{
		this(x,y);
		this.color = color;
	}
	
	public void awaken() {
		alive = true;
	}
	
	public void slumber() {
		alive = false;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void hunt(Player player) {
		target = player;
		calibrateSights();
		move();
	}
	
	private void calibrateSights() {
		direction = (float) Math.acos((target.getX()-getX())/Math.hypot(target.getX()-getX(), 
					target.getY()-getY()));
	}
	
	private void move(){
		float newX = circle.getCenterX() + ((float) Math.sin(direction)*movementSpeed);
		float newY = circle.getCenterY() + ((float) Math.cos(direction)*movementSpeed);

		if(newX>0 && newX<width && newY>0 && newY<height){
			circle.setCenterX(newX);
			circle.setCenterY(newY);
		} else if(!(newX>0 && newX<width)){ //Spegling höger/vänster
			circle.setCenterX(width-newX);
			circle.setCenterY(newY);
		} else { //Spegling upp/ner
			circle.setCenterX(newX);
			circle.setCenterY(height-newY);
		}
	}
	
	public float getX() {
		return circle.getCenterX();
	}
	
	public float getY() {
		return circle.getCenterY();
	}
}
