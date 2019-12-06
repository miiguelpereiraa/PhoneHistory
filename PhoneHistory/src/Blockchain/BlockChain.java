package Blockchain;


import Blockchain.Block;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class BlockChain {
    
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
    public void addPhone(String imei, String desc, String marca, String modelo, String pais, String rede, String reparacao, String material) throws NoSuchAlgorithmException, InterruptedException{
        String prev = getLastBlock();
        Phone block = new Phone(prev, imei, desc, marca, modelo, pais, rede, reparacao, material);
        chain.add(block);
    }
    
    public Phone getNewBlock(String imei, String desc, String marca, String modelo, String pais, String rede, String reparacao, String material) throws NoSuchAlgorithmException, InterruptedException{
        String prev = getLastBlock();
        return new Phone(prev, imei, desc, marca, modelo, pais, rede, reparacao, material);
    }
    
//    public void addBlock(Phone p) throws NoSuchAlgorithmException, InterruptedException{
//        //String prev = getLastBlock();
//        //Phone phone = new Phone(prev, p.getImei(), p.getDesc(), p.getMarca(), p.getModelo(), p.getPais(), p.getRede(), p.getReparacao(), p.getMaterial());
//        chain.add(p);
//    }
//    
    public void addBlock(Block b){
        //String prev = getLastBlock();
        //Phone phone = new Phone(prev, p.getImei(), p.getDesc(), p.getMarca(), p.getModelo(), p.getPais(), p.getRede(), p.getReparacao(), p.getMaterial());
        //Falta verificar se o bloco é válido e se já existe na blockchain
        if(!chain.contains(b))
            chain.add(b);
    }
    
    /**
     * Obtém o último bloco da blockchain
     * @return String com o último bloco da blockchain
     */
    public String getLastBlock(){
        if(chain.isEmpty())
            return "";
        return chain.get(chain.size() - 1).hash;
    }
    
    /**
     * Imprime os blocos da blockchain
     */
    public void print(){
        for (Block block : chain) {
            System.out.println(block.toString());
        }
    }
    
    /**
     * Imprime os Phones da blockchain
     */
//    public String printPhones(){
//        String txt = "";
//        for (Phone phone : chain) {
//            txt += phone.toString() + "\n" + "\n";
//            //System.out.println(phone.toString());
//        }
//        return txt;
//    }
    
    public String printBlocks(){
        String txt = "";
        for (Block blk : chain) {
            txt += blk.toString() + "\n" + "\n";
        }
        return txt;
    }
    
    /**
     * Obtém a lista de blocos com o id enviado por parâmetro
     * @param imei Representa o IMEI do telefone
     * @return Lista de blocos que correspondem ao id fornecido
     */
//    public List<Phone> getBlockById(int id){
//        ArrayList<Phone> phones = new ArrayList<>();
//        for (Phone phone : chain) {
//            if(phone.getId() == id)
//                phones.add(phone);
//        }
//        return phones;
//    }
    
//    public List<Phone> getBlockById(String imei){
//        ArrayList<Phone> phones = new ArrayList<>();
//        for (Phone phone : chain) {
//            if(phone.getImei().equals(imei))
//                phones.add(phone);
//        }
//        return phones;
//    }
    
    public boolean contains(Block b){
        for (Block block : chain) {
            if(block.hash.equals(b.hash))
                return true;
        }
        return false;
    }
}
