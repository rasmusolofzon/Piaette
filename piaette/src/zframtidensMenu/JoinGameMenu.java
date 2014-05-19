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

public class JoinGameMenu extends BasicGameState {
	private int id;
	private MenuButton backButton,joinButton;
	private PiaetteTextField ipField;
	private Image gameTitle;
	
	public JoinGameMenu(int id){
		this.id = id;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		//Title
		gameTitle = new Image("Graphics/menu/GameTitle.png");
		
	
		
		ipField = new PiaetteTextField(gc,comUtility.getNewFont("Futura",20),MainMenu.topButton,"IP Address:");
		
		Image join = new Image("Graphics/menu/join.png");
		Image joinHover = new Image("Graphics/menu/join-hover.png");
		joinButton = new MenuButton(join,joinHover,(GameInstantiator.width-join.getWidth())/2,MainMenu.topButton+MainMenu.buttonSpacing);
		
		Image back = new Image("Graphics/menu/back.png");
		Image backHover = new Image("Graphics/menu/back-hover.png");
		backButton = new MenuButton(back, backHover, (GameInstantiator.width - back.getWidth()) / 2, MainMenu.topButton+MainMenu.buttonSpacing*2);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(gameTitle,(GameInstantiator.width-gameTitle.getWidth())/2,50);
		
		backButton.draw(g);
		joinButton.draw(g);
		ipField.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(backButton.clicked()){
			sbg.enterState(GameStater.onlineMenu);
		}
		
		if(joinButton.clicked()){
			//TODO: JOIN GAMEZ!
		}
	}

	@Override
	public int getID() {
		return id;
	}

}
