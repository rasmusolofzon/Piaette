package main;

import menu.MainMenu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameStater extends StateBasedGame {
	
	public static final int mainMenu = 0, game = 1;
	
	public GameStater(String name) {
		super(name);
		this.addState(new MainMenu(mainMenu));
		this.addState(new Game(game));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		//this.getState(mainMenu).init(gc, this);
		//this.getState(game).init(gc, this);
		this.enterState(mainMenu);
	}
}
