import java.io.*;
import java.net.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket server = null;
        Socket client;

        // Defult port number we are giung to use
        int portNumber = 4500;
        if (args.length >= 1) {
            portNumber = Integer.parseInt(args[0]);
        }
        // Create Server Side Socket
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException ie) {
            System.out.println("Cannot Open Socket." + ie);
            System.exit(1);
        }
        System.out.println("ServerSocket is Created " + server);
        // wait for the data from the client and reply
        while (true) {
            try {

                //listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made
                System.out.println("Waiting for connect request...");
                client = server.accept();

                System.out.println("Connect request is accepted...");
                String clientHost = client.getInetAddress().getHostAddress();
                int clientPort = client.getPort();
                System.out.println("Client Host = " + clientHost + "Client Port = " + clientPort);

                // Read data  from Client
                InputStream clientIn = client.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
                String msgFromClient = br.readLine();
                System.out.println("Message received from client = " + msgFromClient);

                // Send response to the client
                if (msgFromClient != null && !msgFromClient.equalsIgnoreCase("bye")) {
                    OutputStream clientOut = client.getOutputStream();
                    PrintWriter pw = new PrintWriter(clientOut, true);
                    String ansMsg = "Hello, " + msgFromClient;
                    pw.println(ansMsg);
                }

                // Close Socket
                if (msgFromClient != null && msgFromClient.equalsIgnoreCase("bye")) {
                    server.close();
                    client.close();
                    break;
                }
            } catch (IOException ie) {
                // skriv ett lämpligt error meddelande
                System.out.println("Error creating server side socket");
            }
        }
    }
}