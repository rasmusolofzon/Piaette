package menu;

import main.GameStater;
import main.Main;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.TrueTypeFont;

public class SettingsMenu extends BasicGameState implements ComponentListener {

	private int id;
	private MenuButton backButton;
	private TextField nameInput;
	private UnicodeFont font;

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
		
		//Input
		font = new UnicodeFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 20));
		nameInput = new TextField(gc, font, (Main.width - back.getWidth())/2, (Main.height -200), 250, 40,
				new MyComponentListener());
		nameInput.setBackgroundColor(Color.white);
		nameInput.setBorderColor(Color.gray);
		nameInput.setText("Change name");
		
		nameInput.setConsumeEvents(true);
	}

	private class MyComponentListener implements ComponentListener {

		@Override
		public void componentActivated(AbstractComponent source) {
			String message = "Entered1: "+nameInput.getText();
			System.out.println(message);
			nameInput.setFocus(true);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException{
		// TODO
		g.drawImage(backButton.getImage(), backButton.getMinX(),
				backButton.getMinY());

		nameInput.render(gc, g);
		
			}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		// TODO
		if (backButton.clicked()) {
			sbg.enterState(GameStater.mainMenu);
		}
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void componentActivated(AbstractComponent arg0) {
		
	}



}
