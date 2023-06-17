package chat.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import chat.ChatServer;

/**
 * This thread handles connection for each connected client, so the server
 * can handle multiple clients at the same time.
 *
 * @author www.codejava.net
 */
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
 
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
 
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            OutputStream output = socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
 
            String userName = reader.readLine();
            this.server.addUserName(userName);
 
            String serverMessage = userName + " entrou.";
            this.server.broadcast(serverMessage, this);
 
            String message;
 
            do {
                message = reader.readLine();
                message = "[" + userName + "]: " + message;
                this.server.broadcast(message, this);
 
            } while (!message.equals("bye"));
 
            this.server.unsubscribe(userName, this);
            socket.close();
 
            message = userName + " has quitted.";
            this.server.broadcast(message, this);
 
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Sends a message to the client.
     */
    public void sendMessage(String message) {
        if (this.writer != null) 
        {
            this.writer.println(message);
        }
    }
}