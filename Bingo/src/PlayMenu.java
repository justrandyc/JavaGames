
/** Written in 2013 by Randy Crihfield to display "bingo"
 * questions for a New Year's Eve game
 */

import java.awt.*;
import java.awt.event.*;

public class PlayMenu extends Frame {

    public PlayMenu(ActionListener aParent) {

        setTitle("Custom Bingo Driver NYE 2014");
        setBackground(Color.blue);
        setForeground(Color.white);
        setSize(300, 250);

        // do the control buttons...
        Panel pButtonPanel = new Panel(new GridLayout(4, 1));

        // Play Button
        Button bPlay = new Button("Play");
        bPlay.addActionListener(aParent);
        bPlay.setBackground(Color.blue);
        bPlay.setForeground(Color.white);
        pButtonPanel.add(bPlay);

        // List Used Button
        Button bUsed = new Button("List Used Keys");
        bUsed.addActionListener(aParent);
        bUsed.setBackground(Color.blue);
        bUsed.setForeground(Color.white);
        pButtonPanel.add(bUsed);

        // List Remaining Button
        Button bLeft = new Button("List Remaining Keys");
        bLeft.addActionListener(aParent);
        bLeft.setBackground(Color.blue);
        bLeft.setForeground(Color.white);
        pButtonPanel.add(bLeft);

        // Exit Button
        Button bExit = new Button("Exit");
        bExit.addActionListener(aParent);
        bExit.setBackground(Color.blue);
        bExit.setForeground(Color.white);
        pButtonPanel.add(bExit);

        add(pButtonPanel);

        setVisible(true);
    }
}
