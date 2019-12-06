/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import Blockchain.Block;
import Blockchain.BlockChain;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
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
    DistributedMiner miner = new DistributedMiner();

    int port;
    String name;

    public RemoteNode(int port, ServiceGUI gui, BlockChain bc) throws RemoteException, UnknownHostException, NoSuchAlgorithmException, InterruptedException {
        super(port);
        this.gui = gui;
        nodes = new CopyOnWriteArraySet<>();
        this.port = port;
        this.name = InetAddress.getLocalHost().getHostName();
        this.bc = bc;
        //Temporário
//        bc.addPhone("0", "Primeiro Telefone", "Apple", "iPhone", "Portugal", "Vodafone", "Venda", "Inteiro");
//        bc.addPhone("1", "Segundo Telefone", "Samsung", "S10", "Portugal", "Desbloqueado", "Venda", "Inteiro");
//        bc.addPhone("2", "Terceiro Telefone", "Huawei", "P10", "Espanha", "MEO", "Venda", "Peças");
//        bc.addPhone("3", "Quarto Telefone", "Apple", "iPhone", "Portugal", "Vodafone", "Venda", "Inteiro");
    }

    @Override
    public String getName() throws RemoteException {
        return name + ":" + port;
    }

    @Override
    public void addNode(IRemoteNode node) throws RemoteException {
        nodes.add(node);
        gui.displayMessage("Add node", "node" + node.getName());
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
    public void mine(Block b) throws RemoteException {

        if (miner.isWorking()) {
            gui.displayMessage("", "O mineiro está ocupado");
            return;
        }
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

        //Se já tiver o bloco, este não é adicionado
        if (bc.contains(b)) {
            return;
        }

        bc.addBlock(b);

        //A rede para de minar (Problema?????????)
        for (IRemoteNode node : nodes) {
            node.stopMining(b);
        }

    }
}
