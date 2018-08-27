/* *****************************************************
 * @(#)Trivia.java   2.0  08/16/17 17:40:34
 * @author Randy Crihfield
 *
 * Copyright 2017 Hozer Video Games.  All rights reserved.
 * Use is subject to license terms.
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Trivia extends JDialog implements ActionListener,
   KeyListener {

   private boolean   bDEBUG        = false;

   private AnswerBox aBox          = null;
   private StatBox   sBox          = null;
   private GameBoard gBoard        = null;
   private Player    pPlay         = null;
   private int       iPlayer       = 0;
   private int       iNumPlayers   = 1;
   private int       iStat[][]     = null;
   private int       iNumCounter   = 0;
   private int       iScoreCount   = 0;
   private boolean   bBlock        = true;
   private String    sBlockList    = "";
   private String    sBlockLoop    = "";


   ///////////////////////////////////////////////////
   // constructor

   public Trivia (JFrame fParent, int iNum, boolean bBug, boolean bDoBlocking) {
      if (bBug)
         System.out.println("In Trivia Constructor");

      bDEBUG = bBug;
      pPlay = new Player(iNum, bDEBUG);

      iNumPlayers = pPlay.getNumPlayers();

      bBlock = bDoBlocking;
      prepForNewQuestion();
      
      iStat = new int[iNumPlayers][2];
      initStats();

      getContentPane().setLayout(new GridLayout(3,3));
      setTitle("Mouse Player");

      JButton bButton = new JButton("Red");
      bButton.addActionListener(this);
      bButton.setBackground(Color.red);
      bButton.setForeground(Color.white);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Orange");
      bButton.addActionListener(this);
      bButton.setBackground(new Color(218, 129, 47));
      bButton.setForeground(Color.black);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Yellow");
      bButton.addActionListener(this);
      bButton.setBackground(Color.yellow);
      bButton.setForeground(Color.black);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Green");
      bButton.addActionListener(this);
      bButton.setBackground(new Color(37, 208, 40));
      bButton.setForeground(Color.black);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Blue");
      bButton.addActionListener(this);
      bButton.setBackground(Color.blue);
      bButton.setForeground(Color.white);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Purple");
      bButton.addActionListener(this);
      bButton.setBackground(new Color(148, 37, 215));
      bButton.setForeground(Color.white);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Exit");
      bButton.addActionListener(this);
      bButton.setBackground(Color.white);
      bButton.setForeground(Color.black);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Stats");
      bButton.addActionListener(this);
      bButton.setBackground(Color.gray);
      bButton.setForeground(Color.white);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      bButton = new JButton("Next");
      bButton.addActionListener(this);
      bButton.setBackground(Color.black);
      bButton.setForeground(Color.white);
      bButton.addKeyListener(this);
      getContentPane().add(bButton);

      // first create game board, hidden boxes
      gBoard = new GameBoard(pPlay, bDEBUG);
      aBox = new AnswerBox(bDEBUG, fParent);
      sBox = new StatBox(bDEBUG, this, pPlay);
      doSwap(false);

      // NOW show virtual controller
      setModal(true);
      setAlwaysOnTop(true);
      pack();
      setVisible(true);
   }  


   //////////////////////////////////////////////////
   // create the list of blocked players

   public void prepForNewQuestion() {
      if (bDEBUG)
         System.out.println("In Trivia.prepForNewQuestion method");

      // reset question score
      iScoreCount = 6;

      // reset blocking lists
      if (bBlock) {
         sBlockList = "";
         sBlockLoop = "";
         for (int count = 1; count < iNumPlayers; count ++)
            sBlockList = sBlockList + count;
      }
   }


   //////////////////////////////////////////////////
   // someone responded and blocking is on!

   public boolean allowBlockedPlayer(int iBlockPlayer) {
      if (bDEBUG)
         System.out.println("In Trivia.allowBlockedPlayer method");

      // Computer player is never blocked!
      if (iBlockPlayer == 0)
         return(true);

      // returns true if player is allowed to respond,
      // false if not.
      boolean bAllowed = true;

      // first check and deal with Block List
      if (! sBlockList.equals("")) {
      
         if (!sBlockList.contains("" + iBlockPlayer)) {
            // if not in list, leave it that way and return
            // "NOT allowed"
            bAllowed = false;
         } else {
            // chop player out of list and add to loop list.
            // return "allowed"
            sBlockList = sBlockList.replaceAll("" +
               iBlockPlayer, "");
            sBlockLoop = sBlockLoop + iBlockPlayer;
         }

      } else {
         // now that block list is empty, check loop list

         if (!sBlockLoop.contains("" + iBlockPlayer)) {
            // if not in list, leave it that way and return
            // "NOT allowed"
            bAllowed = false;
         } else {
            // if in list, remove and add missing player to
            // end of list.  Return "allowed"

            // first, who's not there?
            int iMiss = 1;
            for (int count = 1; count < iNumPlayers; count ++)
               if (!sBlockLoop.contains("" + count))
                  iMiss = count;

            // yank out current player
            sBlockLoop = sBlockLoop.replaceAll("" +
               iBlockPlayer, "");

            // tack back on "missing" player
            if (!sBlockLoop.contains("" + iMiss))
               sBlockLoop = sBlockLoop + iMiss;
         }
      }

      if (bDEBUG) {
         System.out.println("sBlockList is " + sBlockList);
         System.out.println("sBlockLoop is " + sBlockLoop);
      }

      return(bAllowed);
   }


   //////////////////////////////////////////////////
   // clear the stats

   public void initStats() {
      if (bDEBUG)
         System.out.println("In Trivia.initStats method");

      for (int iPlay = 0; iPlay < iNumPlayers; iPlay ++)
         for (int iVal = 0; iVal < 2; iVal ++)
            iStat[iPlay][iVal] = 0;

      iNumCounter = 0;
   }


   //////////////////////////////////////////////////
   // return the stat for a player, right/wrong

   public int getStat(int iPlay, int iVal) {
      if (bDEBUG)
         System.out.println("In Trivia.getStat method");

      return(iStat[iPlay][iVal]);
   }


   //////////////////////////////////////////////////
   // increment the stat for a player, right/wrong

   public void incrementStat(int iPlay, boolean bCorrect) {
      if (bDEBUG)
         System.out.println("In Trivia.incrementStat method");

      if (bCorrect)
         iStat[iPlay][0] ++;
      else
         iStat[iPlay][1] ++;

      if (bDEBUG)
         System.out.println(pPlay.getNameLabel(iPlay).getText() +
            " is " + bCorrect);
   }


   //////////////////////////////////////////////////
   // show the number of real responses so far...

   public int getNumSoFar() {
      if (bDEBUG)
         System.out.println("In Trivia.getNumSoFar method");

      return(iNumCounter);
   }


   //////////////////////////////////////////////////
   // show the total number of questions

   public int getNumTotal() {
      if (bDEBUG)
         System.out.println("In Trivia.getNumTotal method");

      return(gBoard.getNumOfQuestions());
   }


   //////////////////////////////////////////////////
   // show the answer/response clearly

   private void makeResponse(int iButton) {
      if (bDEBUG)
         System.out.println("In Trivia.makeResponse method");

      // do we block the player?
      if (bBlock == true)
         if (! allowBlockedPlayer(iPlayer))
            return;

      String sAnswer = gBoard.getAnswer(iButton);
      String sResponse = gBoard.getResponse(iButton);

      // check that there's a response at all. If not,
      // do NOTHING except in case of return to question box
      if (sResponse == null || sResponse.equals("")) 
         if (gBoard.isVisible()) {
            // question is worth one less, and removed
            if (! sAnswer.equals("")) {
               // reduce possible question score
               iScoreCount = iScoreCount - 1;
               // ding the goofball who answered wrong!
               pPlay.addScore(iPlayer, -1);
               // adjust their stats
               incrementStat(iPlayer, gBoard.isCorrect(iButton));
            }
            return;
         }

      // any time answer board is show, record right/wrong answer!
      // but ONLY when answer board is being shown!
      if (gBoard.isVisible()) {
         pPlay.addScore(iPlayer, iScoreCount);
         incrementStat(iPlayer, gBoard.isCorrect(iButton));
      }

      aBox.setText(pPlay.getNameLabel(iPlayer).getText(),
         sAnswer, sResponse);
      if (bBlock) {
         prepForNewQuestion();
         aBox.setNext(sBlockList);
      }
      doSwap(false);
   }


   //////////////////////////////////////////////////
   // flip the boxes

   private void doSwap(boolean bStat) {
      if (bDEBUG)
         System.out.println("In Trivia.doSwap method");

      // none visible
      if (!aBox.isVisible() && !gBoard.isVisible() && !sBox.isVisible()) {
         String sIntro = gBoard.getIntro();
         if (sIntro != null && !aBox.isIntro()) {
            aBox.setText(sIntro);
            aBox.setSize(gBoard.getSize());
            aBox.setLocation(gBoard.getLocation());
            aBox.setVisible(true);
         } else {
            gBoard.setSize(aBox.getSize());
            gBoard.setLocation(aBox.getLocation());
            gBoard.setVisible(true);
            prepForNewQuestion();
         }
      } else {
         // Answer box needs dismissed
         if (aBox.isVisible()) {
            aBox.setVisible(false);

            // do I now show stat box? or back to Game Board?
            if (bStat) {
               sBox.setSize(aBox.getSize());
               sBox.setLocation(aBox.getLocation());
               sBox.refresh();
               sBox.setVisible(true);
            } else {
               gBoard.setSize(aBox.getSize());
               gBoard.setLocation(aBox.getLocation());
               gBoard.setVisible(true);
               prepForNewQuestion();
            }
         } else 
            // Stat box needs dismissed, always back to answer box
            if (sBox.isVisible()) {
               sBox.setVisible(false);
               aBox.setSize(sBox.getSize());
               aBox.setLocation(sBox.getLocation());
               aBox.setVisible(true);
            }
         else {
            // game board is showing, display answer box
            gBoard.setVisible(false);
            aBox.setSize(gBoard.getSize());
            aBox.setLocation(gBoard.getLocation());
            aBox.setVisible(true);
         }
      }
   }

   //////////////////////////////////////////////////
   // Someone's listening to the keyboard

   public void keyTyped(KeyEvent eKey) {
      if (bDEBUG)
         System.out.println("In Trivia.keyTyped method");

      // watch out, they may be pressing more than one!
      // last one pressed wins!

      String sAction = "nocolor";

      // Only care if its an actual key typed event!
      int iKey = eKey.getID();
      if (eKey.getID() == KeyEvent.KEY_TYPED) { 
         char cKey = eKey.getKeyChar();

         // defaults to player 1
         iPlayer = 1;
         if ("roygbpn".indexOf(cKey) >= 0) iPlayer = 2;
         if ("ACDEFHI".indexOf(cKey) >= 0) iPlayer = 3;
         if ("acdefhi".indexOf(cKey) >= 0) iPlayer = 4;

         if ("RrAa".indexOf(cKey) >= 0) sAction = "Red";
         if ("OoCc".indexOf(cKey) >= 0) sAction = "Orange";
         if ("YyDd".indexOf(cKey) >= 0) sAction = "Yellow";
         if ("GgEe".indexOf(cKey) >= 0) sAction = "Green";
         if ("BbFf".indexOf(cKey) >= 0) sAction = "Blue";
         if ("PpHh".indexOf(cKey) >= 0) sAction = "Purple";
         if ("NnIi".indexOf(cKey) >= 0) sAction = "Next";

         doActualResponses(sAction);
      }
   }

   //////////////////////////////////////////////////
   // Someone's listening to the computer player!

   public void actionPerformed(ActionEvent eEvent) {
      if (bDEBUG)
         System.out.println("In Trivia.actionPerformed method");

      // this was a mouse event, so the computer player responded!
      iPlayer = 0;

      doActualResponses(eEvent.getActionCommand());
   }


   //////////////////////////////////////////////////
   // Something's happened - respond!

   private void doActualResponses(String sAction) {

      if (bDEBUG)
         System.out.println("In Trivia.doActualResponses method");

      if (sAction.equals("Red")) {
         if (gBoard.isVisible())
            makeResponse(0);
      }

      if (sAction.equals("Orange")) {
         if (gBoard.isVisible())
            makeResponse(1);
      }

      if (sAction.equals("Yellow")) {
         if (gBoard.isVisible())
            makeResponse(2);
      }

      if (sAction.equals("Green")) {
         if (gBoard.isVisible())
            makeResponse(3);
      }

      if (sAction.equals("Blue")) {
         if (gBoard.isVisible())
            makeResponse(4);
      }

      if (sAction.equals("Purple")) {
         if (gBoard.isVisible())
            makeResponse(5);
      }

      if (sAction.equals("Next")) {

         // Only advance at answer box
         if (!aBox.isVisible())
            return;

         if (iPlayer != 0) {
            if (bBlock && !sBlockList.equals("")) {
               // if they ARE in there, remove them
               sBlockList = sBlockList.replaceAll("" +
                  iPlayer, "");

               aBox.setNext(sBlockList);

               // now if list is not empty return
               if (!sBlockList.equals(""))
                  return;
            }
         }

         // that was the last question! restart loop
         if ((iNumCounter) % getNumTotal() == 0
            && !aBox.isIntro()) {
            doSwap(true);
         } else {

            iNumCounter ++;
            // simply move on to next question...
            if (!aBox.isIntro()) {
               gBoard.nextQuestion();
            }
            doSwap(false);
         }

      }

      if (sAction.equals("Stats")) {
         if (aBox.isVisible())
            doSwap(true);
         else
            if (sBox.isVisible())
               doSwap(false);
      }

      if (sAction.equals("Exit"))
         System.exit(0);
   }

   //////////////////////////////////////////////////

   public void keyPressed(KeyEvent eKey) {}
   public void keyReleased(KeyEvent eKey) {}

   //////////////////////////////////////////////////

   public static void main(String[] args) {

      boolean doDebug  = "true".equals(
          System.getProperty("DEBUG", "false"));
      boolean bDoBlock = "true".equals(
          System.getProperty("BLOCKING", "true"));
      int iNum         = 1;

      if (args.length != 0) {
         iNum = Integer.parseInt(args[0]);
      }
      if (iNum > 5 || iNum < 0) 
         iNum = 1;

      Trivia tBox = new Trivia(new JFrame(), iNum, doDebug, bDoBlock);
   }
}
