/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.Set;

/**
 *
 * @author doctmanso
 */
public class Sign {

    static String algorithm = "SHA3-256WITHRSA";
    
    public static KeyPair generateKeysEC() throws NoSuchAlgorithmException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        return kpg.generateKeyPair();
    }

    public static byte[] signature(byte[] data, PrivateKey key) throws Exception {
        Signature sig = Signature.getInstance(algorithm,"BC");
        //Inicia a assinatura
        sig.initSign(key);
        //Assina os dados
        sig.update(data);
        //Devolve a assinatura
        return sig.sign();
    }

    public static boolean verify(byte[] data, byte[] signature, PublicKey key) {
        try {
            Signature sig = Signature.getInstance(algorithm);
            sig.initVerify(key);
            sig.update(data);
            return sig.verify(signature);
        } catch (Exception ex) {
            return false;
        }
    }
    
    
     public static void listAlgorithms() {
        //lista do fornecedores
        Provider providers[] = Security.getProviders();
        for (Provider provider : providers) {
            //Serviços fornecidos
            Set<Provider.Service> services = provider.getServices();
            for (Provider.Service service : services) {
                //Serviço de confidencialidade
                if (service.getType().equalsIgnoreCase("Signature")) {
                    System.out.printf("\n\t %-20s %-20s %s",
                            service.getProvider(), service.getType(), service.getAlgorithm());
                }
            }
        }
    }

}
