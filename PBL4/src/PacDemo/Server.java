package PacDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Model.DataReceiveSchema;
import Model.DataSendSchema;

public class Server {
	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		ServerSocket theServer;
		Socket theConnection;
		try {
			theServer = new ServerSocket(5000);
			while (true) {
				theConnection = theServer.accept();
				System.out.println("Have Connection!");
				new ThreadedHandler(this, theConnection).start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}

class ThreadedHandler extends Thread {
	Server sv;
	public Socket incoming;
	public DataInputStream dis;
	public DataOutputStream dos;

	public ThreadedHandler(Server sv, Socket soc) {
		this.sv = sv;
		this.incoming = soc;
		try {
			this.dis = new DataInputStream(incoming.getInputStream());
			this.dos = new DataOutputStream(incoming.getOutputStream());
		} catch (IOException e) {
			// TODO: handle exception
		}
	}

	public void run() {
		try {
			DataInputStream input = new DataInputStream(dis);
			DataOutputStream output = new DataOutputStream(dos);

			while (true) {
				String jsonString = input.readLine();
				if (jsonString != null) {
					System.out.println(jsonString);
					DataSendSchema data = (DataSendSchema) DataSendSchema.fromJsonString(jsonString);
					DataReceiveSchema result = Dijisktra.dijkstra(data.getMatrix(), data.getSource());
					String resultJsonString = DataReceiveSchema.toJsonString(result);
					output.writeBytes(resultJsonString + "\n");
					System.out.println(resultJsonString);
				} else {
				}
			}
		} catch (IOException e) {
			System.out.println("Input Stream error: " + e);
		} catch (Exception e) {
			System.out.println("Undefined internal error: " + e);
		}
	}
}