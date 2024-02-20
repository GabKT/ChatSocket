import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.util.Scanner;
/*
 - Melhorar tratamentos e mensagens de saida
 - Utilizar threads
 - Permitir envio de arquivos
*/
public class Client {

	public static void main(String[] args) {

		try {
			if (args.length < 2) {
				System.out.println("Please insert the correct format example:'java Client <ip> <port>'");
				System.exit(-1);
			}
			String ip = args[0];
			Integer port = Integer.parseInt(args[1]);
			Socket client = new Socket(ip, port);

			System.out.println("Connection complete!!!");
			System.out.println();

			Scanner scan = new Scanner(System.in);
			DataInputStream dataIn = new DataInputStream(client.getInputStream());
			DataOutputStream dataOut = new DataOutputStream(client.getOutputStream());

			while (true) {
				System.out.println("Send a message or type BYE to finish");
				String msg = scan.nextLine();
				if (msg.equals("BYE")) {
					System.out.println("Closing app...");
					client.close();
					scan.close();
					dataIn.close();
					dataOut.close();
					System.exit(3);
				}
				dataOut.writeUTF(msg);
				System.out.println();
				System.out.println("Aguarde a mensagem do servidor...");
				System.out.println();
				String serverMsg = dataIn.readUTF();
				System.out.println("Server: " + serverMsg);
				System.out.println();
			}
		} catch (EOFException e) {
			System.out.println("Server closed");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
