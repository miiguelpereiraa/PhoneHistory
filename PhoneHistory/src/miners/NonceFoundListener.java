/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miners;

import Blockchain.Block;

/**
 *
 * @author MisterZii
 */
public interface NonceFoundListener {
    
    public void onNonceFound(Block b);
}
