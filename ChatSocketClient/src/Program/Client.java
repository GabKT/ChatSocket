package Program;

import java.io.IOException;
import java.net.Socket;

/*
 - Permitir envio de arquivos
*/
public class Client {

	public static void main(String[] args) {

		try {
			if (args.length != 1) {
				System.out.println("Please insert the correct format example:'java Client <ip>'");
				System.exit(-1);
			}
			String ip = args[0];
			Socket stream = new Socket(ip, 4444);
			System.out.println("Connection complete!!!");
			System.out.println();
			System.out.println("Type your message to server or type BYE to finish: ");
			System.out.println();
			Chat chat = new Chat(stream);

			Thread t1 = new Thread(() -> {
				try {
					chat.receiveMessage();
				} catch (IOException e) {
					if (!chat.getExit()) {
						e.printStackTrace();
					}
				}
			});

			Thread t2 = new Thread(() -> {
				try {
					chat.sendMessage();
				} catch (IOException e) {
					if (!chat.getExit()) {
						e.printStackTrace();
					}
				}
			});

			t1.start();
			t2.start();
			t1.join();
			t2.join();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
