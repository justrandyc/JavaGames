/* *****************************************************
 * @(#)Players.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;

public class Players extends Frame {

   ///////////////////////////////////////////////////
   // global data value settings

   private int iMaxPlayers = 8;
   private TextField tPlayers[] = new TextField[iMaxPlayers];

   private Button bExit         = null;
   private Button bContinue     = null;
 

   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   Players (ActionListener alParent) {

      for (int iCount = 0; iCount < iMaxPlayers; iCount ++)
          tPlayers[iCount] = new TextField();

      setTitle("Fill Or Bust Players");
      setBackground(Color.blue);
      setForeground(Color.white);
      setLayout(new BorderLayout());

      // show the data
      Panel pRealInfo = new Panel(new GridLayout(2,iMaxPlayers));

      for (int iNum = 1; iNum < (iMaxPlayers + 1); iNum ++)
         pRealInfo.add(new Label("Player " + iNum));

      for (int iNum = 0; iNum < iMaxPlayers; iNum ++) {
         tPlayers[iNum].setBackground(Color.white);
         tPlayers[iNum].setForeground(Color.black);
         pRealInfo.add(tPlayers[iNum]);
      }

      add(pRealInfo, BorderLayout.CENTER);

      // do the control buttons...
      Panel pButtonPanel = new Panel();

      bExit = new Button("Exit");
      bExit.addActionListener(alParent);
      bExit.setBackground(Color.red);
      bExit.setForeground(Color.black);
      pButtonPanel.add(bExit);

      bContinue = new Button("Continue");
      bContinue.addActionListener(alParent);
      bContinue.setBackground(Color.green);
      bContinue.setForeground(Color.black);
      pButtonPanel.add(bContinue);

      add(pButtonPanel, BorderLayout.SOUTH);

      pack();
      setVisible(true);
      }  

      //////////////////////////////////////////////////
      // returns name of players
      public Label[] getPlayers() {
         Label lReturn[] = new Label[iMaxPlayers];

         // which is the longest?
         int iLongest = 0;
         for (int iCount = 0; iCount < 0; iCount ++) {
             int iLen = tPlayers[iCount].getText().length();
             if (iLen > iLongest)
                 iLongest = iLen;
         }

         // make shorter ones as long as longest, leave "" ones as ""
         for (int iCount = 0; iCount < iMaxPlayers; iCount ++) {
             String sTemp = tPlayers[iCount].getText();
             int iLen = sTemp.length();
             if (iLen != 0) {
                 sTemp = sTemp + ": ";
                 if (iLen < iLongest)
                     for (int iNum = 0; iNum < iLongest - iLen; iNum ++)
                         sTemp = sTemp + " ";
             }
             lReturn[iCount] = new Label(sTemp);
         }

         return(lReturn);
      }
   }
