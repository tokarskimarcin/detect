package panels;

import utils.Hamming;
import utils.MyConverter;
import utils.TextFormat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-21.
 */
public class HammingPanel extends JPanel {
    private ActionListener actionListener;

    private Border grayLineBorder;
    private JPanel withHammingPanel;
    private JPanel withHammingDisruptedPanel;
    private JPanel parityCheckedPanel;
    private JPanel isCorrectedPanel;
    private JPanel correctedPanel;
    private JPanel outputDataPanel;

    private JLabel title;
    private JLabel resultsLabel;
    private JLabel withHammingLabel;
    private JLabel withHammingDisruptedLabel;
    private JLabel disruptionPositionLabel;
    private JLabel isCorrectedLabel;
    private JLabel correctedLabel;
    private JLabel outputDataLabel;

    public HammingPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents() {
        withHammingPanel = new JPanel();
        withHammingDisruptedPanel = new JPanel();
        parityCheckedPanel = new JPanel();
        isCorrectedPanel = new JPanel();
        correctedPanel = new JPanel();
        outputDataPanel = new JPanel();

        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        Components.buttonList.add(Components.BUTTONS.BACKFROMHAMMING.getId(), new JButton(Components.BUTTONS.BACKFROMHAMMING.getName()));

        title = new JLabel("<html>1 - Dane wejściowe z kodem Hamminga<br>" +
                "2 - Zakłócone dane wejściowe z kodem Hamminga<br>" +
                "3 - Pozycja wykrytych błędów<br>" +
                "4 - Czy błędy skorygowane dobrze?<br>" +
                "5 - Sygnał wyjściowy z danymi kontrolnymi<br>"+
                "6 - Sygnał wyjściowy bez danych kontrolnych");
        withHammingLabel = new JLabel("");
        withHammingDisruptedLabel = new JLabel("");
        disruptionPositionLabel = new JLabel("");
        resultsLabel = new JLabel("");
        isCorrectedLabel = new JLabel("");
        correctedLabel = new JLabel("");
        outputDataLabel = new JLabel("");

        withHammingPanel.add(withHammingLabel);
        withHammingDisruptedPanel.add(withHammingDisruptedLabel);
        parityCheckedPanel.add(disruptionPositionLabel);
        isCorrectedPanel.add(isCorrectedLabel);
        correctedPanel.add(correctedLabel);
        outputDataPanel.add(outputDataLabel);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        withHammingPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "1"));
        withHammingDisruptedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "2"));
        parityCheckedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "3"));
        isCorrectedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "4"));
        correctedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"5"));
        outputDataPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "6"));


        c.gridwidth = 1;
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(withHammingPanel, c);

        c.gridx = 1;
        panel.add(withHammingDisruptedPanel, c);

        c.gridx = 2;
        panel.add(parityCheckedPanel, c);

        c.gridx = 3;
        panel.add(isCorrectedPanel, c);

        c.gridx = 4;
        panel.add(correctedPanel, c);

        c.gridx = 5;
        panel.add(outputDataPanel, c);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension((int) Components.FRAME_SIZE.getWidth() - 10, 300));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(title, c);
        c.gridx = 1;
        add(resultsLabel, c);

        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPane, c);

        c.gridwidth = 2;
        c.gridy = 2;
        JButton button = Components.buttonList.get(Components.BUTTONS.BACKFROMHAMMING.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.BACKFROMHAMMING.getId()), c);

        /*c.gridy = 3;
        button = Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()), c);*/
    }

    public void setLabelsText(ArrayList<Integer> withHamming, String withHammingDisrupted, ArrayList<Integer> disruptionPosition, ArrayList<Integer> correctedDisruption, ArrayList<Integer> outputData) {
        StringBuilder temp = new StringBuilder("<html>");
        StringBuilder temp2 = new StringBuilder();
        String tempStr = MyConverter.arrayIntToBinaryString(withHamming, Hamming.getHammingifiedLength(8));
        for (int i = 0; i < tempStr.length(); i++) {
            if (tempStr.charAt(i) == '\n') {
                temp.append(TextFormat.colorTextHtml(temp2.toString(), Hamming.getMask(Hamming.getHammingifiedLength(8)), "green"));
                temp.append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(tempStr.charAt(i));
        }
        withHammingLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        temp2 = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < withHammingDisrupted.length(); i++) {
            if (withHammingDisrupted.charAt(i) == '\n') {
                if (counter < Components.intNoise.size()) {
                    if (Components.intNoise.get(counter) > 0)
                        temp.append(TextFormat.colorTextHtml(temp2.toString(), Components.intNoise.get(counter), "red"));
                    else
                        temp.append(temp2.toString());
                    counter++;
                } else
                    temp.append(temp2.toString());
                temp.append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(withHammingDisrupted.charAt(i));
        }
        withHammingDisruptedLabel.setText(temp.toString());

        Components.characterDisruptedDetectedHamming=0;
        temp = new StringBuilder("<html>");
        for (Integer aDisruptionPosition : disruptionPosition) {
            if (aDisruptionPosition > 0) {
                Components.characterDisruptedDetectedHamming++;
                temp.append(aDisruptionPosition);
            }else
                temp.append("-");
            temp.append("<br>");
        }
        disruptionPositionLabel.setText(temp.toString());

        Components.characterDisruptedCorrectedHamming=0;
        temp = new StringBuilder("<html>");
        for (int i = 0; i < correctedDisruption.size(); i++) {
            if (disruptionPosition.get(i) > 0) {
                if (withHamming.get(i).equals(correctedDisruption.get(i))) {
                    temp.append("<font color=green>dobrze</font><br>");
                    Components.characterDisruptedCorrectedHamming++;
                } else
                    temp.append("<font color=red>źle</font><br>");
            } else
                temp.append("-<br>");
        }
        isCorrectedLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        temp2 = new StringBuilder();
        tempStr = MyConverter.arrayIntToBinaryString(correctedDisruption, Hamming.getHammingifiedLength(8));
        counter = 0;
        for(int i=0; i < tempStr.length(); i++){
            if(tempStr.charAt(i)=='\n'){
                temp.append(TextFormat.colorTextHtml(temp2.toString(),1<<(disruptionPosition.get(counter)-1),"green")).append("<br>");
                temp2 = new StringBuilder();
                counter ++;
            }else{
                if(temp2.toString().equals("0b")){
                    temp.append(temp2.toString());
                    temp2 = new StringBuilder();
                }
                temp2.append(tempStr.charAt(i));
            }
        }
        correctedLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        tempStr = MyConverter.arrayIntToBinaryString(outputData,8);
        for(int i=0;i < tempStr.length(); i++){
            if(tempStr.charAt(i) == '\n'){
                temp.append("<br>");
            }else
                temp.append(tempStr.charAt(i));
        }
        outputDataLabel.setText(temp.toString());

        resultsLabel.setText("<html>Transmisja zakłócona, wykryta: "+Components.characterDisruptedDetectedHamming+"<br>"+
                "Transmisja zakłócona, niewykryta: "+ (Components.characterDisrupted-Components.characterDisruptedDetectedHamming)+"<br>"+
                "Źle skorygowane bajty wiadomości: "+(Components.characterDisruptedDetectedHamming-Components.characterDisruptedCorrectedHamming)+"<br>"+
                "Liczba bitów wiadomości: "+ 8 * Components.intInputData.size()+"<br>"+
                "Liczba bitów danych kontrolnych: "+
                MyConverter.integerToBinary(Hamming.getMask(Hamming.getHammingifiedLength(8)),Hamming.getHammingifiedLength(8))
                        .chars().filter(num -> num =='1').count() *  Components.intInputData.size());
    }
}
