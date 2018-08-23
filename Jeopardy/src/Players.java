/* *****************************************************
 * @(#)Players.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;

public class Players extends Frame {

   ///////////////////////////////////////////////////
   // global data value settings

   private TextField tPlayers[] = {
      new TextField(""),
      new TextField(""),
      new TextField(""),
      new TextField("")};

   private TextField tScores[]  = {
      new TextField("0"),
      new TextField("0"),
      new TextField("0"),
      new TextField("0")};

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   Players (ActionListener alParent, boolean bVisible) {
      setTitle("Jeopardy Players Page");
      setBackground(Color.blue);
      setForeground(Color.white);
      setLayout(new BorderLayout());

      // show the data
      Panel pRealInfo = new Panel(new GridLayout(3,5));

      pRealInfo.add(new Label("Number:     "));
      for (int iNum = 1; iNum < 5; iNum ++)
         pRealInfo.add(new Label("Player " + iNum));

      pRealInfo.add(new Label("Name:"));
      for (int iNum = 0; iNum < 4; iNum ++) {
         tPlayers[iNum].setBackground(Color.white);
         tPlayers[iNum].setForeground(Color.black);
         pRealInfo.add(tPlayers[iNum]);
      }

      pRealInfo.add(new Label("Score:")); 
      for (int iNum = 0; iNum < 4; iNum ++) {
         tScores[iNum].setBackground(Color.white);
         tScores[iNum].setForeground(Color.black);
         pRealInfo.add(tScores[iNum]);
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
      setVisible(bVisible);
      }  


      //////////////////////////////////////////////////
      // returns name of player
      public String getPlayer(int iPos) {
         String sName = tPlayers[iPos].getText();
         if (sName.equals(""))
            return("Player " + (iPos + 1));

         return(sName);
      }


      //////////////////////////////////////////////////
      // returns score of player
      public String getScore(int iPos) {
         return(tScores[iPos].getText());
      }


      //////////////////////////////////////////////////
      // sets score of player
      public void setScore(int iPos, String sVal) {
         tScores[iPos].setText(sVal);
      }
   }
