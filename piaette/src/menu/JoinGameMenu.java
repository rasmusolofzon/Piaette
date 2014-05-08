package menu;

import main.GameStater;
import main.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class JoinGameMenu extends BasicGameState {
	private int id;
	private MenuButton backButton;
	private PiaetteTextField ipField;
	
	public JoinGameMenu(int id){
		this.id = id;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image back = new Image("menu/back.png");
		Image backHover = new Image("menu/back-hover.png");
		backButton = new MenuButton(back, backHover, (Main.width - back.getWidth()) / 2, Main.height - 100);
		
		ipField = new PiaetteTextField(gc,Utility.getNewFont("Futura",20),200,"IP Address:");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		backButton.draw(g);
		ipField.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(backButton.clicked()){
			sbg.enterState(GameStater.onlineMenu);
		}

	}

	@Override
	public int getID() {
		return id;
	}

}
