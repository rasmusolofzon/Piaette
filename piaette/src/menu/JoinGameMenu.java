package menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class JoinGameMenu extends BasicGameState {
	private int id;
	public JoinGameMenu(int id){
		this.id = id;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Graphics g = gc.getGraphics();
		g.setBackground(Color.white);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input i = gc.getInput();
		
	}

	@Override
	public int getID() {
		return id;
	}

}
