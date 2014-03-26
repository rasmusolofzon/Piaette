package menu;

import java.util.Random;

import main.GameStater;
import main.Main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
	
	private int id,buttonSpacing;
	private float width,sloganPos,finalPos;
	private Image gameTitle,slogan;
	private MenuButton playButton,exitButton,playOnlineButton;
	
	public MainMenu(int id){
		this.id = id;
		width = Main.width;
		buttonSpacing = 75;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Title and slogan
		gameTitle = new Image("menu/GameTitle.png");
		Random generator = new Random();
		int i = generator.nextInt(2)+1;
		slogan = new Image("menu/slogan-"+i+".png");
		finalPos = (width-gameTitle.getWidth())/2+slogan.getWidth()-5;
		sloganPos = -slogan.getWidth();
		
		Image play = new Image("menu/play.png");
		Image playHover = new Image("menu/play-hover.png");
		Image exit = new Image("menu/exit.png");
		Image exitHover = new Image("menu/exit-hover.png");
		Image playWithOthers = new Image("menu/playWithOthers.png");
		Image playWithOthersHover = new Image("menu/playWithOthers-hover.png");
		
		
		playButton = new MenuButton(play,playHover,(width-play.getWidth())/2,250);
		playOnlineButton = new MenuButton(playWithOthers,playWithOthersHover,(width-playWithOthers.getWidth())/2,250+buttonSpacing);
		exitButton = new MenuButton(exit,exitHover,(width-exit.getWidth())/2,250+2*buttonSpacing);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(gameTitle,(width-gameTitle.getWidth())/2,50);
		g.drawImage(slogan,sloganPos,175);
		
		g.drawImage(playButton.getImage(),playButton.getMinX(),playButton.getMinY());
		g.drawImage(playOnlineButton.getImage(),playOnlineButton.getMinX(),playOnlineButton.getMinY());
		g.drawImage(exitButton.getImage(),exitButton.getMinX(),exitButton.getMinY());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(playButton.clicked())
			sbg.enterState(GameStater.game);
		
		else if(playOnlineButton.clicked())
			sbg.enterState(GameStater.onlineMenu);
		
		else if(exitButton.clicked())
			System.exit(0);

		
		if(sloganPos<finalPos) sloganPos+=20;
		else sloganPos = finalPos;
		
	}

	@Override
	public int getID() {
		return id;
	}

}
