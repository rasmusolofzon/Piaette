package framtiden;

import utilities.ClientProtocol;
import utilities.Protocol;
import utilities.ProtocolParser;
import utilities.ServerProtocol;

public class TestProtocol {
	public static void main(String[] args) {
		new TestProtocol();
	}
	
	public TestProtocol() {
		init();
	}
	
	private void init() {
		String message = "SRV:256:3:2-20-21-42-22:3-30-31-43-33:4-40-41-44-444:3";
//		String message = "CLI:254:3:32:33:45";
		ProtocolParser p = ProtocolParser.getInstance();
		
		Protocol rcv = p.parse(message);
		if (rcv!=null && rcv.getProtocol()==Protocol.PROTOCOL_CLIENT) {
			ClientProtocol cRcv = (ClientProtocol) rcv;
			System.out.println(cRcv);
		}
		
		if (rcv!=null && rcv.getProtocol()==Protocol.PROTOCOL_SERVER) {
			ServerProtocol sRcv = (ServerProtocol) rcv;
			System.out.println(sRcv);
		}
	}
}
