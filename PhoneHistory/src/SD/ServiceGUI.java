/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SD;

import Blockchain.Block;
import Blockchain.BlockChain;
import Blockchain.Phone;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import miners.NonceFoundListener;
import myUtils.RMI;

/**
 *
 * @author MisterZii
 */
public class ServiceGUI extends javax.swing.JFrame implements NonceFoundListener {

    IRemoteNode remoteObject;
    RemoteNode myObject;

    BlockChain bc;

    ServerSocket clientListener;

    public String doService(String... param) throws NoSuchAlgorithmException, InterruptedException, RemoteException {
        String cmd = param[0].toUpperCase();
        switch (cmd) {
            case "LOGIN":
                String userLog = param[1];
                String hPassLog = param[2];
                boolean logSucess = myObject.login(userLog, hPassLog);
                if (logSucess) {
                    return "Login efectuado com sucesso";
                } else {
                    return "Login efectuado sem sucesso. P.f. verifique as suas credenciais ou registe-se na aplicação.";
                }
            case "REGISTO":
                String userReg = param[1];
                String hPassReg = param[2];
                boolean regSucess = myObject.register(userReg, hPassReg);
                if (regSucess) {
                    return "Registo efectuado com sucesso";
                } else {
                    return "Registo efectuado sem sucesso. P.f. tente novamente";
                }
            case "ADDREG":
                String user = param[1];
                String hashPass = param[2];
                Phone p = myObject.bc.getNewBlock(
                        param[3],
                        param[4],
                        param[5],
                        param[6],
                        param[7],
                        param[8],
                        param[9],
                        param[10]);
                myObject.sign(p, user, hashPass);
                myObject.mine(p);
//                while(((Block)p).getHash().equals("")){
//                }
                return "Informação registada";
            case "PESQUISAR":
                String result = myObject.getByImei(param[1]);
                if (result != "") {
                    return result;
                } else {
                    return null;
                }
            default:
                return "UNKNOWN COMMAND:" + cmd;
        }
    }

    public void startClientListener(int port) {
        new Thread(
                () -> {
                    try {
                        //código executado pela Thread
                        //Ligar o listener
                        clientListener = new ServerSocket(port);
                        while (true) {
                            //Escutar os clientes
                            Socket client = clientListener.accept();
                            //Extrair o endereço do cliente
                            String txtClient = client.getInetAddress() + ":" + client.getPort();
                            //Informar a interface
                            //displayMessage("Client accepted", txtClient);
                            //Abrir as streams IO
                            DataInputStream in = new DataInputStream(client.getInputStream());
                            DataOutputStream out = new DataOutputStream(client.getOutputStream());
                            //Ler a a mensagem
                            //String msg = in.readUTF();
                            byte[] b = Base64.getDecoder().decode(in.readUTF());
                            String msg = new String(b);
                            //Executar o serviço
                            msg = doService(msg.split(" "));
                            //Devolver a resposta
                            out.writeUTF(msg);
                            //Fechar as streams
                            in.close();
                            out.close();
                            client.close();
                            //displayMessage("Service Done", txtClient);
                        }
                    } catch (Exception ex) {
                        displayException("Cliente listener", ex);
                    }
                }).start();
    }

