package menu;

import main.GameStater;
import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SettingsMenu extends BasicGameState {

	private int id;
	private MenuButton backButton;
	private PiaetteTextField nameInput;

	public SettingsMenu(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// BackButtom
		Image back = new Image("menu/back.png");
		Image backHover = new Image("menu/back-hover.png");
		backButton = new MenuButton(back, backHover,
				(Main.width - back.getWidth()) / 2, Main.height - 100);
		
		nameInput = new PiaetteTextField(gc,(Main.width - back.getWidth())/2, (Main.height -200), 250, 40);
		nameInput.setBackgroundColor(Color.white);
		nameInput.setBorderColor(Color.gray);
		nameInput.setTextColor(Color.black);
	}


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		// TODO
		g.drawImage(backButton.getImage(), backButton.getMinX(),
				backButton.getMinY());

		nameInput.render(g);
		
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (backButton.clicked()) {
			sbg.enterState(GameStater.mainMenu);
		}
		nameInput.update();
	}

	@Override
	public int getID() {
		return id;
	}

}
