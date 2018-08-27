/* *****************************************************
 * @(#)GameBoard.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */

import java.awt.*;
import java.awt.event.*;

class GameBoard extends Frame implements ActionListener, 
    ComponentListener {

    private boolean bDEBUG = false;
    private int iCellX, iCellY;

    // flow control
    private boolean bMayFlip = true;
    private int iActivePlayer = 0;
    private int iHighScorer = 0;
    private int iFillReq = 0;
    private boolean bActiveTurn = false;
    private boolean bFill = false;

    private Panel pCardPanel = null;
    private Cards cCurrent = new Cards(this, false);

    private Panel pDicePanel = null;
    private Die dDice[] = new Die[6];

    private int iTurn = 0; 
    private int iTotalCards = 54;

    // make the inital status label LONG so the image packs right
    private Label lStatus  = 
        new Label("                       ");
    private Label lCurrent = new Label();

    private int iPlayersPossible = 0;
    private Label lPlayers[];
    private int iNumPlayers = 0;
    private Label lScores[];
    private int iScores[];

    // these are points per turn
    private int iCurrentPoints = 0;

    // the constructor itself
    public GameBoard(Label lNames[]) {
        if (bDEBUG)
            System.out.println("In GameBoard constructor");
        lPlayers = lNames;
        iPlayersPossible = lNames.length;
        lScores = new Label[iPlayersPossible];
        iScores = new int[iPlayersPossible];
        for (int iCount = 0; iCount < iPlayersPossible; iCount ++) {
            lScores[iCount] = new Label("0");
            iScores[iCount] = 0;
        }

        lPlayers = lNames;
        while (iNumPlayers < iPlayersPossible && 
            !lPlayers[iNumPlayers].getText().equals(""))
            iNumPlayers = iNumPlayers + 1;

        // make all players, and scores, green
        for (int iCount = 0; iCount < iNumPlayers; iCount ++) {
            lPlayers[iCount].setBackground(Color.green);
            lScores[iCount].setBackground(Color.green);
        }
        iActivePlayer = (int)Math.round(Math.random() * iNumPlayers) - 1;
        lPlayers[iActivePlayer].setBackground(Color.red);
        lScores[iActivePlayer].setBackground(Color.red);

        // make the gameboard green for contrast
        setBackground(Color.green);

        showStatus();
        setCurrentPoints(-1);

        Panel pCenterPanel = new Panel(new GridLayout(1,3));

        // Do the dice...
        pDicePanel = new Panel(new GridLayout(3,2));
        for (int iCount = 0; iCount < 6; iCount ++) {
            dDice[iCount] = new Die(this);
            pDicePanel.add(dDice[iCount]);
        }
        pCenterPanel.add(pDicePanel);

        // show the card
        pCardPanel = new Panel(new BorderLayout());
        pCardPanel.add(cCurrent, BorderLayout.CENTER);
        pCenterPanel.add(pCardPanel);

        // show the scores
        Panel pScorePanel = new Panel(new FlowLayout());
        pScorePanel.setForeground(Color.BLUE);
        pScorePanel.add(lStatus);
        Panel pTextPanel = new Panel(new GridLayout(0,2));
        pTextPanel.add(new Label("At Risk: "));
        pTextPanel.add(lCurrent);
        pTextPanel.add(new Label());
        pTextPanel.add(new Label());
        for (int iCount = 0; iCount < iPlayersPossible; iCount ++) {
            pTextPanel.add(lPlayers[iCount]);
            if (iCount < iNumPlayers)
                pTextPanel.add(lScores[iCount]);
            else
                pTextPanel.add(new Label());
        }
        pScorePanel.add(pTextPanel);
        pCenterPanel.add(pScorePanel);
        
        add(pCenterPanel, BorderLayout.CENTER);
    
        // Do the control panel...
        Panel pBottom = new Panel();
        Button bButton = new Button("Flip Card");
        bButton.addActionListener(this);
        pBottom.add(bButton);
        bButton = new Button("Roll Dice");
        bButton.addActionListener(this);
        pBottom.add(bButton);
        bButton = new Button("Stop Here");
        bButton.addActionListener(this);
        pBottom.add(bButton);
        bButton = new Button("Help!");
        bButton.addActionListener(this);
        pBottom.add(bButton);
        bButton = new Button("Exit");
        bButton.addActionListener(this);
        pBottom.add(bButton);
        add(pBottom, BorderLayout.SOUTH);

        pack();
        addComponentListener(this);
        setSize(640, 420);
        setResizable(false);
        setVisible(true);
    }


    //////////////////////////////////////////////////
    // this is in case someone graps the corner or sides and changes
    // our display area...
    public void componentResized(ComponentEvent eEvent) {
        if (bDEBUG)
            System.out.println("In componentResized method");

        reDrawCards();
        reDrawDice();
    }

    //////////////////////////////////////////////////
    // catch those events!
    public void actionPerformed(ActionEvent eEvent) {
        if (bDEBUG)
            System.out.println("In actionPerformed method");

        String sAction = eEvent.getActionCommand();

        if (sAction.equals("Exit"))
            System.exit(0);

        if (sAction.equals("Help!"))
            new HelpBox(this);

        if (sAction.equals("Stop Here")) {
            if (iCurrentPoints != 0 && iFillReq == 0 && 
                cCurrent.getType() != Cards.MUSTBUST) {
                turnOver();
            }
        }

        if (sAction.equals("Flip Card")) {
            if (bMayFlip) {
                // you flipped a card!
                bMayFlip = false;
                bFill = false;

                doCardFlip();
            }
        }

        if (sAction.equals("Roll Dice")) {
            // for the ONE case where the Vengeance is chosen
            if (cCurrent.getType() == Cards.VENGEANCE)
                bMayFlip = false;

            for (int iCount = 0; iCount < 6; iCount ++) 
                dDice[iCount].roll();
            doDiceScoring();
        }
    }

    ///////////////////////////////////////////////////
    // do a card flip!
    private void doCardFlip() {
        if (bDEBUG)
            System.out.println("In doCardFlip method");

        // free up all dice, set turn points to 0
        iTurn = iTurn + 1;
        if (iTurn <= iTotalCards) {
            cCurrent.newCard();

            // Vengeance doesn't HAVE to be played...
            if (cCurrent.getType() == Cards.VENGEANCE)
                bMayFlip = true;

            newTurn();
        }
        if (iTurn > iTotalCards) {
            // really shouldn't see this
            cCurrent.blankCard();
        }
    }

    ///////////////////////////////////////////////////
    // turn over!
    private void turnOver() {
        if (bDEBUG)
            System.out.println("In turnOver method");

        // if you had a FILL, it's all over!
        bFill = false;

        // allow next player to flip a card
        bMayFlip = true;

        // but it's all over for you bud!
        bActiveTurn = false;

        // none of the the dice are free
        for (int iCount = 0; iCount < 6; iCount ++) 
            dDice[iCount].setAvailable(false);

        // totals up points
        refreshScore();

        // passes control to next player
        lPlayers[iActivePlayer].setBackground(Color.green);
        lScores[iActivePlayer].setBackground(Color.green);
        iActivePlayer = (iActivePlayer + 1) % iNumPlayers;
        lPlayers[iActivePlayer].setBackground(Color.red);
        lScores[iActivePlayer].setBackground(Color.red);
    }

    ///////////////////////////////////////////////////
    // it's a new turn...
    private void newTurn() {
        if (bDEBUG)
            System.out.println("In newTurn method");

        // we're in a turn!
        bActiveTurn = true;

        // they haven't busted yet!
        showStatus();

        // all the dice are free
        for (int iCount = 0; iCount < 6; iCount ++) 
            dDice[iCount].setAvailable(true);

        // obviously if the card is NO DICE the dice are
        // not free and the turn is over!
        if (cCurrent.getType() == Cards.NODICE) {
            // turn all dice green and inactive
            for (int iCount = 0; iCount < 6; iCount ++) 
                dDice[iCount].setAvailable(false);

            showStatus();
            setCurrentPoints(-1);
            turnOver();
        }

        // skip or play Vengeance here...
        if (cCurrent.getType() == Cards.VENGEANCE) {
            // determine player with high score
            iHighScorer = 0;
            int iHVal = 0;

// ok, known bug.  You COULD be tied for first, in which case the
// other guy should be taken down 2500 points.

            for (int iVal = 0; iVal < iScores.length; iVal ++)
                if (iScores[iVal] > iHVal) {
                    iHighScorer = iVal;
                    iHVal = iScores[iVal];
                }

            // if it's you, flip a new card
            if (iHighScorer == iActivePlayer) {
                doCardFlip();
            }
        }
    }   

    ///////////////////////////////////////////////////
    // this could be the end of your turn, or the start!
    public void showStatus() {
        if (bDEBUG)
            System.out.println("In showStatus method");

        int iCurrType = cCurrent.getType();

        // update busted state!
        if (iCurrType == Cards.NODICE) {
            lStatus.setText("No Dice, No Turn!");
            iFillReq = 0;
        }
        else {
            if (iCurrType == Cards.MUSTBUST) {
                lStatus.setText("Must Bust, no fear!");
                iFillReq = 0;
            }
            else {
                if (iCurrType == Cards.FILL1000 ||
                    iCurrType == Cards.VENGEANCE) {
                    lStatus.setText("MUST FILL, can't stop!");
                    iFillReq = 1;
                }
                else {
                    if (iCurrType == Cards.DOUBLETROUBLE) {
                        lStatus.setText("Must FILL TWICE!");
                        iFillReq = 2;
                    } else {
                        lStatus.setText("Bonus " +
                            (300 + (iCurrType * 100)) +
                            " on a FILL");
                        iFillReq = 0;
                    }
                }
            }
        }
    }

    ///////////////////////////////////////////////////
    // shows the current at-risk points!
    public void setCurrentPoints(int iVal) {
        if (bDEBUG)
            System.out.println("In setCurrentPoints method");

        if (iVal == -1)
            iCurrentPoints = 0;
        else
            iCurrentPoints = iCurrentPoints + iVal;

        lCurrent.setText(iCurrentPoints + " points");
    }

    ///////////////////////////////////////////////////
    // count up those points!  Check for a FILL
    public void doDiceScoring() {
        if (bDEBUG)
            System.out.println("In doDiceScoring method");

        // first check that turn has started
        if (! bActiveTurn || bFill)
            return;

        int iTPoints = 0;

        // look for the straight?
        String sRoll = " ";
        for (int iCount = 0; iCount < 6; iCount ++)
            if (dDice[iCount].isAvailable())
                sRoll = sRoll + dDice[iCount].getValue();
        if (sRoll.indexOf('1') > 0 && sRoll.indexOf('2') > 0 &&
            sRoll.indexOf('3') > 0 && sRoll.indexOf('4') > 0 &&
            sRoll.indexOf('5') > 0 && sRoll.indexOf('6') > 0) {
            iTPoints = 1500;

            // mark them ALL rolled!
            for (int iCount = 0; iCount < 6; iCount ++)
                dDice[iCount].setAvailable(false);
        } 

        // parse out the triplets? (could be two of them)
        for (int iCount = 0; iCount < 6; iCount ++)
            if (dDice[iCount].isAvailable()) {

                // get value of die
                int iCheck = dDice[iCount].getValue();
                // track how many found
                int iFound = 1;

                // look for two more die with same value and
                // available
                if (iCount < 4)
                    for (int iLeft = iCount + 1; iLeft < 6; iLeft ++)
                        if (dDice[iLeft].getValue() == iCheck &&
                            dDice[iLeft].isAvailable())
                            iFound = iFound + 1;

                if (iFound > 2) {
                    // mark THIS one no longer available
                    dDice[iCount].setAvailable(false);

                    // score the value
                    if (iCheck > 1)
                        iTPoints = iTPoints + (iCheck * 100);
                    else
                        iTPoints = iTPoints + 1000;

                    // find only two more and mark them not available
                    iFound = 2;
                    for (int iLeft = iCount + 1; iLeft < 6; iLeft ++)
                        if (dDice[iLeft].getValue() == iCheck &&
                            dDice[iLeft].isAvailable() && iFound > 0) {

                            dDice[iLeft].setAvailable(false);
                            iFound = iFound - 1;
                        }
                }
                
            }
        

        // count up loose 1's
        for (int iCount = 0; iCount < 6; iCount ++)
            if (dDice[iCount].isAvailable() && 
                dDice[iCount].getValue() == 1) {
                dDice[iCount].setAvailable(false);
                iTPoints = iTPoints + 100;
            }

        // count up loose 5's
        for (int iCount = 0; iCount < 6; iCount ++)
            if (dDice[iCount].isAvailable() && 
                dDice[iCount].getValue() == 5) {
                dDice[iCount].setAvailable(false);
                iTPoints = iTPoints + 50;
            }

        // did they throw a FILL?
        bFill = true;
        for (int iCount = 0; iCount < 6; iCount ++)
            if (dDice[iCount].isAvailable()) {
                bFill = false;
            }

        // could have NO points, busted!
        if (iTPoints == 0) {
            if (cCurrent.getType() != Cards.MUSTBUST)
                setCurrentPoints(-1);
            lStatus.setText("BUSTED! Turn over");
            turnOver();
        } else {
            setCurrentPoints(iTPoints);
            if (bFill) {
                // there are no active dice left
                if (iFillReq > 0)
                    iFillReq = iFillReq - 1;

                // if it's a mustbust don't let them flip a card
                if (cCurrent.getType() == Cards.MUSTBUST) {
                    for (int iCount = 0; iCount < 6; iCount ++)
                        dDice[iCount].setAvailable(true);
                    bFill = false;
                    bMayFlip = false;
                } else
                    bMayFlip = true;

                if (cCurrent.getType() == Cards.BONUS300)
                    setCurrentPoints(300);

                if (cCurrent.getType() == Cards.BONUS400)
                    setCurrentPoints(400);

                if (cCurrent.getType() == Cards.BONUS500)
                    setCurrentPoints(500);

                if (cCurrent.getType() == Cards.FILL1000)
                    setCurrentPoints(1000);

                if (cCurrent.getType() == Cards.DOUBLETROUBLE) {
                    if (iFillReq == 1) {
                        for (int iCount = 0; iCount < 6; iCount ++)
                            dDice[iCount].setAvailable(true);
                        bFill = false;
                        bMayFlip = false;
                    } else {
                        // can't lose double!
                        refreshScore();
                        lStatus.setText("Added! Play on or stop");
                    }
                }

                if (cCurrent.getType() == Cards.VENGEANCE) {
                    // can't lose these!
                    refreshScore();
                    lStatus.setText("Vengeance! Play on or stop");

// known bug - could be multiple people tied with this score.
// I suppose we could search for them...

                    int iVal = iScores[iHighScorer] - 2500;

                    if (iVal > 0)
                        iScores[iHighScorer] = iVal;
                    else
                        iScores[iHighScorer] = 0;
                    lScores[iHighScorer].setText("" +
                        iScores[iHighScorer]);
                }
            } 
        }
    }


    ///////////////////////////////////////////////////
    // Generic redraw of active score...
    private void refreshScore() {
        if (bDEBUG)
            System.out.println("In refreshScore method");

        iScores[iActivePlayer] = 
            iScores[iActivePlayer] + iCurrentPoints;
        lScores[iActivePlayer].setText("" +
            iScores[iActivePlayer]);

        setCurrentPoints(-1);
    }


    ///////////////////////////////////////////////////
    // This does the redrawing for us...
    public void reDrawCards() {
        if (bDEBUG)
            System.out.println("In reDrawCards method");

        if (pCardPanel != null) {
            Dimension dSize = pCardPanel.getSize();
            iCellX = (int)(dSize.getWidth());
            iCellY = (int)(dSize.getHeight());

            cCurrent.myRePaint(iCellX, iCellY);
        }
    }

    ///////////////////////////////////////////////////
    // This does the redrawing for us...
    public void reDrawDice() {
        if (bDEBUG)
            System.out.println("In reDrawDice method");

        if (pDicePanel != null) {
            Dimension dSize = pDicePanel.getSize();
            iCellX = (int)(dSize.getWidth() / 3);
            iCellY = (int)(dSize.getHeight() / 3);
    
            for (int iCount = 0; iCount < 6; iCount ++)
                dDice[iCount].myRePaint(iCellX, iCellY);
        }
    }

    //////////////////////////////////////////////////
    // needed overridden to use interface for component
    public void componentMoved(ComponentEvent eEvent) {}
    public void componentShown(ComponentEvent eEvent) {}
    public void componentHidden(ComponentEvent eEvent) {}
}
