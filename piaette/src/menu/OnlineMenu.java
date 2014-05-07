package menu;

import main.GameStater;
import main.Main;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OnlineMenu extends BasicGameState {
	
	private int id;
	private MenuButton backButton,joinButton;
	
	public OnlineMenu(int id){
		this.id = id;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//TODO
		Image back = new Image("menu/back.png");
		Image backHover = new Image("menu/back-hover.png");
		backButton = new MenuButton(back,backHover,(Main.width-back.getWidth())/2,Main.height-100);
		
		Image join = new Image("menu/join.png");
		Image joinHover = new Image("menu/join-hover.png");
		joinButton = new MenuButton(join,joinHover,(Main.width-join.getWidth())/2,100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		//TODO
		backButton.draw(g);
		joinButton.draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		//TODO
		if(backButton.clicked()){
			sbg.enterState(GameStater.mainMenu);
		}
		if(joinButton.clicked()){
			sbg.enterState(GameStater.joinGameMenu);
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
