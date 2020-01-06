package Blockchain;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Block implements Serializable {
    
     private static final long SerialVersionUID = 201912112;

    protected String previous;

    protected String fact;

    protected long timestamp;

    protected String hash;
    protected long nonce;
    protected int size;

    /**
     * Constroi um bloco usando o previous, o facte o size
     *
     * @param previous hash do bloco anterior
     * @param data informação a guardar no bloco
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    public Block(String previous, String data) throws NoSuchAlgorithmException, InterruptedException {
        if (previous == null) {
            this.previous = "0";
        } else {
            this.previous = previous;
        }
        this.fact = data;
        this.size = 4;
    }

    /**
     * Obtém a hash do bloco anterior
     *
     * @return String da hash do bloco anterior
     */
    public String getPrevious() {
        return previous;
    }

    /**
     * Obtém o fact do bloco
     *
     * @return String do fact do bloco
     */
    public String getFact() {
        return fact;
    }
    
    public String getImei(){
        return fact.split("#")[0];
    }

    /**
     * Definir a hash do bloco
     *
     * @param hash hash a definir
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Obtém o nonce do bloco
     *
     * @return long do nonce do bloco
     */
    public long getNonce() {
        return nonce;
    }

    /**
     * Definir o nonce do bloco
     *
     * @param nonce
     */
    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    /**
     * Obtém o size do bloco
     *
     * @return int do size do bloco
     */
    public int getSize() {
        return size;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    
    
    //VER ESTE MÉTODO
    public String calcHash() throws NoSuchAlgorithmException {
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        String msg = (previous + fact + nonce);
        byte[] hbytes = hasher.digest(msg.getBytes());
        return Base64.getEncoder().encodeToString(hbytes);
    }

    /**
     * Verifica se a hash do bloco é válida
     *
     * @return true ou false caso a hash seja válida ou não
     * @throws NoSuchAlgorithmException
     */
    public boolean isValid() throws NoSuchAlgorithmException {
        return calcHash().equals(hash);
    }

    /**
     * Imprime a informação do bloco
     *
     * @return String com a informação sobre o bloco
     */
    @Override
    public String toString() {
        String info = "";
        String[] parts = fact.split("#");
        for (String part : parts) {
            info += " " + part + ",";
        }
        return String.format("%20s | %1s |(%6d)| %20s ", previous, info, nonce, hash);
    }

//    public boolean equals(Object obj) {
//        return obj.toString().equals(obj.toString());
//    }
    public boolean equals(Block b) {
        return this.toString().equals(b.toString());
    }

}
