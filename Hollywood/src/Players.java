/* *****************************************************
 * @(#)Players.java   1.0  12/20/06 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;

public class Players extends Frame {

   ///////////////////////////////////////////////////
   // global data value settings

   private TextField tPlayers[] = new TextField[2];

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   Players (ActionListener alParent) {
      setTitle("Jeopardy Players Page");
      setBackground(Color.blue);
      setForeground(Color.white);
      setLayout(new BorderLayout());

      // show the data
      Panel pRealInfo = new Panel(new GridLayout(3,3));

      pRealInfo.add(new Label("Number:     "));
      for (int iNum = 1; iNum < 3; iNum ++)
         pRealInfo.add(new Label("Player " + iNum));

      pRealInfo.add(new Label("Name:"));
      for (int iNum = 0; iNum < 2; iNum ++) {
         tPlayers[iNum] = new TextField();
         tPlayers[iNum].setBackground(Color.white);
         tPlayers[iNum].setForeground(Color.black);
         pRealInfo.add(tPlayers[iNum]);
      }

      add(pRealInfo, BorderLayout.CENTER);

      // do the control buttons...
      Panel pButtonPanel = new Panel();

      Button bButton = new Button("Help");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.green);
      bButton.setForeground(Color.black);
      pButtonPanel.add(bButton);

      bButton = new Button("Exit");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.red);
      bButton.setForeground(Color.black);
      pButtonPanel.add(bButton);

      bButton = new Button("Continue");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.green);
      bButton.setForeground(Color.black);
      pButtonPanel.add(bButton);

      add(pButtonPanel, BorderLayout.SOUTH);

      pack();
      setVisible(true);
      }  


      //////////////////////////////////////////////////
      // returns name of player
      public String getPlayer(int iPos) {
         String sName = tPlayers[iPos].getText();
         String sChar = "X";
         if (iPos > 0)
            sChar = "O";
         if (sName.equals("")) {
            return("Player " + (iPos + 1) + " - " + sChar);
         }

         return(sName + " - " + sChar);
      }
   }