    /**
     * Creates new form ServiceGUI
     */
    public ServiceGUI() {
        initComponents();
        txtMinAnim.setVisible(false);
        bc = new BlockChain();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtServerAddress = new javax.swing.JTextField();
        txtServerPort = new javax.swing.JTextField();
        btStartServer = new javax.swing.JButton();
        btDisconnect = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDisplay = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        listNodes = new javax.swing.JList<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtImei = new javax.swing.JTextField();
        btAddBlock = new javax.swing.JButton();
        txtMarca = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtRede = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPais = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtRep = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtMat = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtBlockchain = new javax.swing.JTextPane();
        btGetBlockchain = new javax.swing.JButton();
        btSaveBC = new javax.swing.JButton();
        txtMinAnim = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtServerAddress.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerAddress.setText("localhost");
        txtServerAddress.setBorder(javax.swing.BorderFactory.createTitledBorder("Address"));
        txtServerAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtServerAddressActionPerformed(evt);
            }
        });

        txtServerPort.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtServerPort.setText("10010");
        txtServerPort.setBorder(javax.swing.BorderFactory.createTitledBorder("Port Number"));

        btStartServer.setText("Start Server");
        btStartServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btStartServerActionPerformed(evt);
            }
        });

        btDisconnect.setText("Disconnect");
        btDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDisconnectActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtServerAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtServerPort, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btStartServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btDisconnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtServerPort)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btStartServer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btDisconnect)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 270, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtLog.setColumns(20);
        txtLog.setRows(5);
        txtLog.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Log", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jScrollPane1.setViewportView(txtLog);

        txtDisplay.setColumns(20);
        txtDisplay.setRows(5);
        txtDisplay.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Display", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        jScrollPane2.setViewportView(txtDisplay);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );

        listNodes.setEnabled(false);
        jScrollPane3.setViewportView(listNodes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 148, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Rede", jPanel1);

        jLabel1.setText("IMEI:");

        txtImei.setText("Teste");
        txtImei.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImeiActionPerformed(evt);
            }
        });

        btAddBlock.setText("add Block");
        btAddBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddBlockActionPerformed(evt);
            }
        });

        txtMarca.setText("Teste");
        txtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMarcaActionPerformed(evt);
            }
        });

        jLabel7.setText("Marca:");

        txtModelo.setText("Teste");

        jLabel8.setText("Modelo:");

        txtRede.setText("Teste");
        txtRede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRedeActionPerformed(evt);
            }
        });

        jLabel9.setText("Rede:");

        txtPais.setText("Teste");

        jLabel10.setText("País:");

        txtRep.setText("Teste");

        jLabel11.setText("Reparação:");

        txtMat.setText("Teste");

        jLabel12.setText("Material:");

        jLabel13.setText("Descrição");

        txtDesc.setText("Teste");

        jScrollPane4.setViewportView(txtBlockchain);

        btGetBlockchain.setText("get blockchain");
        btGetBlockchain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btGetBlockchainActionPerformed(evt);
            }
        });

        btSaveBC.setText("save blockchain");
        btSaveBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveBCActionPerformed(evt);
            }
        });

        txtMinAnim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/myUtils/giphy.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMarca, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                        .addComponent(txtModelo)
                                        .addComponent(txtRede)
                                        .addComponent(txtMat)
                                        .addComponent(txtPais)
                                        .addComponent(txtRep)
                                        .addComponent(txtDesc))))
                            .addComponent(jLabel13))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btGetBlockchain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAddBlock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(btSaveBC)))
                    .addComponent(txtMinAnim))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtImei, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(btAddBlock))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(txtRede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btGetBlockchain)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(txtRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(btSaveBC)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtMat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addGap(35, 35, 35)
                        .addComponent(txtMinAnim, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Serviço", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtImeiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImeiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtImeiActionPerformed

    private void btAddBlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddBlockActionPerformed
        new Thread(
                () -> {
                    try {
                        displayMessage("Add Block", "A adicionar o bloco");
                        Phone p = myObject.bc.getNewBlock(
                                txtImei.getText(),
                                txtDesc.getText(),
                                txtMarca.getText(),
                                txtModelo.getText(),
                                txtPais.getText(),
                                txtRede.getText(),
                                txtRep.getText(),
                                txtMat.getText());
                        myObject.mine(p);
                        displayMessage("", "Bloco adicionado");
                    } catch (Exception ex) {
                        displayException("Add block", ex);
                    }
                }).start();
    }//GEN-LAST:event_btAddBlockActionPerformed

    private void txtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMarcaActionPerformed

    private void txtRedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRedeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRedeActionPerformed

    private void btGetBlockchainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btGetBlockchainActionPerformed
        new Thread(
                () -> {
                    try {
                        txtBlockchain.setText(myObject.getBlockchain().printBlocks());
                    } catch (RemoteException ex) {
                        displayException("Display Blockchain", ex);
                    }
                }).start();
    }//GEN-LAST:event_btGetBlockchainActionPerformed

    private void btStartServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btStartServerActionPerformed
        new Thread(
                () -> {
                    try {
                        int port = Integer.parseInt(txtServerPort.getText());
                        myObject = new RemoteNode(port, this, bc);
                        RMI.startRemoteObject(myObject, port, RemoteNode.NAME);
                        AddressReceiver receiver = new AddressReceiver(myObject, this, txtServerAddress.getText());
                        receiver.start();
                        displayMessage("Start Server", "Objecto remoto disponível");
                        //Faz load da blockchain guardada
                        myObject.loadBlockchain();
                        Thread.sleep(1000);
                        //Sincronizar a blockchain
                        myObject.syncBlockchain();
                        AddressAnouncer anouncer = new AddressAnouncer(txtServerAddress.getText());
                        anouncer.start();
                        startClientListener(10020);
                        //updateList();
                    } catch (Exception ex) {
                        displayException("Start Server", ex);
                    }
                }).start();
    }//GEN-LAST:event_btStartServerActionPerformed

    private void txtServerAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtServerAddressActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtServerAddressActionPerformed

    private void btSaveBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveBCActionPerformed

        new Thread(
                () -> {
                    try {
                        myObject.saveBlockchain();
                    } catch (RemoteException ex) {
                        Logger.getLogger(ServiceGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
    }//GEN-LAST:event_btSaveBCActionPerformed

    private void btDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDisconnectActionPerformed
        // TODO
    }//GEN-LAST:event_btDisconnectActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServiceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServiceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServiceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServiceGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServiceGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddBlock;
    private javax.swing.JButton btDisconnect;
    private javax.swing.JButton btGetBlockchain;
    private javax.swing.JButton btSaveBC;
    private javax.swing.JButton btStartServer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JList<String> listNodes;
    private javax.swing.JTextPane txtBlockchain;
    private javax.swing.JTextField txtDesc;
    private javax.swing.JTextArea txtDisplay;
    private javax.swing.JTextField txtImei;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtMat;
    private javax.swing.JLabel txtMinAnim;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPais;
    private javax.swing.JTextField txtRede;
    private javax.swing.JTextField txtRep;
    private javax.swing.JTextField txtServerAddress;
    private javax.swing.JTextField txtServerPort;
    // End of variables declaration//GEN-END:variables

    public void displayException(String source, Exception ex) {
        txtLog.setText(
                source + "\t" + ex.getMessage() + "\n" + txtLog.getText()
        );
        Logger.getLogger(ServiceGUI.class.getName()).log(Level.SEVERE, null, ex);
    }

    public void displayMessage(String source, String txt) {
        txtDisplay.setText(
                source + "\t" + txt + "\n" + txtDisplay.getText()
        );
    }

    public void updateList() {
        try {
            DefaultListModel model = new DefaultListModel();
            for (IRemoteNode node : myObject.getNodes()) {
                model.addElement(node.getName());
            }
            listNodes.setModel(model);
        } catch (Exception ex) {
            displayException("Update List", ex);
        }
    }

    @Override
    public void onNonceFound(Block b) {
        try {
            myObject.stopMining(b);
        } catch (Exception ex) {
            displayException("On Nonce Found", ex);
        }
    }

    public void setWorking(boolean state) {
        txtMinAnim.setVisible(state);
    }

}
