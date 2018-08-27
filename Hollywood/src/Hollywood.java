/* *****************************************************
 * @(#)Hollywood.java   1.0  12/20/06 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.event.*;

public class Hollywood implements ActionListener {

   Players pCurrent = null;
   GameBoard gBoard = null;
 
   Hollywood () {
      pCurrent = new Players(this);
      }  

   //////////////////////////////////////////////////

   public void actionPerformed(ActionEvent eEvent) {
      String sAction = eEvent.getActionCommand();

      if(sAction.equals("Continue")) {
         pCurrent.setVisible(false);
         gBoard = new GameBoard(this, pCurrent);
         }

      if(sAction.equals("Help"))
         new HelpBox(pCurrent, "Hollywood Squares Help!");

      if(sAction.equals("Exit"))
         System.exit(0);
      }

   //////////////////////////////////////////////////

   public static void main(String[] args) {
      new Hollywood();
      }
   }
