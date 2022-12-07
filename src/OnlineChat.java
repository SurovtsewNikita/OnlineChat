import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class OnlineChat {
    ArrayList<Client> clients = new ArrayList<>();

    ServerSocket serverSocket ;

    public OnlineChat() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    void sendAll(String messang){
        for (Client client:clients){
            client.receive(messang);
        }
    }
    public void run() {
        while (true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                clients.add(new Client(socket,this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new OnlineChat().run();

    }


}