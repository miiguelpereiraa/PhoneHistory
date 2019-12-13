/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MisterZii
 */
public class AddressAnouncer extends  Thread{

    IRemoteNode nodes;
    String address;
    final int SOCKET_PORT = 10010;
    
    public AddressAnouncer(String address, IRemoteNode nodes) {
        this.nodes = nodes;
        this.address = address;
        //start();
    }
    
    public void run(){
        
        try {
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket data = new DatagramPacket(nodes.getName().getBytes(), nodes.getName().getBytes().length, SOCKET_PORT);
            while (true) {                
                socket.send(data);
                sleep(1000);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddressAnouncer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
