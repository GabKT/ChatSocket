package Program;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {

	private Socket server;
	private volatile boolean exit = false;

	public Chat(Socket server) {
		this.server = server;
	}

	public Boolean getExit() {
		return exit;
	}

	public void setExit(Boolean exit) {
		this.exit = exit;
	}

	public void receiveMessage() throws IOException {
		DataInputStream dataIn = new DataInputStream(server.getInputStream());
		while (!this.getExit()) {
			try {
				String serverMsg = dataIn.readUTF();
				System.out.println("Server: " + serverMsg);
				System.out.println();
				if (serverMsg.equals("BYE")) {
					setExit(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dataIn.close();
	}

	public void sendMessage() throws IOException {
		DataOutputStream dataOut = new DataOutputStream(server.getOutputStream());
		Scanner scan = new Scanner(System.in);
		while (!this.getExit()) {
			try {
				String msg = scan.nextLine();
				dataOut.writeUTF(msg);
				System.out.println();

				if (msg.equals("BYE")) {
					exit = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		scan.close();
		dataOut.close();
	}

}
