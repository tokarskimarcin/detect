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
    private JPanel parityCheckedPanel;

    private JLabel title;
    private JLabel resultsLabel;
    private JLabel withParityLabel;
    private JLabel withParityDisturbedLabel;
    private JLabel parityCheckedLabel;

    public ParityPanel(ActionListener actionListener) {
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addComponents();
    }

    private void createComponents(){
        withParityPanel = new JPanel();
        withParityDisturbedPanel = new JPanel();
        parityCheckedPanel = new JPanel();
        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        Components.buttonList.add(Components.BUTTONS.BACK.getId(),new JButton(Components.BUTTONS.BACK.getName()));
        Components.buttonList.add(Components.BUTTONS.SENDTOHAM.getId(),new JButton(Components.BUTTONS.SENDTOHAM.getName()));

        title = new JLabel("<html>1 - Dane wejściowe z bitem parzystości<br>2 - Zakłócone dane wejściowe z bitem parzystości<br>3 - Sprawdzenie parzystości");
        withParityLabel = new JLabel("");
        withParityDisturbedLabel = new JLabel("");
        parityCheckedLabel = new JLabel("");
        resultsLabel = new JLabel("");

       /* withParityPanel.add(withParityScrollPane);
        withParityDisturbedPanel.add(withParityDisturbedScrollPane);
        parityCheckedPanel.add(parityCheckedScrollPane);*/
        withParityPanel.add(withParityLabel);
        withParityDisturbedPanel.add(withParityDisturbedLabel);
        parityCheckedPanel.add(parityCheckedLabel);

    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        withParityPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"1"));
        withParityDisturbedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"2"));
        parityCheckedPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"3"));

        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(withParityPanel,c);

        c.gridx = 1;
        panel.add(withParityDisturbedPanel,c);

        c.gridx = 2;
        panel.add(parityCheckedPanel,c);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension((int)Components.FRAME_SIZE.getWidth()-10,300));

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        add(title, c);
        c.gridx = 1;
        add(resultsLabel,c);

        c.weightx = 0.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        add(scrollPane, c);

        c.gridwidth = 2;
        c.gridy = 2;
        JButton button = Components.buttonList.get(Components.BUTTONS.BACK.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.BACK.getId()),c);

        c.gridy = 3;
        button = Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()),c);
    }

    public void colorText(String withParity, String withParityDisturbed, String noise, ArrayList<Boolean> parityChecked){
        StringBuilder temp = new StringBuilder("<html>");
        int counter = 0;
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

        Components.characterDisturbedDetected=0;
        temp = new StringBuilder("<html>");
        for (int i = 0; i < parityChecked.size(); i++) {
            Boolean aParityChecked = parityChecked.get(i);
            if (aParityChecked) {
                temp.append("   <font color=green>blad transmisji</font>");
                Components.characterDisturbedDetected++;
            } else if (Components.intNoise.get(i) > 0)
                temp.append("   <font color=red>nie wykryto</font>");
            else
                temp.append("   <font color=green>nie wykryto</font>");
            temp.append("<br>");
        }

        resultsLabel.setText("<html>KONTROLA PARZYSTOŚCI<br>Transmisja zakłócona, wykryta : "+Components.characterDisturbedDetected +"<br>"+
        "Transmisja zakłócona, nie wykryta: "+ (Components.characterDisturbed - Components.characterDisturbedDetected) );
        parityCheckedLabel.setText(temp.toString());
    }
}
