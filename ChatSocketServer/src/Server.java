import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
	- Melhorar tratamentos e mensagens de saida
	- Utilizar threads
	- Permitir envio de arquivos
*/

public class Server {

	public static void main(String[] args) throws IOException {

		try {
			Integer porta = 4444;
			ServerSocket server = new ServerSocket(porta);
			System.out.println("Server listening port: " + porta + "...");

			while (true) {

				Socket client = server.accept();

				System.out.println("Connection complete, client: " + client.getInetAddress().getHostAddress());
				System.out.println();
				DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());
				DataInputStream dataIn = new DataInputStream(client.getInputStream());
				dataOut.flush();
				Scanner scan = new Scanner(System.in);
				while (true) {
					String clientMsg = dataIn.readUTF();
					System.out.println("Client: " + clientMsg);
					System.out.println();
					System.out.println("Send a message or type BYE to finish");
					String msg = scan.nextLine();
					if (msg.equals("BYE")) {
						System.out.println("Closing app...");
						server.close();
						client.close();
						dataOut.close();
						dataIn.close();
						scan.close();
						System.exit(3);
					}
					dataOut.writeUTF(msg);
					System.out.println();
				}

			}
		} catch (EOFException e) {
			System.out.println("Server closed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
