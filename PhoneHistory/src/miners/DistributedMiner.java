/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miners;

import Blockchain.Block;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author MisterZii
 */
public class DistributedMiner {

    Long nonce;
    AtomicBoolean isWorking;

    public DistributedMiner() throws NoSuchAlgorithmException {
        isWorking = new AtomicBoolean(false);
        Security.addProvider(new BouncyCastleProvider());
        
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking.set(isWorking);
    }

    public long getNonce() {
        return nonce;
    }

    public void stopMining() {
        isWorking.set(false);
    }

    public boolean isWorking() {
        return isWorking.get();
    }

    public void mine(Block blk, NonceFoundListener listener) throws Exception {
        
        MessageDigest hasher = MessageDigest.getInstance("SHA3-256");
        String prefix = String.format("%0" + blk.getSize() + "d", 0);
        System.out.println("Estou a minar");

        isWorking.set(true);
        while (isWorking.get()) {
            long num = ThreadLocalRandom.current().nextLong();
            String msg = blk.getPrevious() + blk.getFact() + num;
            
            byte[] h = hasher.digest(msg.getBytes());
            String txtH = Base64.getEncoder().encodeToString(h);

            if (txtH.startsWith(prefix)) {
                System.out.println("Encontrei");
                //nonce = num;
                blk.setNonce(num);
                blk.setHash(txtH);
                //isWorking.set(false);
                listener.onNonceFound(blk);
            }
        }
        System.out.println("Acabei");

    }
}
