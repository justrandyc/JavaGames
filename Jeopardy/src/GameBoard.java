/* *****************************************************
 * @(#)GameBoard.java   1.0  09/20/05 17:40:34
 * @author Randy Crihfield
 */
 
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends Frame implements ActionListener {

   ///////////////////////////////////////////////////
   // global data value settings

   private Players pPlayers     = null;
   private int iActivePlayer    = (int)Math.round(Math.random() * 3);

   private Label lRound         = 
      new Label("You are in Jeopardy!          ");

   private Panel pCenterPanel   = null;
   private CardLayout cCenter   = null;
   private Answers aAnswer      = null;
 
   private Button bPlayer[]     = new Button[4];
   private Label lScores[]      = new Label[4];


   ///////////////////////////////////////////////////
   // constructor, draws the window initially.

   GameBoard (ActionListener alParent, Players pPlay, boolean bVisible) {

      pPlayers = pPlay;
      aAnswer = new Answers(this, pPlay);

      setTitle("Jeopardy GameBoard Page");
      setBackground(Color.blue);
      setForeground(Color.white);
      setLayout(new BorderLayout());

      // show the Round data
      Panel pTopPanel = new Panel(new GridLayout(1,4));

      Button bButton = new Button("Jeopardy");
      bButton.setBackground(Color.blue);
      bButton.setForeground(Color.white);
      bButton.addActionListener(this);
      pTopPanel.add(bButton);
      bButton = new Button("Double Jeopardy");
      bButton.setBackground(Color.blue);
      bButton.setForeground(Color.white);
      bButton.addActionListener(this);
      pTopPanel.add(bButton);
      bButton = new Button("Final Jeopardy");
      bButton.setBackground(Color.blue);
      bButton.setForeground(Color.white);
      bButton.addActionListener(this);
      pTopPanel.add(bButton);

      lRound.setForeground(Color.yellow);
      pTopPanel.add(lRound);

      add(pTopPanel, BorderLayout.NORTH);


      // show the Jeopardy values
      cCenter = new CardLayout();
      pCenterPanel = new Panel(cCenter);
      pCenterPanel.add(aAnswer.getRound(0), "Jeopardy");
      pCenterPanel.add(aAnswer.getRound(1), "Double");
      pCenterPanel.add(aAnswer.getRound(2), "Final");
      add(pCenterPanel, BorderLayout.CENTER);


      // show the Player data, Control buttons
      Panel pBottomPanel = new Panel(new GridLayout(2,1));

      Panel pPlayerInfo = new Panel();
      pPlayerInfo.setLayout(new GridLayout(2,4));
      for (int iNum = 0; iNum < 4; iNum ++) {
         bPlayer[iNum] = new Button(pPlayers.getPlayer(iNum));
         bPlayer[iNum].setBackground(Color.blue);
         bPlayer[iNum].setForeground(Color.white);
         bPlayer[iNum].addActionListener(this);
         pPlayerInfo.add(bPlayer[iNum]);
      }
      // initial player selected
      bPlayer[iActivePlayer].setForeground(Color.red);
 
      for (int iNum = 0; iNum < 4; iNum ++) {
         lScores[iNum] = new Label(pPlayers.getScore(iNum));
         pPlayerInfo.add(lScores[iNum]);
      }

      Panel pControlPanel = new Panel();

      bButton = new Button("Help");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.green);
      bButton.setForeground(Color.black);
      pControlPanel.add(bButton);

      bButton = new Button("Exit");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.red);
      bButton.setForeground(Color.black);
      pControlPanel.add(bButton);

      bButton = new Button("Edit");
      bButton.addActionListener(alParent);
      bButton.setBackground(Color.green);
      bButton.setForeground(Color.black);
      pControlPanel.add(bButton);

      pBottomPanel.add(pPlayerInfo);
      pBottomPanel.add(pControlPanel);

      add(pBottomPanel, BorderLayout.SOUTH);

      pack();
      setSize(800, 600);
      setVisible(bVisible);
   }  


   public void refresh() {
      for (int iNum = 0; iNum < 4; iNum ++)
         bPlayer[iNum].setLabel(pPlayers.getPlayer(iNum));
 
      for (int iNum = 0; iNum < 4; iNum ++)
         lScores[iNum].setText(pPlayers.getScore(iNum));
   }


   //////////////////////////////////////////////////
   // disable player when final round is over
   public void allDone(int iNum) {
      bPlayer[iNum].setEnabled(false);
      refresh();
   }


   //////////////////////////////////////////////////
   // return the active player for scoring
   public int getActivePlayer() {
      return(iActivePlayer);
   }


   //////////////////////////////////////////////////
   // set the active player for scoring
   public void setActivePlayer(int iActive) {
      iActivePlayer = iActive;
      if (iActivePlayer > 3)
         iActivePlayer = 0;

      for (int iNum = 0; iNum < 4; iNum ++)
         bPlayer[iNum].setForeground(Color.white);

      bPlayer[iActivePlayer].setForeground(Color.red);
   }


   //////////////////////////////////////////////////
   // catch those events!
   public void actionPerformed(ActionEvent eEvent) {
      String sAction = eEvent.getActionCommand();

      if(sAction.equals("Jeopardy")) {
         lRound.setText("You are in Jeopardy!");
         cCenter.show(pCenterPanel, "Jeopardy");
      }

      if(sAction.equals("Double Jeopardy")) {
         lRound.setText("You are in Double Jeopardy!");
         cCenter.show(pCenterPanel, "Double");
      }

      if(sAction.equals("Final Jeopardy")) {
         lRound.setText("You are in Final Jeopardy!");
         cCenter.show(pCenterPanel, "Final");
      }

      if(sAction.equals(pPlayers.getPlayer(0))) {
         iActivePlayer = 0;
      }

      if(sAction.equals(pPlayers.getPlayer(1))) {
         iActivePlayer = 1;
      }

      if(sAction.equals(pPlayers.getPlayer(2))) {
         iActivePlayer = 2;
      }

      if(sAction.equals(pPlayers.getPlayer(3))) {
         iActivePlayer = 3;
      }

      // highlight current player
      setActivePlayer(iActivePlayer);
   }
}
