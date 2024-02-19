import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class exemple_client {

    public static void main(String[] args) {
        final String adr_serveur = "localhost";
        final int port_serveur = 9876;

        try {
            Socket socket = new Socket(adr_serveur, port_serveur);
            System.out.println("connecté au serveur");

            BufferedReader lecture = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter ecriture = new PrintWriter(socket.getOutputStream(), true);

            String msg = "bonjour serveur";
            System.out.println("envoyé au serveur: " + msg);
            ecriture.println(msg);

            String reponse= lecture.readLine();
            System.out.println("reçu par le serveur: " + reponse);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
