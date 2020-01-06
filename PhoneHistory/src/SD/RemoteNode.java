/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import Blockchain.Block;
import Blockchain.BlockChain;
import Timer.TimeServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import miners.DistributedMiner;

/**
 *
 * @author MisterZii
 */
public class RemoteNode extends UnicastRemoteObject implements IRemoteNode {

    public static final String NAME = "Node";

    ServiceGUI gui;
    CopyOnWriteArraySet<IRemoteNode> nodes;
    BlockChain bc;
    DistributedMiner miner;
    
    TimeServer tm;

    int port;
    String name;

    public RemoteNode(int port, ServiceGUI gui, BlockChain bc) throws Exception {
        super(port);
        this.gui = gui;
        nodes = new CopyOnWriteArraySet<>();
        this.port = port;
        this.name = InetAddress.getLocalHost().getHostName();
        this.bc = bc;
        miner = new DistributedMiner();
    }

    @Override
    public String getName() throws RemoteException {
        return name + ":" + port;
    }

    @Override
    public void addNode(IRemoteNode node) throws RemoteException {
        nodes.add(node);
        //gui.displayMessage("Add node", "node" + node.getName());
    }
    
    @Override
    public void removeNode(IRemoteNode node) throws RemoteException {
        nodes.remove(node);
        gui.displayMessage("Remove node", "node" + node.getName());
    }


    @Override
    public List<IRemoteNode> getNodes() throws RemoteException {
        return new ArrayList(nodes);
    }

    @Override
    public void addBlock(Block b) throws RemoteException {
//        try {
//            bc.addBlock(p);
//        } catch (Exception ex) {
//            gui.displayException("Adicionar bloco", ex);
//        } 
        try {
            bc.addBlock(b);
        } catch (Exception ex) {
            gui.displayException("Adicionar bloco", ex);
        }
    }

    @Override
    public BlockChain getBlockchain() throws RemoteException {
        return bc;
    }
    
    @Override
    public ArrayList<Block> getBlocksFrom(Block b) throws RemoteException {
        return bc.getBlocksFrom(b);
    }
    
    @Override
    public Block getLastBlock() throws RemoteException {
        return bc.getLastBlock();
    }
    
    @Override
    public String getByImei(String imei) throws RemoteException{
        return bc.getByImei(imei);
    }
    
    @Override
    public void syncBlockchain() throws RemoteException {
        long timestamp = 0;
        String nodeName = "";
        //Verifica qual o node que possui a blockchain com o último bloco mais recente;
        for (IRemoteNode node : nodes) {
            if(node.getBlockchain().size() != 0){
                if (node.getLastBlock().getTimestamp() > timestamp) {
                    timestamp = node.getLastBlock().getTimestamp();
                    nodeName = node.getName();
                }
            }
            
        }
        //Sincronizar a blockchain
        for (IRemoteNode node : nodes) {
            if(node.getName().equals(nodeName)){
                if(bc.isEmpty())
                    bc = node.getBlockchain();
                else
                    bc.addBlockList(node.getBlocksFrom(bc.getLastBlock()));
            }
        }
    }

    @Override
    public void mine(Block b) throws RemoteException {

        if (miner.isWorking()) {
            gui.displayMessage("", "O mineiro está ocupado");
            return;
        }
        gui.setWorking(true);
        new Thread(
                () -> {
                    try {
                        miner.setIsWorking(true);
                        System.out.println("THR " + Thread.currentThread().getName() + port);

                        //Dá o bloco para a rede minar
                        for (IRemoteNode node : nodes) {
                            node.mine(b);
                        }
                        //Começa a minar
                        miner.mine(b, gui);
                    } catch (Exception ex) {
                        gui.displayException("Minar", ex);
                    }
                }
        ).start();

    }

    @Override
    public void stopMining(Block b) throws RemoteException {
        miner.stopMining();
        gui.setWorking(false);

        //Se já tiver o bloco, este não é adicionado
        if (bc.contains(b)) {
            return;
        }
//        try {
//            b.setTimestamp(getTimeTCP("192.168.1.180"));
//        } catch (IOException ex) {
//            Logger.getLogger(RemoteNode.class.getName()).log(Level.SEVERE, null, ex);
//        }

        bc.addBlock(b);

        //A rede para de minar
        for (IRemoteNode node : nodes) {
            node.stopMining(b);
        }

    }

//    public static long getTimeTCP(String host) throws IOException{
//        Socket timeServer = new Socket(host, 37);
//        DataInputStream input = new DataInputStream(timeServer.getInputStream());
//        long time = (input.readInt() & 0xffffffffL);//convert to unsigned int
//        timeServer.close();
//        return (time - 2208988800L) * 1000L;
//    }

    @Override
    public void saveBlockchain() throws RemoteException {
        try {
            FileOutputStream fOS = new FileOutputStream("bc");
            ObjectOutputStream objOS = new ObjectOutputStream(fOS);
            objOS.writeObject(bc);
            objOS.close();
        } catch (Exception ex ) {
            gui.displayException("Guardar Blockchain", ex);
        } 
    }

    @Override
    public void loadBlockchain() throws RemoteException {
         try {
             File file = new File("bc");
             if(file.exists()){
                FileInputStream fIS = new FileInputStream(file);
                ObjectInputStream objIS = new ObjectInputStream(fIS);
                bc = (BlockChain)objIS.readObject();
                objIS.close();
             }
        } catch (Exception ex) {
            gui.displayException("Carregar Blockchain", ex);
        } 
    }

    
                
}
