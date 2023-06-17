package chat;

import java.net.*;
import java.io.*;

import chat.thread.*;


public class ChatClient {
    private String hostname;
    private int port;
    private String userName;
 
    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
 
    public void start() {
        try {
            Socket socket = new Socket(hostname, port);
 
            System.out.println("Connected to the chat server");
 
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
 
        } catch (UnknownHostException ex) {
            System.out.println("Servidor não encontrado: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro de I/O: " + ex.getMessage());
        }
 
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getUserName() {
        return this.userName;
    }
}
