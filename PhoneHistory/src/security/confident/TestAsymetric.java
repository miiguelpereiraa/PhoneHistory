/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security.confident;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;

/**
 *
 * @author doctmanso
 */
public class TestAsymetric {

//     Simetric.saveKey(keys.getPrivate(), "k1Priv.key");
//            Simetric.saveKey(keys.getPublic(), "k1Pub.key");
//            
//            Key k2 = Asimetric.loadPublicKey("k1Pub.key");
//            Key k1 = Asimetric.loadPrivateKey("k1Priv.key");
    public static void main(String[] args) {
        try {

            String msg = "Hello World";

            System.out.println("MENSAGEM = " + msg);
           
            KeyPair keys = Asimetric.generateKeyPair(1024);
            
            byte[] secret = Simetric.encrypt(msg.getBytes(), keys.getPublic());
            System.out.println("SEGREDO : " + Base64.getEncoder().encodeToString(secret));
            
            byte[] plain = Simetric.decrypt(secret, keys.getPrivate());

            System.out.println("ORIGINAL = " + new String(plain));
            
            
        } catch (Exception ex) {
            Logger.getLogger(TestAsymetric.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
