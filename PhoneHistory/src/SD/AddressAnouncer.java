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

    String ip;
    final int SOCKET_PORT = 10010;
    
    public AddressAnouncer(String ip) {
        this.ip = ip;
    }
    
    public void run(){
        
        try {
            DatagramSocket socket = new DatagramSocket();
            //InetAddress addr = InetAddress.getByName("239.1.1.1");
            InetAddress addr = InetAddress.getByName("255.255.255.255");
            DatagramPacket data = new DatagramPacket(ip.getBytes(), ip.getBytes().length,addr, SOCKET_PORT);
            while (true) {                
                socket.send(data);
                sleep(1000);
            }
        } catch (Exception ex) {
            Logger.getLogger(AddressAnouncer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
