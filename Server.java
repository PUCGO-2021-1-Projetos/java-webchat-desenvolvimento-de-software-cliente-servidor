import chat.ChatServer;

public class Server {
    public static void main(String[] args) {
        ChatServer server = new ChatServer(3000);
        server.start();
    }
}
