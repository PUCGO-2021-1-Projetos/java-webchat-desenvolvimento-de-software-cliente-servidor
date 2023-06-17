import chat.ChatClient;

public class Client {
    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 3000);
        client.start();
    }
}
