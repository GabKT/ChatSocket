package Program;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Chat {

	private Socket client;
	private volatile boolean exit = false;

	public Chat(Socket client) {
		this.client = client;
	}

	public Boolean getExit() {
		return exit;
	}

	public void setExit(Boolean exit) {
		this.exit = exit;
	}

	public void receiveMessage() throws IOException {
		DataInputStream dataIn = new DataInputStream(client.getInputStream());
		while (!getExit()) {
			try {
				String clientMsg = dataIn.readUTF();
				System.out.println("Client: " + clientMsg);
				System.out.println();
				if (clientMsg.trim().equalsIgnoreCase("BYE")) {
					setExit(true);
				}
			} catch (Exception e) {
				if (!getExit()) {
					e.printStackTrace();
				}
			}
		}
		dataIn.close();
	}

	public void sendMessage() throws IOException {
		DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
		Scanner scan = new Scanner(System.in);
		while (!getExit()) {
			try {
				String msg = scan.nextLine();
				dataOut.writeUTF(msg);
				System.out.println();

				if (msg.equals("BYE")) {
					setExit(true);
				}
			} catch (Exception e) {
				if (!getExit()) {
					e.printStackTrace();
				}
			}
		}
		dataOut.close();
		scan.close();

	}

}
