/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import Blockchain.Block;
import Blockchain.BlockChain;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author MisterZii
 */
public interface IRemoteNode extends Remote {

    String getName() throws RemoteException;
    
    void addNode(IRemoteNode node) throws RemoteException;

    List<IRemoteNode> getNodes() throws RemoteException;

    //void addBlock(Phone p) throws RemoteException;
    void addBlock(Block b) throws RemoteException;

    public BlockChain getBlockchain() throws RemoteException;

    public void mine(Block b) throws RemoteException;

    public void stopMining(Block b) throws RemoteException;
}
