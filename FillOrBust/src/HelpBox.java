/* *****************************************************
 * @(#)HelpBox.java   1.0  11/20/06 17:40:34
 * @author Randy Crihfield
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;


class HelpBox extends Dialog implements ActionListener {

    Frame fParent;
    String sHelpSource = "HelpText";

    HelpBox(Frame fRent) {
        super(fRent, " Fill or Bust Help! ", true);

        fParent = fRent;
        
        // stuff it full of text
        Panel pMainText = new Panel();
        pMainText.add(getContents(), BorderLayout.CENTER);
        add(pMainText, BorderLayout.CENTER);

        // allow for the dismissal button
        Button bOK = new Button("OK");
        bOK.setBackground(Color.green);
        bOK.addActionListener(this);
        add(bOK, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setVisible(true);
    }

    ////////////////////////////////////////////////////

    private TextArea getContents() {
        TextArea tReturn = new TextArea("", 20, 80,
            TextArea.SCROLLBARS_VERTICAL_ONLY);

        StringBuffer sBuffer = new StringBuffer();
        BufferedReader bRead = null;

        try {
            bRead = new BufferedReader(new FileReader(
                new File(sHelpSource)));
            String sLine = null;

            while((sLine = bRead.readLine()) != null) {
                sBuffer.append(sLine);
                sBuffer.append(System.getProperty("line.separator"));
            }

            bRead.close();
        }
        catch(Exception eExcept) {
            sBuffer.append("Problem opening help file!");
        }

        tReturn.append(sBuffer.toString());

        return(tReturn);
    }

    ////////////////////////////////////////////////////

    public void actionPerformed(ActionEvent eEvent) {
        Button bTmp = (Button)eEvent.getSource();

        if (bTmp.getLabel().equals("OK")) {
            setVisible(false);
            dispose();
        }
    }
}
