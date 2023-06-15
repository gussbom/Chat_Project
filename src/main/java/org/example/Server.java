package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            System.out.println("New Client Connected");
            ClientHandler clientHandler = new ClientHandler(socket);
            executorService.execute(clientHandler);
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
