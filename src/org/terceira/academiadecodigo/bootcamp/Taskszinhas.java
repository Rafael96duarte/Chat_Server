package org.terceira.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;

public class Taskszinhas implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Serverzito serverzito;
    private String name;
    private static int namenbr = 1;
    private String[] splitedMessage;
    private String infoRcv;


    public Taskszinhas(Socket clientSocket, Serverzito serverzito) throws IOException {
        this.clientSocket = clientSocket;
        this.serverzito = serverzito;
        name = "Client " + namenbr + ": ";
        namenbr++;

    }

    @Override
    public void run() {

        try {

            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (clientSocket.isBound()) {

                //__implement thread responsibility___//
                infoRcv = in.readLine();
                String message = messageRcv();
                splitedMessage = infoRcv.split(" ");


                if (splitedMessage[0].contains("/changeName")){

                    name = setName() + " : ";
                }

                serverzito.broadcast(name, message);
            }

        } catch(IOException ex){
            ex.printStackTrace();

        }
    }

    private String messageRcv() {
        String message = null;
        message =infoRcv; // blocking
        return message;
    }

    public void sendMsg(String message) {
        out.println(message);
        out.flush();
    }

    private String setName() {

        return splitedMessage[1];
    }
}