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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import miners.DistributedMiner;
import Security.Asimetric;
import Security.PBE;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import security.Sign;

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
    public String getByImei(String imei) throws RemoteException {
        return bc.getByImei(imei);
    }

    @Override
    public void syncBlockchain() throws RemoteException {
        long timestamp = 0;
        String nodeName = "";
        //Verifica qual o node que possui a blockchain com o último bloco mais recente;
        for (IRemoteNode node : nodes) {
            if (node.getBlockchain().size() != 0) {
                if (node.getLastBlock().getTimestamp() > timestamp) {
                    timestamp = node.getLastBlock().getTimestamp();
                    nodeName = node.getName();
                }
            }

        }
        //Sincronizar a blockchain
        for (IRemoteNode node : nodes) {
            if (node.getName().equals(nodeName)) {
                if (bc.isEmpty()) {
                    bc = node.getBlockchain();
                } else {
                    bc.addBlockList(node.getBlocksFrom(bc.getLastBlock()));
                }
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

        bc.addBlock(b);

        //A rede para de minar
        for (IRemoteNode node : nodes) {
            node.stopMining(b);
        }

    }

    @Override
    public void saveBlockchain() throws RemoteException {
        try {
            FileOutputStream fOS = new FileOutputStream("bc");
            ObjectOutputStream objOS = new ObjectOutputStream(fOS);
            objOS.writeObject(bc);
            objOS.close();
        } catch (Exception ex) {
            gui.displayException("Guardar Blockchain", ex);
        }
    }

    @Override
    public void loadBlockchain() throws RemoteException {
        try {
            File file = new File("bc");
            if (file.exists()) {
                FileInputStream fIS = new FileInputStream(file);
                ObjectInputStream objIS = new ObjectInputStream(fIS);
                bc = (BlockChain) objIS.readObject();
                objIS.close();
            }
        } catch (Exception ex) {
            gui.displayException("Carregar Blockchain", ex);
        }
    }

    @Override
    public boolean register(String hUser, String hPass) throws RemoteException {
        try {
            //Gerar o par de chaves publica e privada
            KeyPair kp = Asimetric.generateKeyPair(2048);
            //Guardar a chave publica
            Asimetric.saveKey(kp.getPublic(), hUser + ".pub");
            //Encriptar a chave privada com a password introduzida pelo utilizador
            byte[] secret = PBE.encrypt(kp.getPrivate().getEncoded(), hPass);
            //Guardar a chave privada encriptada com a password
            Files.write(Paths.get(hUser + ".priv"), secret);
            return true;
        } catch (Exception ex) {
            gui.displayException("Registo", ex);
            return false;
        }
    }

    @Override
    public boolean login(String hUser, String hPass) throws RemoteException {
        try {
            //Verifica se o utilizador está registado            
            if (Files.exists(Paths.get(hUser + ".pub")) && Files.exists(Paths.get(hUser + ".priv"))) {
                //Carrega o ficheiro da chave privada encriptada
                byte[] privateKey = Files.readAllBytes(Paths.get(hUser + ".priv"));
                //Desencripta a chave privada
                byte[] decrypted = PBE.decrypt(privateKey, hPass);
                //Se a chave privada foi desencriptada com sucesso, a password fornecida está correcta
                return true;
            } else {
                //Pergunta aos restantes nodos da rede se o utilizador está registado
                for (IRemoteNode node : nodes) {
                    if (node.verifyExistingUser(hUser, hPass)) {
                        return true;
                    }
                }
                //Utilizador não está registado ou as credenciais de acesso estão erradas
                throw new RuntimeException("Caso esteja registado, p.f. verifique as suas credenciais, caso contrário, p.f. efectue o registo.");
            }
        } catch (Exception ex) {
            gui.displayException("Login", ex);
            return false;
        }
    }

    @Override
    public boolean verifyExistingUser(String hUser, String hPass) throws RemoteException {
        try {
            if (Files.exists(Paths.get(hUser + ".pub")) && Files.exists(Paths.get(hUser + ".priv"))) {
                //Carrega o ficheiro da chave privada encriptada
                byte[] privateKey = Files.readAllBytes(Paths.get(hUser + ".priv"));
                //Desencripta a chave privada
                byte[] decrypted = PBE.decrypt(privateKey, hPass);
                //Se a chave privada foi desencriptada com sucesso, a password fornecida está correcta
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            gui.displayException("Login", ex);
            return false;
        }
    }

    @Override
    public void sign(Block b, String user, String hPass) throws RemoteException {
        if (Files.exists(Paths.get(user + ".pub")) && Files.exists(Paths.get(user + ".priv"))) {
            try {
                //Carrega o ficheiro da chave privada encriptada
                byte[] privateKey = Files.readAllBytes(Paths.get(user + ".priv"));
                //Desencripta a chave privada
                byte[] decrypted = PBE.decrypt(privateKey, hPass);
                //Obtém a chave privada
                PrivateKey privKey = Asimetric.getPrivateKey(decrypted);
                //Converte o fact do bloco para array de bytes
                byte[] data = b.getFact().getBytes();
                //Assina os dados com a chave privada
                byte[] sign = Sign.signature(data, privKey);
                //Atribui a assinatura ao bloco
                b.setSignature(Base64.getEncoder().encodeToString(sign));
            } catch (Exception ex) {
                gui.displayException("Sign", ex);
            }
        }else{
            //Solicitar aos restantes nodes da rede se possuem a chave necessária para a assinatura
            for (IRemoteNode node : nodes) {
                    if (node.verifyExistingUser(user, hPass)) {
                        node.sign(b, user, hPass);
                    }
                }
        }

    }

}
