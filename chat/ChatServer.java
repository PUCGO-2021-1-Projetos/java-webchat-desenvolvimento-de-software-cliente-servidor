package chat;
 
import java.io.*;
import java.net.*;
import java.util.*;

import chat.thread.UserThread;
 
/**
 * This is the chat server program.
 * Press Ctrl + C to terminate the program.
 *
 * @author www.codejava.net
 */
public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
 
    public ChatServer(int port) {
        this.port = port;
    }
 
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
            System.out.println("Chat server ouvindo na porta " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Novo inscrito conectado");
 
                UserThread thread = new UserThread(socket, this);
                userThreads.add(thread);
                thread.start();
            }
 
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
 
    /**
     * Delivers a message from one user to others (broadcasting)
     */
    public void broadcast(String message, UserThread userThread) {
        for (UserThread thread : this.userThreads) {
            if (thread == userThread) {
                continue;
            }
            thread.sendMessage(message);
        }
    }
 
    /**
     * Stores username of the newly connected client.
     */
    public void addUserName(String userName) {
        userNames.add(userName);
        System.out.println("Novo inscrito: " + userName);
    }
 
    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    public void unsubscribe(String userName, UserThread userThread) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(userThread);
            System.out.println("The user " + userName + " quitted");
        }
    }
}