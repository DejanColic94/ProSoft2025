/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dejan Colic
 */
public class StartServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<ClientServiceThread> clientList;
    private int port = 8189;

    public StartServerThread() {
        try {
            clientList = new ArrayList<>();
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port: " + port);
        } catch (IOException ex) {
            System.out.println("Error creating server socket.");
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected successfully!");
                ClientServiceThread clientThread = new ClientServiceThread(clientSocket, clientList);
                clientList.add(clientThread);
                clientThread.start();
            } catch (SocketException se) {
                System.out.println("Server socket closed, stopping server thread.");
                break; // exit the loop
            } catch (IOException ex) {
                System.out.println("Error in accepting the client socket connection.");
                ex.printStackTrace();
            }
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void terminateThreads() {
        try {
            for (ClientServiceThread clientThread : clientList) {
                clientThread.getSocket().close();
            }

            serverSocket.close();
        } catch (IOException ioe) {
            System.out.println("Error in client socket termination process.");
        }
    }

}
