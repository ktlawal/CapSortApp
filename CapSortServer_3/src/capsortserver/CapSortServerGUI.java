package capsortserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Date: 11/12/2017
 *
 * @author Kareem Lawal
 */
/**
 *  A simple swing based Capitalization and Sorting Server which implements
 * Ricart and Agrawala’s algorithm
 */
public class CapSortServerGUI extends javax.swing.JFrame {

    /**
     * Creates new form CapSortServerGUI
     */
    public CapSortServerGUI() {
        initComponents();
    }
    //Declaration of Variables
    static String[] arrFromClient;
    static String[] arrayToClient;
    Map<Integer, Socket> clients = new HashMap<>();
    ArrayList<Double> stamps = new ArrayList<>();
    TreeMap<Double, Integer> clientsList = new TreeMap<>();

    ServerSocket listener;
    Message message = new Message();
    ClientHandler ch;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Logs:");

        jLabel2.setText("Connected Client(s):");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("Server IP Address");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    //Instantiate Server socket, bind it to a port and listens for incoming connections
    public void connectServer() throws Exception {
        try {
            int clientNumber = 1;
            jButton1.setText("" + InetAddress.getLocalHost());
            listener = new ServerSocket(8888);
            jTextArea1.append("<<<<<Server Started! - Waiting for Connections >>>>> \n");
            System.out.println("The capitalization server is running.");

            try {
                while (true) {
                    ch = new ClientHandler(listener.accept(), clientNumber++);
                    ch.start();

                }
            } finally {
                listener.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(CapSortServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //ClientHandler 
    private class ClientHandler extends Thread {

        private Socket socket;
        private int clientNumber;
        private double timeStamp;
        private double maxStamp;
        String outgoing;

        public ClientHandler(Socket socket, int clientNumber) {
            this.socket = socket;
            this.timeStamp = ThreadLocalRandom.current().nextDouble(10);
            this.clientNumber = clientNumber;

            InetAddress addr = socket.getInetAddress();
            int port = socket.getPort();

            //Logs connection to console and add individual connections to List
            log("New connection with client " + clientNumber + " at " + socket);
            clients.put(clientNumber, socket);
            stamps.add(timeStamp);
            clientsList.put(timeStamp, clientNumber);

            //Iterates list and append all connections to server text area
            for (Map.Entry<Double, Integer> entry : clientsList.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            jTextArea1.append("Connection established with client " + clientNumber + "\n");

            //Displays connected clients to TextArea
            jTextArea2.setText(null);
            clients.entrySet().forEach((entry) -> {
                jTextArea2.append("Client " + entry.getKey() + " : " + entry.getValue() + "\n");
            });
        }

        //Finds maximum timestamp of all connected clients
        public double maxStamp() {
            double timeStamp = 0.0;
            for (int i = 0; i < stamps.size(); i++) {
                System.out.println(stamps.get(i));
                if (stamps.get(i) > timeStamp) {
                    timeStamp = stamps.get(i);
                }
            }
            return timeStamp;
        }

        //Returns connected clients by ID and Socket
        public String connectedClients() {
            String connected = "";
            for (Map.Entry<Integer, Socket> entry : clients.entrySet()) {
                connected = connected + "\r\n" + " Client " + entry.getKey() + " : " + entry.getValue().getInetAddress()
                        + " : " + entry.getValue().getPort();
            }
            return connected;

        }

        //Returns TimeStamp and ID of each client 
        public String trackClients() {
            TreeMap<Double, Integer> clientsStringClone = new TreeMap<>();
            clientsStringClone = (TreeMap) clientsList.clone();
            String clientString = "";
            for (Map.Entry<Double, Integer> entry : clientsStringClone.entrySet()) {
                clientString = clientString + "\r\n" + " Client " + entry.getValue() + " : " + entry.getKey();
            }
            System.out.println("ClientString: " + clientString);
            return clientString;

        }

        //Run Method
        public void run() {

            try {
                //Input and outputstream to and from client
                ObjectInputStream inFromClient = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());

                //Hello message to clients
                String connected = "Hello, you are client :" + clientNumber + "\n";

                //Packs message to client
                message.setMessage(connected);
                message.setTrackClients(trackClients());
                message.setClients(connectedClients());
                outToClient.writeObject(message);

                System.out.print(connected);

                while (true) {

                    //Input and output streams to and from client
                    inFromClient = new ObjectInputStream(socket.getInputStream());
                    outToClient = new ObjectOutputStream(socket.getOutputStream());

                    message = (Message) inFromClient.readObject();
//                    System.out.println(message.getMessage() + "  :  " + timeStamp);
                    jTextArea1.append("Received request from Client " + clientNumber + "\n");

                    //Critical Section
                    if (timeStamp == maxStamp()) {

                        String[] incoming = message.getMessage().split("\\s+");
                        for (int i = 0; i < incoming.length; i++) {
                            incoming[i] = incoming[i].toUpperCase();
                        }
                        Arrays.sort(incoming);
                        outgoing = String.join("\n", incoming);

                        message.setMessage(outgoing);
                        message.setTrackClients(trackClients());
                        message.setClients(connectedClients());
                        outToClient.writeObject(message);
                        jTextArea1.append("Processed and sent response to client " + clientNumber + "\n");
                        outToClient.flush();
                    } else {
                        //Rejects critical section
                        message.setMessage("You are not in the Critical Section");
                        message.setTrackClients(trackClients());
                        message.setClients(connectedClients());
                        outToClient.writeObject(message);
                        outToClient.flush();
                    }
                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CapSortServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Error closing socket");
                }

                //Remove and Log Disconnected clients from all Lists
                log("Client " + clientNumber + " disconnected");
                jTextArea1.append("client " + clientNumber + " disconnected\n");
                clients.values().removeAll(Collections.singleton(socket));
                stamps.remove(timeStamp);
                clientsList.values().removeAll(Collections.singleton(clientNumber));

                jTextArea2.setText(null);
                clients.entrySet().forEach((entry) -> {
                    jTextArea2.append("Client " + entry.getKey() + " : " + entry.getValue() + "\n");
                });
            }
        }

        //  Log method to display log in console
        private void log(String message) {
            System.out.println(message);
        }
    }

//Main Method
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
            java.util.logging.Logger.getLogger(CapSortServerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapSortServerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapSortServerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapSortServerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapSortServerGUI().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
