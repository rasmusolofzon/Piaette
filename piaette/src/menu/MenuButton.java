package menu;

import main.Main;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class MenuButton {
	private float x, y;
	private Image img,hover;
	private boolean hovering,waitingForMouseRelease=false;
	public MenuButton(Image img,Image hover, float x, float y){
		this.hover = hover;
		this.img = img;
		this.x = x;
		this.y = y;
	}
	public float getMinX(){
		return x;
	}
	public float getMinY(){
		return y;
	}
	public float getMaxX(){
		return x + img.getWidth();
	}
	public float getMaxY(){
		return y+img.getHeight();
	}
	public void draw(Graphics g){
		g.drawImage(this.getImage(), this.getMinX(), this.getMinY());
	}
	
	/*
	 * Sets if the mouse is hovering and returns boolean if needed
	 */
	public boolean isHovering(float mouseX, float mouseY){
		hovering = (mouseX>x && mouseX<getMaxX() && mouseY<(Main.height-y) && mouseY>(Main.height-getMaxY()));
		return hovering;
	}
	public boolean isHovering(){
		return hovering;
	}
	public Image getImage(){
		if(hovering) return hover;
		return img;
	}
	public boolean clicked(){
		float x = Mouse.getX(), y = Mouse.getY();
		if(isHovering(x,y)){
			if(!Mouse.isButtonDown(0) && this.waitingForMouseRelease) return true;
			if(Mouse.isButtonDown(0)) this.waitingForMouseRelease = true;
		} else{
			this.waitingForMouseRelease = false;
		}
		return false;
	}
	
	
}
