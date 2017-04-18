package panels;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

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

    private Border grayLineBorder;
    private JPanel withParityPanel;
    private JPanel withParityDisturbedPanel;

    private JLabel withParityLabel;
    private JLabel withParityDisturbedLabel;

    private JScrollPane withParityScrollPane;
    private JScrollPane withParityDisturbedScrollPane;

    public ParityPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents(){
        withParityPanel = new JPanel();
        withParityDisturbedPanel = new JPanel();
        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);


        Components.buttonList.add(Components.BUTTONS.BACK.getId(),new JButton(Components.BUTTONS.BACK.getName()));

        withParityLabel = new JLabel("");
        withParityScrollPane = new JScrollPane(new JPanel().add(withParityLabel));
        withParityScrollPane.setPreferredSize(new Dimension(250,350));

        withParityDisturbedLabel = new JLabel("");
        withParityDisturbedScrollPane = new JScrollPane(new JPanel().add(withParityDisturbedLabel));
        withParityDisturbedScrollPane.setPreferredSize(new Dimension(250,350));


        withParityPanel.add(withParityScrollPane);
        withParityDisturbedPanel.add(withParityDisturbedScrollPane);
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        withParityPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Dane wejściowe z bitem parzystości"));
        withParityDisturbedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Zakłócone dane wejściowe z bitem parzystości"));
        c.gridwidth = c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(withParityPanel,c);
        c.gridx = 1;
        add(withParityDisturbedPanel,c);
        c.gridx = 0;
        c.gridy = 1;
        JButton button = Components.buttonList.get(Components.BUTTONS.BACK.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.BACK.getId()),c);
    }

    public void colorText(String withParity, String withParityDisturbed, String noise, ArrayList<Boolean> parityChecked){
        StringBuilder temp = new StringBuilder("<html>");
        int counter = 0, counterParityChecked = 0;
        for(int i=0; i < withParity.length(); i++){
            if(withParity.charAt(i)=='\n'){
                temp.append("<br>");
            }else if(counter ==10){
                temp.append("<font color=green>").append(withParity.charAt(i)).append("</font>");
                counter =0;
            }else{
                temp.append(withParity.charAt(i));
                counter++;
            }
        }
        withParityLabel.setText(temp.toString());

        temp = new StringBuilder("<html>");
        for (int i = 0; i < withParityDisturbed.length(); i++) {
            if (withParityDisturbed.charAt(i) == '\n') {
                if (parityChecked.get(counterParityChecked)) {
                    temp.append("   <font color=green>blad transmisji</font>");
                    Components.characterDisturbedDetected++;
                } else
                    temp.append("   <font color=red>nie wykryto</font>");
                counterParityChecked++;
                temp.append("<br>");
            }else if (noise.length() > i) {
                if (noise.charAt(i) == '1')
                    temp.append("<font color=red>").append(withParityDisturbed.charAt(i)).append("</font>");
                else
                    temp.append(withParityDisturbed.charAt(i));
            } else
                temp.append(withParityDisturbed.charAt(i));
        }
        withParityDisturbedLabel.setText(temp.toString());
    }
}
