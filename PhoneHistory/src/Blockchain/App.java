package Blockchain;

import Blockchain.Phone;
import Blockchain.BlockChain;
import java.security.NoSuchAlgorithmException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class App {
    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        BlockChain bc = new BlockChain();

        bc.addPhone("0", "Primeiro Telefone","Apple","iPhone","Portugal","Vodafone","Venda","Inteiro");
        bc.addPhone("1", "Segundo Telefone","Samsung","S10","Portugal","Desbloqueado","Venda","Inteiro");
        bc.addPhone("2", "Terceiro Telefone","Huawei","P10","Espanha","MEO","Venda","Peças");
        bc.addPhone("3", "Quarto Telefone","Apple","iPhone","Portugal","Vodafone","Venda","Inteiro");
        
          
         bc.printBlocks();
    
        //Pesquisar por IMEI a info de um
        
//        for(Phone ph : bc.getBlockById("1")){
//         System.out.println(ph.getInfo());
//       }
//        
        
    }
}
