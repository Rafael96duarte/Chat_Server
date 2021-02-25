package org.terceira.academiadecodigo.bootcamp;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serverzito {

    private LinkedList<Taskszinhas> sims;

    public Serverzito() {
        this.sims = new LinkedList<>();
    }

    public void start() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6969);
        } catch (
                IOException e) {
            e.printStackTrace();
            System.err.println("asdfghjklç not working");
        }

        ExecutorService fixedPool = Executors.newFixedThreadPool(21);

        while (serverSocket.isBound()) {
            System.out.println("Server is listening...");

            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Thread thread = new Thread(new Tasks(clientSocket));
            //thread.start();

            Taskszinhas taskszinhas = new Taskszinhas(clientSocket, this);
            sims.add(taskszinhas);
            fixedPool.submit(taskszinhas);
        }
        fixedPool.shutdown();
    }

    public void broadcast(String message){

        for (Taskszinhas taskszinhas : sims ) {
            taskszinhas.sendMsg(message);
        }
    }
}