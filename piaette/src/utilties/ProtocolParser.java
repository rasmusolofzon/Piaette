package utilties;

import java.util.ArrayList;


public class ProtocolParser {
	
	private static ProtocolParser proto;
	
	private ProtocolParser() {}
	
	public static ProtocolParser getInstance() {
		if (proto==null) {
			return new ProtocolParser();
		}else{
			return proto;
		}
	}
	
	public Protocol parse(String s) {
		Protocol rVal = null;
		String[] p = s.split(":");
		
		if (p.length<1) {
			return null;
		}
		
		int pType = getType(p[0]);
		if (pType==-1) {
			return null;
		}
		
		if (pType==Protocol.PROTOCOL_CLIENT) {
			int SEQ = toInt(p[1]);
			int PID = toInt(p[2]);
			float X = toFloat(p[3]);
			float Y = toFloat(p[4]);
			float ROT = toFloat(p[5]);
			
			rVal = new ClientProtocol(SEQ, PID, X, Y,ROT);
		}
		
		if (pType==Protocol.PROTOCOL_SERVER) {
			int SEQ = toInt(p[1]);
			int PLAYERS = toInt(p[2]);
			ArrayList<PlayerDefinition> pp = new ArrayList<PlayerDefinition>();
			for (int i = 0;i<PLAYERS;i++) {
				String[] playerInfo = p[i+3].split("-");
				int PID = toInt(playerInfo[0]);
				float PX = toFloat(playerInfo[1]);
				float PY = toFloat(playerInfo[2]);
				float PR = toFloat(playerInfo[3]);
				float PT = toFloat(playerInfo[4]);
				PlayerDefinition pd = new PlayerDefinition("na",PID);
				pd.updateX(PX);
				pd.updateY(PY);
				pd.updateRotation(PR);
				pd.updateTimer(PT);
				pp.add(pd);
			}
			int PIAETTE = toInt(p[p.length-1]);
			rVal = new ServerProtocol(SEQ, pp, PIAETTE);
		}
		
		System.out.println(s);
		return rVal;
	}
	
	private int getType(String raw) {
		int type = -1;
		if (raw.equalsIgnoreCase("SRV")) {
			return Protocol.PROTOCOL_SERVER;
		}else if (raw.equalsIgnoreCase("CLI")) {
			return Protocol.PROTOCOL_CLIENT;
		}
		return type;
	}
	
	private int toInt(String raw) {
		try {
			return Integer.parseInt(raw);
		}catch(Exception e) {
			return -1;
		}
	}
	
	private float toFloat(String raw) {
		try {
			return Float.parseFloat(raw);
		}catch(Exception e) {
			return -1;
		}
	}
	
}
