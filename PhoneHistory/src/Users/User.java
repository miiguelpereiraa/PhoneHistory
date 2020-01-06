package Users;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyPair;
import security.confident.Asimetric;
import security.password.PBE;
/**
 *
 * @author MisterZii
 */
public class User {

    private String name;
    private Key pubKey;
    private Key privKey;

    //Registo do utilizador
    public void register(String user, String passwd) throws Exception {
        this.name = user;
        //Gerar o par de chaves publica e privada
        KeyPair kp = Asimetric.generateKeyPair(2048);
        //Guardar a chave publica
        Asimetric.saveKey(kp.getPublic(), user+".pub");
        //Encriptar a chave privada com a password introduzida pelo utilizador
        byte[] secret = PBE.encrypt(kp.getPrivate().getEncoded(), passwd);
        //Guardar a chave privada encriptada com a password
        Files.write(Paths.get(user+".priv"),secret);
    }

    //Login do utilizador
    public void login(String user, String passwd) throws Exception {
        //Verifica se o utilizador está registado
        if(Files.exists(Paths.get(user+".pub")) && Files.exists(Paths.get(user+".priv"))){
            //Obtém a chave pública
            pubKey = Asimetric.loadPublicKey(user+".pub");
            //Carrega o ficheiro da chave privada encriptada
            byte[] privateKey = Files.readAllBytes(Paths.get(user+".priv"));
            //Desencripta a chave privada
            byte[] decrypted = PBE.decrypt(privateKey, passwd);
            //Obtém a chave privada
            privKey = Asimetric.getPrivateKey(decrypted);
        }else{
            throw new RuntimeException("Wrong User or wrong password");
        }
    }

    public static void encrypt() {

    }

    public static void decrypt() {

    }
    
    public static void main(String[] args) {
        try {
            User u = new User();
            //u.register("ana", "123");
            u.login("ana", "123");
          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
