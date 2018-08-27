import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ActionBox extends JFrame {

    public ActionBox(ActionListener aParent) {
        super();

        JButton   bTemp = null;
        JMenuItem mTemp = null;

        getContentPane().setBackground(Color.red);
        getContentPane().setLayout(new GridLayout(5, 1));
        setAlwaysOnTop(true);

        // Create the attack menu
        JMenuBar mBar = new JMenuBar();
        mBar.setBackground(Color.red);
        JMenu mAttack = new JMenu("Attack!");
        mBar.add(mAttack);

        String[] sAttacks = new String[] {"Land_2", "Land_1"};
        for (String sInstance : sAttacks) {
            mTemp = new JMenuItem("Against " + sInstance);
            mTemp.setBackground(Color.red);
            mTemp.addActionListener(aParent);
            mAttack.add(mTemp);
        }
        getContentPane().add(mBar);

        // Create the action buttons
        String[] sActions = new String[] {"Repeat Attack", "Pass",
            "Move", "Quit"};
        for (String sInstance : sActions) {
            bTemp = new JButton(sInstance);
            bTemp.setBackground(Color.red);
            bTemp.addActionListener(aParent);
            getContentPane().add(bTemp);
        }

        setLocation(1600, 75);
        setSize(200, 200);
        setVisible(true);
    }
}
