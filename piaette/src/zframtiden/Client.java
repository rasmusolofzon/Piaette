package zframtiden;

import java.io.IOException;

import client.LobbyClient;


public class Client {
	public static void main(String[] args){
		try {
			LobbyClient client = new LobbyClient("localhost",22222,"Sven");
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
