import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;

    // создаем удобные средства ввода и вывода
    Scanner in ;
    PrintStream out;
    OnlineChat chat;

    public Client(Socket socket, OnlineChat chat){

        this.socket = socket;
        this.chat=chat;
        new Thread(this).start();
    }

    void receive(String messange){
        out.println(messange);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to anonymous chat!");
            String input = in.nextLine();
            while (!input.equals("bye")) {
                chat.sendAll(input);
                input = in.nextLine();
            }
            chat.sendAll("One of the users has left the chat.");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
