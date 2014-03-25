package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import shapes.Player;


public class Game extends BasicGame {
	private Player chaser;
	private ArrayList<Player> players;
	private int width,height,dingCounter;
	private float scale, explX,explY,boomX,boomY;
	private Animation boomAnimate,intro,explodeAnimate,winner;
	private boolean boom,explode;
	private Audio ding,explosion;
	private boolean isRunning = false;
	private long elapsedTime,gameLength;
	
	public Game(String title) {
		super(title);
		width = GameTest.width;
		height = GameTest.height;
		scale = GameTest.scale;
		players = new ArrayList<Player>();
		
		//Tweakvärde
		gameLength = 10000;
	}
	
	
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
		//Vit bakgrund
		Graphics g = gc.getGraphics();
		g.setBackground(Color.white);
		
		
		//Init players. Could be done better.
		players.add(new Player(width/2*scale,height/2*scale,
				Input.KEY_UP,Input.KEY_DOWN,Input.KEY_LEFT,Input.KEY_RIGHT,"Sad player",Color.cyan));
		players.add(new Player(width/2*scale,height/2*scale+100,
				Input.KEY_W,Input.KEY_S,Input.KEY_A,Input.KEY_D,"Sexy Player",Color.pink));
		chaser = players.get(0);
		
		//Ladda litta ljud
		try {
			explosion = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/smallBomb.wav"));
			ding = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sounds/ding.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//Ladda animationer
		boomAnimate = new Animation(new SpriteSheet("animations/boom.png",128,128,Color.black),50);
		boomAnimate.setLooping(false);
		
		explodeAnimate = new Animation(new SpriteSheet("animations/explosion.png",128,128,Color.black),50);
		explodeAnimate.setLooping(false);
		
		intro = new Animation(new SpriteSheet("animations/intro.png",128,128),250);
		intro.setLooping(false);
		
		winner = new Animation(new SpriteSheet("animations/winner.png",512,128),100);
		
	}

	//Rita maddafukkas
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		//Under gameplay
		if(isRunning){
			
			//Fyll pjättarrrns cirkel
			g.setColor(chaser.color);
			g.fill(chaser.circle);
			
			
			//Rita och animera de andra spelarna
			for(Player p : players){
				g.setColor(p.color);
				g.draw(p.circle);
				
				if(p.isRunning){ //springandes
					p.playerAnimation.getCurrentFrame().setRotation(-p.getDirection()-90);
					p.playerAnimation.draw(p.circle.getMinX(),p.circle.getMinY());
				} else { //Stillastående
					p.playerAnimation.getImage(0).setRotation(-p.getDirection()-90);
					p.playerAnimation.getImage(0).draw(p.circle.getMinX(),p.circle.getMinY());
				}
				
			}
			
			//Score
			int i = 0;
			for(Player p:players){
				g.setColor(p.color);
				g.drawRect(20, 20+i*30, 100, 20);
				g.fillRect(20, 20+i*30, (gameLength - p.score)/(gameLength/100),20);
				g.drawString(p.score/1000+"", 125, 23+i*30);
				i++;
			}
			
			//esssplosions
			if(explode) {
				explodeAnimate.draw(explX,explY);
			} if(boom) {
				boomAnimate.draw(boomX,boomY);
			}
			
			//För att rita ut "GO! GO! GO! GO!"
			if(elapsedTime<4000) intro.draw(width/2-64, height/2-64);
			
		} else if(elapsedTime<4000){ //starting the game
			intro.draw(width/2-64, height/2-64); 
			
		} else { //it's over. Show dah winner
			
			for(Player p : players){ //Finns bara en player i loopen, men orka.
				g.setColor(p.color);
				g.fill(p.circle);
				if(p.isRunning){
					p.playerAnimation.getCurrentFrame().setRotation(-p.getDirection()-90);
					p.playerAnimation.draw(p.circle.getMinX(),p.circle.getMinY());
				} else {
					p.playerAnimation.getImage(0).setRotation(-p.getDirection()-90);
					p.playerAnimation.getImage(0).draw(p.circle.getMinX(),p.circle.getMinY());
				}
			}
			
			//WINNER! animatino
			winner.draw(width/2-256,height/2-64);
			
			//Behövs för att låta losern explodera
			if(explode) {
				explodeAnimate.draw(explX,explY);
			}
		}	
	}

	//körs varje frame. Logik som behöver uppdateras i realtid.
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		
		//Räknar total tid
		elapsedTime+=delta;

		//Mouse & tangentbord input
		Input i = gc.getInput();
		
		//Animationslogik
		boomAnimate.update(delta);
		explodeAnimate.update(delta);
		if(boomAnimate.isStopped()) boom = false;
		if(explodeAnimate.isStopped()) explode = false;
		
		//Pjättarn förlorar poäng
		if(isRunning) chaser.score+=delta;
		
		//Introanimationer, ljud och vem som börjar pjätta
		if(!isRunning && elapsedTime>3000 && elapsedTime<5000) {
			ding.playAsSoundEffect(2f, 0.7f, false);
			isRunning = true;
			Random generator = new Random();
			youreIt(players.get(generator.nextInt(players.size())));
		} else if(!isRunning && elapsedTime/1000>=dingCounter && elapsedTime<5000){
			dingCounter++;
			ding.playAsSoundEffect(1f, 0.5f, false);
		}
		
		//Låt inte spelare styra under tiden introt körs
		if(elapsedTime<3000) return;
		
		//Spelares styrning
		for(Player player : players){
			//Player-objektet hanterar input själv
			player.handleInput(i);
			//Animation
			player.playerAnimation.update(delta);
			
			//Om spelet är över, så kolla inte efter kollisioner
			if(!isRunning) return;
			
			//Kollisionsdetektion
			if(player!= chaser && chaser.circle.intersects(player.circle) && !chaser.isFrozen()){
				youreIt(player);
			}
			
			//När tiden rinner ut
			if(player.score>gameLength){ 
				player.die();
				explode = true;
				explodeAnimate.restart();
				explosion.playAsSoundEffect(0.5f, 0.5f, false);
				explX = player.circle.getCenterX()-explodeAnimate.getWidth()/2;
				explY = player.circle.getCenterY()-explodeAnimate.getWidth()/2;
				
				//Om spelaren är den sista kvar = WINNER!
				if(players.size()-1==1) {
					isRunning = false;
					players.remove(player);
					break;
				}
				
				//Utse random nästa pjättare
				else{
					Random generator = new Random();
					youreIt(players.get(generator.nextInt(players.size())));
				}
			}
		}
	}
	
	//Animationer och ljud för att bli pjättad
	private void youreIt(Player player) {
		ding.playAsSoundEffect(0.5f, 0.5f, false);
		chaser = player;
		chaser.freeze();
		boom = true;
		boomAnimate.restart();
		boomX = player.circle.getCenterX()-boomAnimate.getWidth()/2;
		boomY = player.circle.getCenterY()-boomAnimate.getWidth()/2;
	}

}