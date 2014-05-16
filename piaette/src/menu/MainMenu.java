package menu;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

import main.GameInstantiator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import framtiden.GameStater;

public class MainMenu extends BasicGameState {
	
	private int id;
	private float width,sloganPos,finalPos;
	private Image gameTitle,slogan;
	private MenuButton playButton,exitButton,playOnlineButton, settingsButton;
	private FileFilter filter;
	public static final int buttonSpacing = 75,topButton = 250;
	
	public MainMenu(int id){
		this.id = id;
		width = GameInstantiator.width;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		//Title
		gameTitle = new Image("Graphics/menu/GameTitle.png");
		
		//Slogan
		Random generator = new Random();
		filter = new PNGFileFilter();
		int nbrOfSlogans = new File("Graphics/menu/slogan/").listFiles(filter).length;
		int randomNbrOfSlogans = generator.nextInt(nbrOfSlogans)+1;
		slogan = new Image("Graphics/menu/slogan/slogan-"+randomNbrOfSlogans+".png");
		finalPos = (width-gameTitle.getWidth())/2+slogan.getWidth()-5;
		sloganPos = -slogan.getWidth();
		
		//Bilder p� knappar
		Image play = new Image("Graphics/menu/play.png");
		Image playHover = new Image("Graphics/menu/play-hover.png");
		Image exit = new Image("Graphics/menu/exit.png");
		Image exitHover = new Image("Graphics/menu/exit-hover.png");
		Image playWithOthers = new Image("Graphics/menu/playWithOthers.png");
		Image playWithOthersHover = new Image("Graphics/menu/playWithOthers-hover.png");
		Image settings = new Image("Graphics/menu/settings-button.png");
		Image settingsHover = new Image("Graphics/menu/settings-button-hover.png");
		
		
		playButton = new MenuButton(play,playHover,(width-play.getWidth())/2,topButton);
		playOnlineButton = new MenuButton(playWithOthers,playWithOthersHover,(width-playWithOthers.getWidth())/2,topButton+buttonSpacing);
		settingsButton = new MenuButton(settings,settingsHover,(width-settings.getWidth())/2,topButton+2*buttonSpacing);
		exitButton = new MenuButton(exit,exitHover,(width-exit.getWidth())/2,topButton+3*buttonSpacing);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(gameTitle,(width-gameTitle.getWidth())/2,50);
		g.drawImage(slogan,sloganPos,175);
		
		playButton.draw(g);
		playOnlineButton.draw(g);
		settingsButton.draw(g);
		exitButton.draw(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		if(playButton.clicked())
			sbg.enterState(GameStater.game);
		
		else if(playOnlineButton.clicked())
			sbg.enterState(GameStater.onlineMenu);
	
		
		else if(settingsButton.clicked())
			sbg.enterState(GameStater.settingsMenu);

		else if(exitButton.clicked())
			gc.exit();
		
		if(sloganPos<finalPos) sloganPos+=20;
		else sloganPos = finalPos;
		
	}

	@Override
	public int getID() {
		return id;
	}
	
	//Returnerar true f�r alla .png-filer
	public static class PNGFileFilter implements FileFilter {

	    @Override
	    public boolean accept(File pathname) {
	        String suffix = ".png";
	        if( pathname.getName().toLowerCase().endsWith(suffix) ) {
	            return true;
	        }
	        return false;
	    }

	}
	

}
