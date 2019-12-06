package miners;


import Blockchain.Block;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import miners.MinerTHR;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class Miner {

    /**
     * Efectua a mineração do bloco enviado por parâmetro
     * @param b Bloco a ser minerado
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException 
     */
    public static void mine(Block b) throws NoSuchAlgorithmException, InterruptedException {

        AtomicBoolean isDone = new AtomicBoolean(false);
        AtomicLong nonce = new AtomicLong(0);
        
        int procs = Runtime.getRuntime().availableProcessors();
        MinerTHR[] miner = new MinerTHR[procs];
        
        for (int i = 0; i < miner.length; i++) {
            miner[i] = new MinerTHR(isDone, nonce, b);
            miner[i].start();
        }
        
        for (int i = 0; i < miner.length; i++) {
            miner[i].join();
        }
        
        b.setNonce(nonce.get());
        
        Security.addProvider(new BouncyCastleProvider());
        
        MessageDigest hasher = MessageDigest.getInstance("SHA3-256");
        byte[] bh = hasher.digest((b.getPrevious() + b.getFact() + b.getNonce()).getBytes());
        b.setHash(Base64.getEncoder().encodeToString(bh));
    }
}
