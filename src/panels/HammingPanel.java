package panels;

import utils.Hamming;
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
    private JPanel outputDataPanel;

    private JLabel title;
    private JLabel resultsLabel;
    private JLabel withHammingLabel;
    private JLabel withHammingDisruptedLabel;
    private JLabel disruptionPositionLabel;
    private JLabel isCorrectedLabel;
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
        outputDataPanel = new JPanel();

        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        Components.buttonList.add(Components.BUTTONS.BACKFROMHAMMING.getId(), new JButton(Components.BUTTONS.BACKFROMHAMMING.getName()));

        title = new JLabel("<html>1 - Dane wejściowe z kodem Hamminga<br>" +
                "2 - Zakłócone dane wejściowe z kodem Hamminga<br>" +
                "3 - Pozycja wykrytych błędów<br>"+
                "4 - Czy błędy skorygowane?<br>"+
                "5 - Sygnał wyjściowy bez danych kontrolnych");
        withHammingLabel = new JLabel("");
        withHammingDisruptedLabel = new JLabel("");
        disruptionPositionLabel = new JLabel("");
        resultsLabel = new JLabel("");
        isCorrectedLabel = new JLabel("");
        outputDataLabel = new JLabel("");

        withHammingPanel.add(withHammingLabel);
        withHammingDisruptedPanel.add(withHammingDisruptedLabel);
        parityCheckedPanel.add(disruptionPositionLabel);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        withHammingPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "1"));
        withHammingDisruptedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "2"));
        parityCheckedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "3"));

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(withHammingPanel, c);

        c.gridx = 1;
        panel.add(withHammingDisruptedPanel, c);

        c.gridx = 2;
        panel.add(parityCheckedPanel, c);

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

    public void setLabelsText(String withHamming, String withHammingDisrupted, ArrayList<Integer> disruptionPosition) {
        StringBuilder temp = new StringBuilder("<html>");
        StringBuilder temp2 = new StringBuilder();
        for (int i = 0; i < withHamming.length(); i++) {
            if (withHamming.charAt(i) == '\n') {
                temp.append(TextFormat.colorTextHtml(temp2.toString(), Hamming.getMask(Hamming.getHammingifiedLength(8)), "green"));
                temp.append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(withHamming.charAt(i));
        }
        withHammingLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        temp2 = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < withHamming.length(); i++) {
            if (withHamming.charAt(i) == '\n') {
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

        temp = new StringBuilder("<html>");
        for (int i = 0; i < disruptionPosition.size(); i++) {
            if(disruptionPosition.get(i) >0)
                Components.characterDisruptedDetectedHamming++;
            temp.append(disruptionPosition.get(i));
            /*if(Components.intNoise.size() > i)
                temp.append(Components.intNoise.get(i));*/
            temp.append("<br>");
        }

        disruptionPositionLabel.setText(temp.toString());
    }
}
