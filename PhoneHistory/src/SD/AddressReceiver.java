/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import myUtils.RMI;

/**
 *
 * @author MisterZii
 */
public class AddressReceiver extends Thread {

    IRemoteNode nodes;
    final int SOCKET_PORT = 10010;
    String[] aux;
    

    public AddressReceiver(IRemoteNode nodes) {
        this.nodes = nodes;
        //start();
    }

    public void run() {

        try {
            byte[] array = new byte[2048];
            DatagramPacket data = new DatagramPacket(array, array.length);
            DatagramSocket socket = new DatagramSocket(SOCKET_PORT);
            while (true) {                
                socket.receive(data);
                String msg = new String(data.getData(),0,data.getLength());
                aux = msg.split(":");
                IRemoteNode node = (IRemoteNode) RMI.getRemote(aux[0], Integer.parseInt(aux[1]), "Node");
                nodes.addNode(node);
            }
        } catch (SocketException ex) {
            Logger.getLogger(AddressReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddressReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(AddressReceiver.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }
}
