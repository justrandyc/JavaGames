/* *****************************************************
 * @(#)Player.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */
 
import java.awt.*;

public class Player {

   ///////////////////////////////////////////////////
   // global data value settings

   private boolean    bDEBUG       = false; 

   private Label      lNames[]     = new Label[7];
   private Label      lScores[]    = new Label[7];
   private int        iScores[]    = new int[7];
   private int        iNumPlayers  = 0;
 

   ///////////////////////////////////////////////////
   // constructor

   Player(int iNum, boolean bBug) {
      if (bBug)
         System.out.println("In Player method");

      bDEBUG = bBug;

      iNumPlayers = iNum + 1;
      if (iNumPlayers > 7)
         iNumPlayers = 7;
      if (iNumPlayers < 1)
         iNumPlayers = 1;

      for (int count = 0; count < iNumPlayers; count ++) {
         lNames[count] = makeLabel("Player " + count);
         lScores[count] = makeLabel("");
         iScores[count] = 0;
      }

      lNames[0].setText("Computer");
      refreshScores();
   }  


   //////////////////////////////////////////////////
   // Helper method for creating labels
   private Label makeLabel(String sContents) {
      Label lReturn = new Label(sContents, Label.CENTER);
      lReturn.setBackground(Color.darkGray);
      lReturn.setForeground(Color.white);

      return(lReturn);
   }


   //////////////////////////////////////////////////
   // gets the name of a player as a label
   public Label getNameLabel(int iVal) {
      if (bDEBUG)
         System.out.println("In getNameLabel method");

      // return the label object
      return(lNames[iVal]);
   }


   //////////////////////////////////////////////////
   // gets the score of a player as a label
   public Label getScoreLabel(int iVal) {
      if (bDEBUG)
         System.out.println("In getScoreLabel method");

      // return the label object
      return(lScores[iVal]);
   }


   //////////////////////////////////////////////////
   // add to scores
   public void addScore(int iVal, int iScore) {
      if (bDEBUG)
         System.out.println("In addScore method");

      iScores[iVal] = iScores[iVal] + iScore;
      refreshScores();
   }


   //////////////////////////////////////////////////
   // refresh scores
   public void refreshScores() {
      if (bDEBUG)
         System.out.println("In refreshScores method");

      for (int count = 0; count < iNumPlayers; count ++)
         lScores[count].setText(" " + iScores[count]);
   }


   //////////////////////////////////////////////////
   // return number of players
   public int getNumPlayers() {
      if (bDEBUG)
         System.out.println("In getNumPlayers method");

      // return the label object
      return(iNumPlayers);
   }
}
