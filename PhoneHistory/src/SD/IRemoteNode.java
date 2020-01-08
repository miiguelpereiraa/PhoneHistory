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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MisterZii
 */
public interface IRemoteNode extends Remote {

    public String getName() throws RemoteException;
    
    public void addNode(IRemoteNode node) throws RemoteException;
    public void removeNode(IRemoteNode node) throws RemoteException;
    public List<IRemoteNode> getNodes() throws RemoteException;

    public void addBlock(Block b) throws RemoteException;

    public BlockChain getBlockchain() throws RemoteException;
    public ArrayList<Block> getBlocksFrom(Block b) throws RemoteException;
    public String getByImei(String imei, String user) throws RemoteException;
    public Block getLastBlock() throws RemoteException;
    public void syncBlockchain() throws RemoteException;
    
    public void saveBlockchain() throws RemoteException;
    public void loadBlockchain() throws RemoteException;

    public void mine(Block b) throws RemoteException;
    public void sign(Block b, String user, String hPass) throws RemoteException;
    public void verifySign(Block b, String user) throws RemoteException;
    public void stopMining(Block b) throws RemoteException;
    
    public boolean register(String hUser, String hPass) throws RemoteException;
    public boolean login(String hUser, String hPass) throws RemoteException;
    public boolean verifyExistingUser(String hUser) throws RemoteException;
}
