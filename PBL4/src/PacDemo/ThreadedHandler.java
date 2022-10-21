package PacDemo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadedHandler extends Thread{
	DijisktraServer sv;
	public Socket incoming;
	public DataInputStream dis;
	public DataOutputStream dos;
	public ThreadedHandler(DijisktraServer sv, Socket soc) {
		this.sv = sv;
		this.incoming = soc;
		try {
			this.dis = new DataInputStream(incoming.getInputStream());
			this.dos = new DataOutputStream(incoming.getOutputStream());
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	public void run () {
		try {
			ObjectInputStream input = new ObjectInputStream(dis);
			ObjectOutputStream output = new ObjectOutputStream(dos);
			while(true) {
				DataSendSchema data= (DataSendSchema) input.readObject();
				DataReceiveSchema result = Dijisktra.dijkstra(data.getMatrix(), data.getSource());
				output.writeObject(result);
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
