import java.io.*;
import java.net.*;

public class serveur {

    public static void main(String[] args) {
        final int port = 9876;
        final int max_cl = 10;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("le serveur écoute sur le port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("un nouveau client est connecté " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket);//créer une thread pour chaque client
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader lecture = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter ecriture = new PrintWriter(clientSocket.getOutputStream(), true);
                String input;
                while ((input = lecture.readLine()) != null) {
                    System.out.println("reçu du client: " + input);

                    try {
                        Thread.sleep(5000);      // Sleep for 5 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String chaineINV = new StringBuilder(input).reverse().toString();
                    ecriture.println(chaineINV);
                    System.out.println("envoyé au client: " + chaineINV);
                }
                lecture.close();
                ecriture.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}