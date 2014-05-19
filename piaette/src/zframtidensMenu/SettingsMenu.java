package zframtidensMenu;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utilities.comUtility;



import zframtiden.GameStater;
import game.GameInstantiator;

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
		Image back = new Image("Graphics/menu/back.png");
		Image backHover = new Image("Graphics/menu/back-hover.png");
		backButton = new MenuButton(back, backHover,
				(GameInstantiator.width - back.getWidth()) / 2, GameInstantiator.height - 100);
		
		nameInput = new PiaetteTextField(gc,comUtility.getNewFont("Futura", 20),250,"Player name:");
		nameInput.deactivate();
		nameInput.setConsumeEvents(false);
	}


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		// TODO
		g.drawImage(backButton.getImage(), backButton.getMinX(),
				backButton.getMinY());

		nameInput.render(g);
		
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (backButton.clicked()) {
			sbg.enterState(GameStater.mainMenu);
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
