package protocol;

public class Protocol {
	public static int PROTOCOL_CLIENT = 0, PROTOCOL_SERVER = 1;
	private int protocol,seq;
	
	public Protocol(int p) {
		this.protocol = p;
	}
	
	public int getProtocol() {return protocol;}
}
