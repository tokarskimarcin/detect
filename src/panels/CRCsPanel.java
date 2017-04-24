package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Marcin on 2017-04-23.
 */
public class CRCsPanel extends JPanel {
    private ActionListener actionListener;

    private JTabbedPane tabbedPane;
    public JToggleButton changeVisibilityButton;
    public CRCPanel crcTablePanel;
    public JLabel titleLabel;
    public CRCPanel crc12;
    public CRCPanel crc16;
    public CRCPanel crcItu;
    public CRCPanel atm;


    public CRCsPanel(ActionListener actionListener){
        super(new GridBagLayout());
        this.actionListener = actionListener;

        createComponents();
        addCrcsTableComponents();
        addComponents();
    }

    private void createComponents(){
        titleLabel = new JLabel();

        Components.buttonList.add(Components.BUTTONS.BACKFROMCRC.getId(), new JButton(Components.BUTTONS.BACKFROMCRC.getName()));
        Components.buttonList.add(Components.BUTTONS.TOGENERATOR.getId(), new JButton(Components.BUTTONS.TOGENERATOR.getName()));

        tabbedPane = new JTabbedPane();

        crc12 = new CRCPanel(actionListener);
        crc16 = new CRCPanel(actionListener);
        crcItu = new CRCPanel(actionListener);
        atm = new CRCPanel(actionListener);
        crcTablePanel = new CRCPanel(actionListener);

        changeVisibilityButton = new JToggleButton("X");
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        add(titleLabel, c);
        c.gridx = 1;
        changeVisibilityButton.addActionListener(actionListener);
        add(changeVisibilityButton, c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;

        crc12.setName("CRC-12");
        crc16.setName("CRC-16");
        crcItu.setName("CRC-ITU");
        atm.setName("ATM");

        tabbedPane.addTab(crc12.getName(),crc12);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab(crc16.getName(),crc16);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        tabbedPane.addTab(crcItu.getName(),crcItu);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        tabbedPane.addTab(atm.getName(),atm);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_3);

        add(tabbedPane, c);
        add(crcTablePanel,c );

        c.gridy = 2;
        JButton button = Components.buttonList.get(Components.BUTTONS.BACKFROMCRC.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.BACKFROMCRC.getId()), c);

        c.gridy = 3;
        button = Components.buttonList.get(Components.BUTTONS.TOGENERATOR.getId());
        button.addActionListener(actionListener);
        add(Components.buttonList.get(Components.BUTTONS.TOGENERATOR.getId()), c);
    }

    private void addCrcsTableComponents(){
        crcTablePanel.getCaption().setText("<html>1 - Wejściowa suma kontrolna<br>" +
                "2 - Wyjściowa z suma kontrolna<br>" +
                "3 - Sprawdzenie poprawności transmisji<br>");
    }

    public void changeVisibility(){
        tabbedPane.setVisible(!changeVisibilityButton.isSelected());
        crcTablePanel.setVisible(changeVisibilityButton.isSelected());
    }

}
