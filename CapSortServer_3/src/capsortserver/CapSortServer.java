package capsortserver;

/**
 * Date: 11/12/2017
 * @author Kareem Lawal
 */
/**
 * A Driver Class for the Capitalization and Sorting Server
 */
public class CapSortServer {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // Intanstiation of the GUI Object of the CapSortServerGUI class
        CapSortServerGUI GUI = new CapSortServerGUI();
        GUI.pack();
        GUI.setTitle("Server");
        GUI.setVisible(true);
        GUI.connectServer();
    }
}
