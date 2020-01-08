/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Blockchain;

import java.security.NoSuchAlgorithmException;


public class Phone extends Block{
    
    private String imei;
    
    private String desc;

    private String marca; 
    private String modelo; 
    private String pais; 
    private String rede; 
    private String reparacao; 
    private String material; 
    
    /**
     * Constrói um bloco com informação sobre o telemóvel, dado o hash do bloco anterior, o id do telefone e uma descrição
     * @param previous Hash do bloco anterior
     * @param imei - Representa o IMEI do telefone 
     * @param desc Descrição 
     * @param marca - Marca do telefone
     * @param modelo - Modelo do Telefone 
     * @param pais - Pais onde o telefone foi vendido 
     * @param rede - Rede do telefone
     * @param reparacao - Informações sobre a reparação
     * @param material - Informação sobre o material colocado 
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException 
     */
    public Phone(String previous, String imei, String desc, String marca, String modelo, String pais, String rede, String reparacao, String material) throws NoSuchAlgorithmException, InterruptedException {
        super(previous, imei+"#"+marca+"#"+modelo+"#"+pais+"#"+rede+"#"+reparacao+"#"+material+"#"+desc);
        this.imei = imei;
        this.desc = desc;
        this.marca= marca; 
        this.modelo = modelo; 
        this.pais= pais;
        this.rede= rede;
        this.reparacao= reparacao; 
        this.material= material; 
       
    }

    /**
     * Obtém o id de um bloco
     * @return int que representa o id do bloco
     */
    public String getImei() {
        return imei;
    }

    public String getDesc() {
        return desc;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPais() {
        return pais;
    }

    public String getRede() {
        return rede;
    }

    public String getReparacao() {
        return reparacao;
    }

    public String getMaterial() {
        return material;
    }
    
    public String getInfo(){
        return this.imei + " "+ this.marca +" "+  this.modelo +" "+  this.pais + " "+ this.rede+ " "+ this.reparacao + " "+ this.material;
    }
    
    /**
     * Imprime a informação de Phone
     * @return String com a informação sobre o Phone
     */
    @Override
    public String toString(){
        return String.format("%20s | %20s , %10s, %10s, %10s, %10s, %10s, %10s, %10s |(%6d)| %20s ", previous, imei, desc,marca, modelo, pais, rede, reparacao, material ,nonce, hash);
    }
    
    
}
