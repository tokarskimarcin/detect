package panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Marcin on 2017-04-15.
 */
public class GeneratePanel extends JPanel{
    private ActionListener actionListener;
    public GeneratePanel(ActionListener actionListener){
        super(new GridBagLayout());
        this.actionListener = actionListener;


        createComponents();
        addInputDataComponents();
        addNoiseComponents();
        addComponents();
    }

    private void createComponents(){
        Components.buttonList.add(Components.BUTTONS.GENERATEINPUT.getId(), new JButton(Components.BUTTONS.GENERATEINPUT.getName()));
        Components.buttonList.add(Components.BUTTONS.SENDINPUT.getId(), new JButton(Components.BUTTONS.SENDINPUT.getName()));
        Components.buttonList.add(Components.BUTTONS.GENERATENOISE.getId(), new JButton(Components.BUTTONS.GENERATENOISE.getName()));
        Components.buttonList.add(Components.BUTTONS.TOBINARY.getId(), new JButton(Components.BUTTONS.TOBINARY.getName()));
        Components.buttonList.add(Components.BUTTONS.FROMBINARY.getId(), new JButton(Components.BUTTONS.FROMBINARY.getName()));

        Components.inputDataTextArea = new JTextArea(20,20);
        Components.inputDataTextArea.setLineWrap(true);
        Components.scrollPaneList.add(new JScrollPane(Components.inputDataTextArea));

        Components.noiseTextArea = new JTextArea(20,20);
        Components.noiseTextArea.setLineWrap(true);
        Components.scrollPaneList.add(new JScrollPane(Components.noiseTextArea));

        Components.generateDataPanel = new JPanel(new GridBagLayout());
        Components.inputDataPanel = new JPanel(new GridBagLayout());
        Components.noisePanel = new JPanel(new GridBagLayout());

        Components.numberSlider = new JSlider(JSlider.HORIZONTAL,0,100, 20);
    }

    private void addInputDataComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        Components.inputDataPanel.add(Components.scrollPaneList.get(0),c);
        Components.inputDataPanel.setBorder(BorderFactory.createTitledBorder(Components.grayLineBorder,"Dane wejściowe"));

        c.gridx = 0;
        c.gridy = 1;

        JButton button = Components.buttonList.get(Components.BUTTONS.GENERATEINPUT.getId());
        Components.inputDataPanel.add(button, c);
        button.addActionListener(actionListener);
    }

    private void addNoiseComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        Components.noisePanel.setBorder(BorderFactory.createTitledBorder(Components.grayLineBorder, "Zakłócenia"));
        Components.noisePanel.add(Components.scrollPaneList.get(1), c);

        c.gridy = 1;

        JButton button = Components.buttonList.get(Components.BUTTONS.GENERATENOISE.getId());
        Components.noisePanel.add(button,c);
        button.addActionListener(actionListener);
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        add(Components.inputDataPanel, c);
        c.gridx = 1;
        add(Components.noisePanel, c);


        JPanel converterPanel = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JButton button = Components.buttonList.get(Components.BUTTONS.TOBINARY.getId());
        converterPanel.add(button, c);
        button.addActionListener(actionListener);

        c.gridy = 1;
        button = Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId());
        converterPanel.add(button, c);
        button.setEnabled(false);
        button.addActionListener(actionListener);

        c.gridx= 2;
        c.gridy = 0;
        add(converterPanel, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        Components.numberSlider.setMajorTickSpacing(20);
        Components.numberSlider.setPaintLabels(true);
        add(Components.numberSlider,c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        button = Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        add(button, c);
        button.addActionListener(actionListener);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        Components.numberSlider.setMajorTickSpacing(20);
        Components.numberSlider.setPaintLabels(true);
        add(Components.numberSlider,c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        button = Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        add(button, c);
        button.addActionListener(actionListener);
    }
}