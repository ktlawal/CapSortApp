 
package capsortserver;


/**
 * Date: 11/12/2017
 * @author Kareem Lawal
 */
/**
 * A Driver Class for the client of the capitalization and sorting server
 */
public class CapSortClient {

    public static void main(String[] args) throws Exception {
        //Instantiation of GUI Object of CapSortClientGUI class
        CapSortClientGUI GUI = new CapSortClientGUI();        
        GUI.pack();
        GUI.setTitle("Client");
        GUI.setVisible(true);

    }
}
 