/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.MulticastSocket;
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

    RemoteNode nodes;
    ServiceGUI gui;
    String myIP;
    final int SOCKET_PORT = 10010;
    String[] aux;
    

    public AddressReceiver(RemoteNode nodes, ServiceGUI gui, String ip) {
        this.nodes = nodes;
        this.gui = gui;
        this.myIP = ip;
    }

    public void run() {

        try {
            byte[] array = new byte[2048];
            DatagramPacket data = new DatagramPacket(array, array.length);
            DatagramSocket socket = new DatagramSocket(SOCKET_PORT);
            //MulticastSocket socket = new MulticastSocket(SOCKET_PORT);
            //InetAddress addr = InetAddress.getByName("239.1.1.1");
            //socket.joinGroup(addr);
            while (true) {                
                socket.receive(data);
                String msg = new String(data.getData(),0,data.getLength());
                if(!msg.equals(myIP)){
                    //gui.displayMessage("Conexão", "Conexão recebida");
                    IRemoteNode node = (IRemoteNode) RMI.getRemote(msg, SOCKET_PORT, "Node");
                    nodes.addNode(node);
                    gui.updateList();
                }
               
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
