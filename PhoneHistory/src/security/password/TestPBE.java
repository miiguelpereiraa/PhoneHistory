/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security.password;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author doctmanso
 */
public class TestPBE {
    
    public static void main(String[] args) {
        try {
            String msg = "Hello World";            
            byte [] secret = PBE.encrypt(msg.getBytes(), "password");
            byte[] plain = PBE.decrypt(secret, "password");
            System.out.println("MSG = " + new String(plain));
        } catch (Exception ex) {
            Logger.getLogger(TestPBE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
