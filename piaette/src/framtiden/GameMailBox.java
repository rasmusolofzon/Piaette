package framtiden;

import java.util.ArrayList;
import java.util.Observable;

import utilties.*;

public class GameMailBox extends Observable {
	private ArrayList<GameClientHandlerIn> playersIn;
	private ArrayList<GameClientHandlerOut> playersOut;
	private ArrayList<PlayerDefinition> players;
	private int sequenceNbr;
	private int piaette_id;
	
	public GameMailBox() {
		playersIn = new ArrayList<GameClientHandlerIn>();
		playersOut = new ArrayList<GameClientHandlerOut>();
		sequenceNbr = 0;
	}
	
	public synchronized int addClientIn(GameClientHandlerIn clientHandlerIn) {
		playersIn.add(clientHandlerIn);
		setChanged();
		notifyObservers(this);
		return playersIn.size();
	}
	
	public synchronized int addClientOut(GameClientHandlerOut clientHandlerOut) {
		playersOut.add(clientHandlerOut);
		setChanged();
		notifyObservers(this);
		return playersOut.size();
	}
	
	public void updateClients() {
		//SEQ:#PLAYERS:1:X:Y:rotation:TMR:2:X:Y:rotation:TMR:3:X:Y:rotation:TMR:4:X:Y:rotation:TMR:PIATTE_I
		/*String message = sequenceNbr++ + ":" + players.size() + 
				":1:" + players.get(0).getX() + ":" + players.get(0).getY() + ":" + 
				players.get(0).getRotation() + ":" + players.get(0).getTimer() + 
				":2:" + players.get(1).getX() + ":" + players.get(1).getY() + ":" + 
				players.get(1).getRotation() + ":" + players.get(1).getTimer() +
				":3:" + players.get(2).getX() + ":" + players.get(2).getY() + ":" + 
				players.get(2).getRotation() + ":" + players.get(2).getTimer() +
				":4:" + players.get(3).getX() + ":" + players.get(3).getY() + ":" + 
				players.get(3).getRotation() + ":" + players.get(3).getTimer() + 
				piaette_id;*/
		
		ServerProtocol sp = new ServerProtocol(sequenceNbr++, players, piaette_id);
		
		for (GameClientHandlerOut p : playersOut) {
			p.updateClient(sp.toString());
		}
	}
}
