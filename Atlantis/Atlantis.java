
/** Written in 2014 by Randy Crihfield because I am nostalgic
 *  for "Battle for Atlantis" by William Soleau
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class Atlantis implements ActionListener {

    GameBoard gBoard     = null;
    ActionBox aBox       = null;
    String    sActive    = null;
    String    sFoe       = null;
    int       iMaxStart  = 0;
    int       iLevel     = 0;
    int       iBlueTotal = 0;
    int       iBrnTotal  = 0;
    int       iRedTotal  = 0;
    int       iGrnTotal  = 0;

    /**
     * The constructor
     */
    public Atlantis() {
// TODO: show welcome screen
        gBoard = new GameBoard(this);
        aBox   = new ActionBox(this);
        startNew();
        setupBoard();
        debug();
    }

    private void startNew() {
        // Select Difficulty Level
        Object[] oLevels = {" ? ", " 1 ", " 2 ", " 3 ", " 4 ", " 5 ",
            " 6 ", " 7 "};
        iLevel = JOptionPane.showOptionDialog(gBoard,
            "Choose Level of Difficulty\n1=Easy 4=Average\n" +
            "7=Hard ?=Random", "Level of Difficulty",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, oLevels, oLevels[4]);

        if (iLevel == 0) {
            iLevel = randRange(1, 7);
        }

        Object[] oArmies = {" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ",
            " 7 ", " 8 "};
        iMaxStart = 1 + JOptionPane.showOptionDialog(gBoard,
            "Starting number of Armies Possible?\n\n1 to 8\n" +
            "3/4 Recommended", "Max Armies to Start",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, oArmies, oArmies[3]);

        // How many nations each?
        iBlueTotal = 19 - (iLevel * 2);
        iBrnTotal  = randRange(5, 7) + iLevel;
        iGrnTotal  = randRange(5, 7) + iLevel;
        iRedTotal  = 43 - iBlueTotal - iBrnTotal - iGrnTotal;
    }

    private void setupBoard() {
        // set nation's color and number of armies
        int[] iNations = new int[43];
        for (int iCounter = 0; iCounter < 43; iCounter++)
            iNations[iCounter] = iCounter;

        shuffleArray(iNations);

        // do them in order... Blue, Brown, Green, Red
        int iCounter = 0;
        int iSegment = iBlueTotal;
        for (; iCounter < iSegment; iCounter++) {
            gBoard.setCountryColor(iNations[iCounter], "blue");
            gBoard.setCountryArmies(iNations[iCounter], randomArmy(iMaxStart));
        }
        iCounter = iSegment;
        iSegment = iSegment + iBrnTotal;
        for (; iCounter < iSegment; iCounter++) {
            gBoard.setCountryColor(iNations[iCounter], "brown");
            gBoard.setCountryArmies(iNations[iCounter], randomArmy(iMaxStart));
        }
        iCounter = iSegment;
        iSegment = iSegment + iGrnTotal;
        for (; iCounter < iSegment; iCounter++) {
            gBoard.setCountryColor(iNations[iCounter], "green");
            gBoard.setCountryArmies(iNations[iCounter], randomArmy(iMaxStart));
        }
        iCounter = iSegment;
        iSegment = iSegment + iRedTotal;
        for (; iCounter < iSegment; iCounter++) {
            gBoard.setCountryColor(iNations[iCounter], "red");
            gBoard.setCountryArmies(iNations[iCounter], randomArmy(iMaxStart));
        }
// TODO: KEEP GOING HERE!!
    }

    private void shuffleArray(int[] iToShuffle) {
        Random rRand = ThreadLocalRandom.current();

        for (int iIndex = iToShuffle.length - 1; iIndex > 0; iIndex--) {
            int index = rRand.nextInt(iIndex + 1);

            // Swap
            int a = iToShuffle[index];
            iToShuffle[index] = iToShuffle[iIndex];
            iToShuffle[iIndex] = a;
        }
    }

    private int randomArmy(int iNum) {
        return (new Random().nextInt(iNum) + 1);
    }

    public void debug() {
        System.out.println("Level is " + iLevel);
        System.out.println("MaxArmies is " + iMaxStart);
        System.out.println("Blue has " + iBlueTotal + " countries");
        System.out.println("Brown has " + iBrnTotal + " countries");
        System.out.println("Green has " + iGrnTotal + " countries");
        System.out.println("Red has " + iRedTotal + " countries");
    }

    public int randRange(int iMin, int iMax) {
        return (iMin + (int)(Math.random() * ((iMax - iMin) + 1)));
    }

    public void actionPerformed(ActionEvent eEvent) {
        String sAction = eEvent.getActionCommand();

        if (sAction.contains("Land_")) {
            String sNation = eEvent.getActionCommand();
            // check we can only select OUR countries!
            sActive = sNation;
            System.out.println(sNation + " Selected");
        }

        if (sAction.startsWith("Against")) {
            sFoe = sAction;
            System.out.println("Attack " + sAction);
        }

        if(sAction.equals("Repeat Attack")) {
            if (sFoe != null) {
                System.out.println("Attack " + sFoe + " again!");
            } else {
                System.out.println("No enemy selected!");
            }
        }

        if(sAction.equals("Pass")) {
            sFoe = null;
            System.out.println("Pass");
        }

        if(sAction.equals("Move")) {
            sFoe = null;
            System.out.println("Move");
        }

        if(sAction.equals("Quit")) {
            int iReturn = JOptionPane.showConfirmDialog(gBoard,
            "Are you sure you want to quit?",
            "Quit Confirmation",
            JOptionPane.YES_NO_OPTION);

            if (iReturn == 0) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {

//        if (args.length < 1) {
//            System.out.println("\nA data file is required!\n");
//            System.exit(1);
//        }

        new Atlantis();
    }
}
