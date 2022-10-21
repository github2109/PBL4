package PacDemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DijisktraServer {
	public static void main(String[] args) {
		new DijisktraServer();
	}
	public DijisktraServer() {
		ServerSocket theServer;
		Socket theConnection;
		try {
			theServer = new ServerSocket(5000);
			while(true) {
				theConnection = theServer.accept();
				System.out.println("Have Connection!");
				new ThreadedHandler(this,theConnection).start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
