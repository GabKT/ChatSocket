package Program;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
	- Melhorar o uso de threads, verificar vazamento de recurso
	- Permitir envio de arquivos
*/

public class Server {

	public static void main(String[] args) throws IOException {
		Integer port = 4444;
		try {
			ServerSocket server = new ServerSocket(port);
			System.out.println("Server listening port: " + port + "...");

			Socket client = server.accept();
			System.out.println("Connection complete, client: " + client.getInetAddress().getHostAddress());
			System.out.println();
			System.out.println("Type your message to client or type BYE to finish: ");
			System.out.println();
			Chat chat = new Chat(client);

			Thread t1 = new Thread(() -> {
				try {
					chat.receiveMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			Thread t2 = new Thread(() -> {
				try {
					chat.sendMessage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			t1.start();
			t2.start();

			while (!chat.getExit()) {

			}
			server.close();

		} catch (EOFException e) {
			System.out.println("Server closed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
