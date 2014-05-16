package utilties;

public class Protocol {
	public static int PROTOCOL_CLIENT = 0, PROTOCOL_SERVER = 1;
	private int protocol;
	
	public Protocol(int p) {
		this.protocol = p;
	}
	
	public int getProtocol() {return protocol;}
}
