package server;

import java.util.ArrayList;
import java.util.Observable;

public class GameMailBox extends Observable {
	ArrayList<GameClientHandlerIn> playersIn;
	ArrayList<GameClientHandlerOut> playersOut;
	ArrayList<PlayerDefinition> players;
	
	public GameMailBox() {
		playersIn = new ArrayList<GameClientHandlerIn>();
		playersOut = new ArrayList<GameClientHandlerOut>();
	}
	
	public synchronized int addClient(GameClientHandlerIn clientHandlerIn) {
		playersIn.add(clientHandlerIn);
		setChanged();
		notifyObservers(this);
		return playersIn.size();
	}
	
	public synchronized int addClient(GameClientHandlerOut clientHandlerOut) {
		playersOut.add(clientHandlerOut);
		setChanged();
		notifyObservers(this);
		return playersOut.size();
	}
}
