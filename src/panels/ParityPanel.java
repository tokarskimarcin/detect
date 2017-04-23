package panels;

import utilities.TextFormat;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-17.
 */
public class ParityPanel extends JPanel {
    private ActionListener actionListener;

    public JLabel titleLabel;

    private Border grayLineBorder;
    private JPanel withParityPanel;
    private JPanel withParityDisruptedPanel;
    private JPanel parityCheckedPanel;

    private JLabel caption;
    private JLabel resultsLabel;
    private JLabel withParityLabel;
    private JLabel withParityDisruptedLabel;
    private JLabel parityCheckedLabel;

    public ParityPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents() {
        titleLabel = new JLabel();
        withParityPanel = new JPanel();
        withParityDisruptedPanel = new JPanel();
        parityCheckedPanel = new JPanel();

        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        Components.buttonList.add(Components.BUTTONS.BACKFROMPARITY.getId(), new JButton(Components.BUTTONS.BACKFROMPARITY.getName()));
        Components.buttonList.add(Components.BUTTONS.SENDTOHAM.getId(), new JButton(Components.BUTTONS.SENDTOHAM.getName()));

        caption = new JLabel("<html>1 - Dane wejściowe z bitem parzystości<br>" +
                "2 - Zakłócone dane wejściowe z bitem parzystości<br>" +
                "3 - Sprawdzenie poprawności transmisji<br>");
        withParityLabel = new JLabel("");
        withParityDisruptedLabel = new JLabel("");
        parityCheckedLabel = new JLabel("");
        resultsLabel = new JLabel("");

        withParityPanel.add(withParityLabel);
        withParityDisruptedPanel.add(withParityDisruptedLabel);
        parityCheckedPanel.add(parityCheckedLabel);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        withParityPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "1"));
        withParityDisruptedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "2"));
        parityCheckedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "3"));

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(withParityPanel, c);

        c.gridx = 1;
        panel.add(withParityDisruptedPanel, c);

        c.gridx = 2;
        panel.add(parityCheckedPanel, c);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension((int) Components.FRAME_SIZE.getWidth() - 10, 300));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(titleLabel, c);

        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridy = 1;
        add(caption, c);
        c.gridx = 1;
        add(resultsLabel, c);

        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        add(scrollPane, c);

        c.gridwidth = 2;
        c.gridy = 3;
        JButton button = Components.buttonList.get(Components.BUTTONS.BACKFROMPARITY.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.BACKFROMPARITY.getId()), c);

        c.gridy = 4;
        button = Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()), c);
    }

    public void setLabelsText(String withParity, String withParityDisrupted, ArrayList<Boolean> parityChecked, ArrayList<Integer> intNoiseWithShift) {
        StringBuilder temp = new StringBuilder("<html>");
        StringBuilder temp2 = new StringBuilder();
        for (int i = 0; i < withParity.length(); i++) {
            if (withParity.charAt(i) == '\n') {
                temp.append(TextFormat.colorTextHtml(temp2.toString(), 1, "green"));
                temp.append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(withParity.charAt(i));
        }
        withParityLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        temp2 = new StringBuilder();
        int counter = 0;
        for (int i = 0; i < withParityDisrupted.length(); i++) {
            if (withParityDisrupted.charAt(i) == '\n') {
                if (counter < intNoiseWithShift.size()) {
                    if (intNoiseWithShift.get(counter) > 0)
                        temp.append(TextFormat.colorTextHtml(temp2.toString(), intNoiseWithShift.get(counter), "red"));
                    else
                        temp.append(temp2.toString());
                    counter++;
                } else
                    temp.append(temp2.toString());
                temp.append("<br>");
                temp2 = new StringBuilder();
            } else
                temp2.append(withParityDisrupted.charAt(i));
        }
        withParityDisruptedLabel.setText(temp.toString());

        int characterDisruptedDetectedParity = 0;
        temp = new StringBuilder("<html>");
        for (int i = 0; i < parityChecked.size(); i++) {
            Boolean aParityChecked = parityChecked.get(i);
            if (aParityChecked) {
                temp.append("   <font color=green>blad transmisji</font>");
                characterDisruptedDetectedParity++;
            } else if (intNoiseWithShift.size() > i)
                if (intNoiseWithShift.get(i) > 0)
                    temp.append("   <font color=red>nie wykryto</font>");
                else
                    temp.append("   <font color=green>nie wykryto</font>");
            else
                temp.append("   <font color=green>nie wykryto</font>");
            temp.append("<br>");
        }
        parityCheckedLabel.setText(temp.toString());

        resultsLabel.setText("<html>Transmisja zakłócona, wykryta : " + characterDisruptedDetectedParity + "<br>" +
                "Transmisja zakłócona, niewykryta: " + (Components.characterDisrupted - characterDisruptedDetectedParity) + "<br>" +
                "Liczba bitów wiadomości: " + 8 * Components.intInputData.size() + "<br>" +
                "Liczba bitów nadmiarowych: " + Components.intInputData.size());
    }
}
