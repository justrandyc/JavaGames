/* *****************************************************
 * @(#)Jeopardy.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.event.*;

public class Jeopardy implements ActionListener {

   Players pCurrent = null;
   GameBoard gBoard = null;
 
   Jeopardy () {
      pCurrent = new Players(this, true);

      gBoard = new GameBoard(this, pCurrent, false);
      }  

   //////////////////////////////////////////////////

   public void actionPerformed(ActionEvent eEvent) {
      String sAction = eEvent.getActionCommand();

      if(sAction.equals("Edit")) {
         pCurrent.setLocation(gBoard.getLocation());
         gBoard.setVisible(false);
         pCurrent.setVisible(true);
         }

      if(sAction.equals("Continue")) {
         gBoard.setLocation(pCurrent.getLocation());
         pCurrent.setVisible(false);
         gBoard.refresh();
         gBoard.setVisible(true);
         }

      if(sAction.equals("Help"))
         new HelpBox(pCurrent);

      if(sAction.equals("Exit"))
         System.exit(0);
      }

   //////////////////////////////////////////////////

   public static void main(String[] args) {
      new Jeopardy();
      }
   }
