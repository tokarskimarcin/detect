package panels;

import utilities.CRC;
import utilities.MyConverter;
import utilities.TextFormat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-23.
 */
public class CRCPanel extends JPanel {

    private ActionListener actionListener;

    private Border grayLineBorder;
    private JPanel withChecksumPanel;
    private JPanel withChecksumDisruptedPanel;
    private JPanel checksumCheckedPanel;

    private JLabel caption;
    private JLabel resultsLabel;
    private JLabel withChecksumLabel;
    private JLabel withChecksumDisruptedLabel;
    private JLabel checksumCheckedLabel;

    public CRCPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents() {
        withChecksumPanel = new JPanel();
        withChecksumDisruptedPanel = new JPanel();
        checksumCheckedPanel = new JPanel();

        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        caption = new JLabel("<html>1 - Dane wejściowe z sumą kontrolną<br>" +
                "2 - Zakłócone dane wejściowe z sumą kontrolną<br>" +
                "3 - Sprawdzenie poprawności transmisji<br>");

        withChecksumLabel = new JLabel("");
        withChecksumDisruptedLabel = new JLabel("");
        checksumCheckedLabel = new JLabel("");
        resultsLabel = new JLabel("");

        withChecksumPanel.add(withChecksumLabel);
        withChecksumDisruptedPanel.add(withChecksumDisruptedLabel);
        checksumCheckedPanel.add(checksumCheckedLabel);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        withChecksumPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "1"));
        withChecksumDisruptedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "2"));
        checksumCheckedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "3"));

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(withChecksumPanel, c);

        c.gridx = 1;
        panel.add(withChecksumDisruptedPanel, c);

        c.gridx = 2;
        panel.add(checksumCheckedPanel, c);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension((int) Components.FRAME_SIZE.getWidth() - 10, 300));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(caption, c);
        c.gridx = 1;
        add(resultsLabel, c);

        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPane, c);
    }

    public void setLabelsText(ArrayList<Integer> withChecksum, ArrayList<Integer> withChecksumDisrupted, ArrayList<Integer> checksumChecked, CRC.POLYNOMIAL p, ArrayList<Integer> intNoiseShifted) {
        String tempStr = MyConverter.arrayIntToBinaryString(withChecksum, 8 + CRC.getDegreeOfPolynomial(p));
        StringBuilder temp = new StringBuilder("<html>");
        StringBuilder temp2 = new StringBuilder();
        for (int i = 0; i < tempStr.length(); i++) {
            if (tempStr.charAt(i) == '\n') {
                temp.append(TextFormat.colorTextHtml(temp2.toString(), CRC.getPolynomialMask(p), "green")).append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(tempStr.charAt(i));
        }

        withChecksumLabel.setText(temp.toString());

        tempStr = MyConverter.arrayIntToBinaryString(withChecksumDisrupted, 8 + CRC.getDegreeOfPolynomial(p));
        temp = new StringBuilder("<html>");
        temp2 = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < tempStr.length(); i++) {
            if (tempStr.charAt(i) == '\n') {
                if (counter < intNoiseShifted.size())
                    temp.append(TextFormat.colorTextHtml(temp2.toString(), intNoiseShifted.get(counter), "red")).append("<br>");
                else
                    temp.append(temp2.toString()).append("<br>");
                temp2 = new StringBuilder();
                counter++;
            } else
                temp2.append(tempStr.charAt(i));
        }

        withChecksumDisruptedLabel.setText(temp.toString());

        int characterDisruptedDetectedCRC = 0;
        temp = new StringBuilder("<html>");
        for(int i = 0; i < checksumChecked.size();i++){
            if(checksumChecked.get(i) == 0){
                if(i < intNoiseShifted.size())
                    if(intNoiseShifted.get(i) > 0){
                        temp.append("<font color=red>nie wykryto</font>");}
                        else
                        temp.append("<font color=green>nie wykryto</font>");
                else
                        temp.append("<font color=green>nie wykryto</font>");
            }else{
                temp.append("<font color=green>błąd transmisji</font>");
                characterDisruptedDetectedCRC++;
            }
            temp.append("<br>");
        }

        checksumCheckedLabel.setText(temp.toString());


        resultsLabel.setText("<html>Transmisja zakłócona, wykryta : " + characterDisruptedDetectedCRC + "<br>" +
                "Transmisja zakłócona, niewykryta: " + (Components.characterDisrupted - characterDisruptedDetectedCRC) + "<br>" +
                "Liczba bitów wiadomości: " + 8 * Components.intInputData.size() + "<br>" +
                "Liczba bitów nadmiarowych: " + CRC.getDegreeOfPolynomial(p)*Components.intInputData.size());
    }

    public void setLabelsTextT(ArrayList<Integer> checksum, ArrayList<Integer>checksumDisrupted, ArrayList<Boolean> checkedChecksum){
        StringBuilder temp = new StringBuilder("<html>");
        for (Integer aChecksum : checksum) {
            temp.append("0x").append(Integer.toHexString(aChecksum)).append("<br>");
        }
        withChecksumLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        for (Integer aChecksum : checksumDisrupted) {
            temp.append("0x").append(Integer.toHexString(aChecksum)).append("<br>");
        }
        withChecksumDisruptedLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        for (Boolean aCheckedChecksum : checkedChecksum) {
            if (aCheckedChecksum) {
                temp.append("nie wykryto<br>");
            } else
                temp.append("błąd transmisji<br>");
        }
        checksumCheckedLabel.setText(temp.toString());
    }

    public JLabel getCaption(){
        return caption;
    }
    public JLabel getResultsLabel(){return resultsLabel;}
}



