
/** Written in 2013 by Randy Crihfield to display "bingo"
 * questions for a New Year's Eve game
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JFrame {

    JTextArea tQuestion = new JTextArea("Some Dummy");
    JTextArea tAnswer   = new JTextArea("Some Dummy");

    String    sHoldAns  = "";

    public GameBoard(ActionListener aParent) {

        setTitle("Custom Bingo Driver NYE 2014");
        setBackground(Color.BLUE);
        setForeground(Color.WHITE);
        setSize(600, 500);
        setLayout(new BorderLayout());

        // do the control buttons...
        JPanel pButtonPanel = new JPanel(new GridLayout(3,1));

        // Next Button
        JButton bNext = new JButton("Next");
        bNext.addActionListener(aParent);
        bNext.setBackground(Color.BLUE);
        bNext.setForeground(Color.WHITE);
        pButtonPanel.add(bNext);

        // Show Answer Button
        JButton bShow = new JButton("Show Answer");
        bShow.addActionListener(aParent);
        bShow.setBackground(Color.BLUE);
        bShow.setForeground(Color.WHITE);
        pButtonPanel.add(bShow);

        // Main Menu Button
        JButton bMenu = new JButton("Main Menu");
        bMenu.addActionListener(aParent);
        bMenu.setBackground(Color.BLUE);
        bMenu.setForeground(Color.WHITE);
        pButtonPanel.add(bMenu);

        // blend it all together
        JPanel pAnswerPanel = new JPanel(new GridLayout(2,1));
        tQuestion.setLineWrap(true);
        tQuestion.setWrapStyleWord(true);
        tQuestion.setBackground(Color.BLUE);
        tQuestion.setForeground(Color.WHITE);
        tQuestion.setFont(new Font("Serif", Font.PLAIN, 52));
        pAnswerPanel.add(tQuestion);

        tAnswer.setBackground(Color.BLUE);
        tAnswer.setForeground(Color.WHITE);
        tAnswer.setFont(new Font("Serif", Font.PLAIN, 52));
        pAnswerPanel.add(tAnswer);

        // put it all together
        add(pAnswerPanel, BorderLayout.CENTER);
        add(pButtonPanel, BorderLayout.EAST);
    }

    /**
     * Sets the Question, Answer values
     */
    public void setCurrent(String sQues, String sAns) {
        tQuestion.setText("\n" + sQues);
        sHoldAns = "\n  " + sAns;
        tAnswer.setText("");
    }

    /**
     * Allows one to see the answer...
     */
    public void setAnswerVisible() {
        tAnswer.setText(sHoldAns);
    }
}
