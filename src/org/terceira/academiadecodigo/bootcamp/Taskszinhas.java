package org.terceira.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;

public class Taskszinhas implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Serverzito serverzito;


    public Taskszinhas(Socket clientSocket, Serverzito serverzito) {
        this.clientSocket = clientSocket;
        this.serverzito = serverzito;
    }

    @Override
    public void run() {

        try {

            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (clientSocket.isBound()) {

                //__implement thread responsibility___//
                String message = messageRcv();
                serverzito.broadcast(message);
            }

        } catch(IOException ex){
            ex.printStackTrace();

        }
    }

    private String messageRcv() {
        String message = null;
        try {
            message = in.readLine(); // blocking
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public void sendMsg(String message) {
        out.println(message);
        out.flush();
    }
}