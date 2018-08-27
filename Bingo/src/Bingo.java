
/** Written in 2013 by Randy Crihfield to display "bingo"
 * questions for a New Year's Eve game
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Bingo implements ActionListener {

    FileIO            fIO    = null;
    ArrayList<String> lUsed  = new ArrayList(0);
    ArrayList<String> lLeft  = null;
    PlayMenu          pMenu  = null;
    GameBoard         gBoard = null;

    /**
     * The constructor, which only needs the data to take off, eh!
     */
    public Bingo(String sDataFile) {

        fIO    = new FileIO(sDataFile);
        lLeft  = fIO.getKeys();
        pMenu  = new PlayMenu(this);
        gBoard = new GameBoard(this);
    }

    /**
     * This does the heavy lifting for showing used or
     * remaining keys...
     */
    private void showKeys(String sOption) {

        String sMessage = "";

        // build message
        if (sOption.equals("Used")) {
            for (String sKey : lUsed) {
                sMessage = sMessage + "\n" + sKey;
            }
        } else {
            for (String sKey : lLeft) {
                sMessage = sMessage + "\n" + sKey;
            }
        }

        if (sMessage.equals("")) {
            sMessage = "No keys have been used!";
        }

        JOptionPane.showConfirmDialog( null, sMessage, "List " + 
            sOption + " Keys", JOptionPane.CLOSED_OPTION);
    }

    /**
     * This gets the next key/value pair and populates the
     * game board - then sets it visible!
     */
    private void updateGameBoard() {

        // should NEVER happen, but if we run out of clues circle around!
        if (lLeft.size() == 0) {
            lLeft  = fIO.getKeys();
        }

        String sAns = lLeft.get(0);
        String sQues = fIO.getValue(sAns);

        // a little housekeeping...
        lUsed.add(sAns);
        lLeft.remove(sAns);

        gBoard.setCurrent(sQues, sAns);
        gBoard.repaint();
        gBoard.setVisible(true);
    }

    public void actionPerformed(ActionEvent eEvent) {
        String sAction = eEvent.getActionCommand();

        if (sAction.equals("Play")) {
            pMenu.setVisible(false);
            updateGameBoard();
        }

        if (sAction.equals("List Used Keys")) {
            showKeys("Used");
        }

        if (sAction.equals("List Remaining Keys")) {
            showKeys("All");
        }

        if (sAction.equals("Next")) {
            updateGameBoard();
        }

        if (sAction.equals("Show Answer")) {
            gBoard.setAnswerVisible();
        }

        if (sAction.equals("Main Menu")) {
            gBoard.setVisible(false);
            pMenu.setVisible(true);
        }

        if(sAction.equals("Exit")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("\nA data file is required!\n");
            System.exit(1);
        }

        new Bingo(args[0]);
    }
}
