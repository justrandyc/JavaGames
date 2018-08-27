import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JFrame {

    DrawCountry[] dNations = new DrawCountry[43];
    Nation[]      nNation  = new Nation[43];

    public GameBoard(ActionListener aParent) {
        super();

        setTitle("Battle For Atlantis");
        getContentPane().setBackground(Color.blue);
        getContentPane().setLayout(null);

        // Create country maps
        for (int iNum = 0; iNum < 43; iNum++) {
            nNation[iNum] = new Nation("Land_" + (iNum + 1));
            dNations[iNum] = new DrawCountry(nNation[iNum]);
            dNations[iNum].setBackground(Color.gray);
            dNations[iNum].setBounds(
                nNation[iNum].getXBound(), nNation[iNum].getYBound(),
                nNation[iNum].getMaxX(), nNation[iNum].getMaxY());
            dNations[iNum].addActionListener(aParent);
            getContentPane().add(dNations[iNum]);
        }

        setLocation(40, 20);
        setSize(1800, 1010);
        setVisible(true);
    }

    public void setCountryColor(int iNum, String sColor) {
        if (sColor == "blue")
            dNations[iNum].setBackground(Color.cyan);
        if (sColor == "red")
            dNations[iNum].setBackground(new Color(165,42,42));
        if (sColor == "green")
            dNations[iNum].setBackground(Color.green);
        if (sColor == "brown")
            dNations[iNum].setBackground(new Color(222,184,135));
    }

    public void setCountryArmies(int iNum, int iNumArmies) {
        dNations[iNum].updateArmies(iNumArmies);
    }
}
