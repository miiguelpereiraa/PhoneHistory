package Blockchain;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class BlockChain implements Serializable {

    private static final long SerialVersionUID = 201912111;

    //Blockchain
    //protected CopyOnWriteArrayList<Phone> chain = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Block> chain = new CopyOnWriteArrayList<>();

//    public void add(String data) throws NoSuchAlgorithmException, InterruptedException{
//        String prev = getLastBlock();
//        Block block = new Block(prev, data);
//        chain.add(block);
//    }
    /**
     * Adiciona um novo bloco à blockchain, dado um id e uma descrição
     *
     * @param imei String que representa o IMEI do telefone
     * @param desc String com uma descrição
     * @param marca String com a marca
     * @param modelo String com o modelo
     * @param pais String com o pais
     * @param rede String com a rede
     * @param reparacao String com a info sobre a reparação
     * @param material String com a info sobre o material
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    public void addPhone(String imei, String desc, String marca, String modelo, String pais, String rede, String reparacao, String material) throws NoSuchAlgorithmException, InterruptedException {
        String prev = getLastBlockHash();
        Phone block = new Phone(prev, imei, desc, marca, modelo, pais, rede, reparacao, material);
        chain.add(block);
    }

    public Phone getNewBlock(String imei, String desc, String marca, String modelo, String pais, String rede, String reparacao, String material) throws NoSuchAlgorithmException, InterruptedException {
        String prev = getLastBlockHash();
        return new Phone(prev, imei, desc, marca, modelo, pais, rede, reparacao, material);
    }

    public void addBlock(Block b) {
        //Falta verificar se o bloco é válido e se já existe na blockchain
        if (!chain.contains(b)) {
            chain.add(b);
        }
    }

    public void addBlockList(ArrayList<Block> blockList) {
        for (Block block : blockList) {
            chain.add(block);
        }
    }

    /**
     * Obtém o último bloco da blockchain
     *
     * @return String com o último bloco da blockchain
     */
    public String getLastBlockHash() {
        if (chain.isEmpty()) {
            return "";
        }
        return chain.get(chain.size() - 1).hash;
    }

    public Block getLastBlock() {
        if (chain.isEmpty()) {
            return null;
        }
        return chain.get(chain.size() - 1);
    }

    public ArrayList<Block> getBlocksFrom(Block b) {
        ArrayList<Block> aux = new ArrayList<>();
        int pos = 0;
        if (!b.equals(null)) {
            for (int i = 0; i < chain.size() - 1; i++) {
                if (chain.get(i).hash.equals(b.hash)) {
                    pos = i + 1;
                    break;
                }
            }
        }

        for (int i = pos; i < chain.size() - 1; i++) {
            aux.add(chain.get(i));
        }
        return aux;
    }

    public String getByImei(String imei) {
        ArrayList<Block> aux = new ArrayList<>();
        String result = new String();
        int pos = 0;
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).getImei().equals(imei)) {
                if(!aux.contains(chain.get(i)))
                    aux.add(chain.get(i));
            }
        }
        for (Block block : aux) {
            result.concat(block.getFact() + ";");
            //result += block.getFact() + ";";
        }
        //Retirar o ultimo ;
        if(result != "")
            return result.substring(0, result.length()-1);
        else
            return null;
    }

    /**
     * Imprime os blocos da blockchain
     */
    public void print() {
        for (Block block : chain) {
            System.out.println(block.toString());
        }
    }

    public String printBlocks() {
        String txt = "";
        for (Block blk : chain) {
            txt += blk.toString() + "\n" + "\n";
        }
        return txt;
    }

    public boolean contains(Block b) {
        for (Block block : chain) {
            if (block.fact.equals(b.fact)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return chain.size();
    }

    public boolean isEmpty() {
        return chain.isEmpty();
    }
}
