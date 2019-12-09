/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Timer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MisterZii
 */
public class TimeServer {

    ServerSocket timeServer;
    
    public static void main(String[] args) {
        try {
            new TimeServer();
        } catch (IOException ex) {
            Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TimeServer() throws IOException {
        timeServer = new ServerSocket(37);
        while (true) {
            Socket socket = timeServer.accept();
            System.out.println("Recebi pergunta");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int time = (int) (Instant.now().getEpochSecond() + 2208988800L);
                        byte[] bytes = ByteBuffer.allocate(4).putInt(time).array();
                        //Resposta
                        socket.getOutputStream().write(bytes);
                        socket.getOutputStream().flush();
                        //fechar
                        socket.close();
                        System.out.println("Dei resposta");
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    }

}
