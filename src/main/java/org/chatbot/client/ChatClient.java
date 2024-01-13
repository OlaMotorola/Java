package org.chatbot.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final BufferedReader userInputReader;

    public ChatClient(String address, int port) throws IOException {
        socket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        userInputReader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient("localhost", 1234);
            client.startCommunication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        userInputReader.close();
    }

    public void startCommunication() throws IOException {
        System.out.println("Connected to chatbot. Type your messages:");

        while (true) {
            System.out.print("You: ");
            String userInput = userInputReader.readLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Closing the chat...");
                break;
            }

            send(userInput);

            String response = receive();
            System.out.println("Chatbot says: " + response);
        }

        close();
    }
}
