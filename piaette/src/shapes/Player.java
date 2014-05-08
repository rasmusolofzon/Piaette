package shapes;


import main.Main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;


public class Player{

	//Kanske borde göra några av dessa attribut privata... eller alla
	private float direction,movementSpeed,height,width;
	public Shape circle;
	public String name;
	public int down,up,left,right;
	private long frozenTime,freezeTime;
	public long score;
	public Color color;
	public Animation playerAnimation;
	public boolean isRunning=false,isAlive = true;

	private Player(float x, float y) throws SlickException {
		circle = new Circle(x,y,32);
		direction = 0f;
		movementSpeed = 4; // Tweak
		freezeTime = 2000; // Tweak
		width = Main.width;
		height = Main.height;
		playerAnimation = new Animation(new SpriteSheet("Graphics/animations/runningPlayer2.png",64,64),250);
		frozenTime = System.currentTimeMillis();
		score = 0;
	}

	public Player(float x, float y, int keyUp,int keyDown,int keyLeft,int keyRight,String name,Color color) throws SlickException{
		this(x,y);
		down = keyDown;
		up = keyUp;
		left = keyLeft;
		right = keyRight;
		this.name = name;
		this.color = color;
	}


	public void draw(Graphics g, Player chaser){
		
		if(this == chaser){
			g.setColor(color);
			g.fill(circle);
		}
		
		g.setColor(color);
		g.draw(circle);

		if(isRunning){ //springandes
			playerAnimation.getCurrentFrame().setRotation(-getDirection()-90);
			playerAnimation.draw(circle.getMinX(),circle.getMinY());
		} else { //Stillastående
			playerAnimation.getImage(0).setRotation(-getDirection()-90);
			playerAnimation.getImage(0).draw(circle.getMinX(),circle.getMinY());
		}
	}

	public void handleInput(Input i){
		//Sätt animationen till stilla
		isRunning = false;
		//Får inte styra om man precis blivit pjättad eller är död
		if(isFrozen() || !isAlive) return; 

		//Up and down
		if(i.isKeyDown(down)){
			goBack();
		} if(i.isKeyDown(up)){
			goForth();
		}
		//Left and right
		if(i.isKeyDown(left)){
			turnLeft(0.1f);
		}  if(i.isKeyDown(right)){
			turnLeft(-0.1f);
		}
	}
	public void turnLeft(float radians){
		direction+=radians;
	}
	public void goForth(){
		move(-1);
	}
	public void goBack(){
		move(1);
	}


	private void move(int forward){
		isRunning=true; //NU SPRINGER VI JUH!

		float newX = circle.getCenterX()+(float) ((float) forward*Math.sin(direction)*movementSpeed);
		float newY = circle.getCenterY()+(float) ((float) forward*Math.cos(direction)*movementSpeed);

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

	public String toString(){
		return name;
	}

	public void freeze(){ //Frys spelaren när denne blir pjättad
		frozenTime = System.currentTimeMillis();
	}
	public boolean isFrozen(){
		return (System.currentTimeMillis()-frozenTime) < freezeTime;
	}

	public float getDirection(){
		return (float) Math.toDegrees(direction);
	}
	
	public float getX() {
		return circle.getCenterX();
	}
	
	public float getY() {
		return circle.getCenterY();
	}
	
	public void die(){ // DÖÖÖÖÖÖÖÖ!
		isAlive = false;
	}
	
	public void updateSpeed(){ //Run faster the less life you has
		//this.movementSpeed = score/8000 +4;
	}

}
