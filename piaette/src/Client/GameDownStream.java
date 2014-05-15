package Client;

import java.util.ArrayList;

import server.PlayerDefinition;
import main.Utility;

public class GameDownStream extends Thread {
	private GameClient model;
	public GameDownStream(GameClient model){
		//TODO
		this.model = model;
	}
	@Override
	public void run(){
		while(true){
			String receive = Utility.receiveUDP(model.getSocket());
			parse(receive);
		}
	}
	public void parse(String message){
		message = "SRV:SEQ:4:1:X:Y:rotation:TMR:2:X:Y:rotation:TMR:3:X:Y:rotation:TMR:4:X:Y:"
				+ "rotation:TMR:PIATTE_ID:piaettarens ID";
		String[] words = message.split(":");
		if(words.length>3 && words[0].equals("SRV")){
			try{
				int nbrOfPlayers = Integer.parseInt(words[2]);
				int sequenceNumber = Integer.parseInt(words[1]);

				ArrayList<PlayerDefinition> players = new ArrayList<PlayerDefinition>();

				for(int i = 3;i<words.length;i=i+5){
					int id = Integer.parseInt(words[i]);
					float x = Float.parseFloat(words[i+1]);
					float y = Float.parseFloat(words[i+2]);
					float r = Float.parseFloat(words[i+3]);
					float timer = Float.parseFloat(words[i+4]);
					model.updatePlayer(id,x,y,r,timer);
				}
			} catch (NumberFormatException e){
				e.printStackTrace();
			}
		}
	}

	//TESTING
	public static void main(String[] args){
		GameDownStream gds = new GameDownStream(null);
		gds.parse("");
	}
}
