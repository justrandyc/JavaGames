/* *****************************************************
 * @(#)Fill.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.event.*;

public class Fill implements ActionListener {

    Players pCurrent = null;
    GameBoard gBoard = null;
 
    Fill() {
        pCurrent = new Players(this);
    }  

    //////////////////////////////////////////////////

    public void actionPerformed(ActionEvent eEvent) {
        String sAction = eEvent.getActionCommand();

        if(sAction.equals("Continue")) {
            try {
                gBoard = new GameBoard(pCurrent.getPlayers());
                gBoard.setLocation(pCurrent.getLocation());
                pCurrent.setVisible(false);
                gBoard.setVisible(true);
            }
            catch(Exception eExcept) {
                // you MUST enter at least one name to continue
            }
        }

        if(sAction.equals("Exit"))
            System.exit(0);
    }

    //////////////////////////////////////////////////

    public static void main(String[] args) {
       new Fill();
    }
}
