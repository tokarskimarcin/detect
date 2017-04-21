package panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Marcin on 2017-04-15.
 */
public class GeneratePanel extends JPanel{

    public JTextArea inputDataTextArea;
    public JTextArea noiseTextArea;
    public JRadioButton normalParity;
    public JRadioButton negativeParity;

    private ButtonGroup group;

    private JScrollPane inputScrollPane;
    private JScrollPane noiseScrollPane;
    private JPanel inputDataPanel;
    private JPanel noisePanel;
    private Border grayLineBorder;
    private JSlider numberSlider;
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

        inputDataTextArea = new JTextArea(20,20);
        inputDataTextArea.setLineWrap(true);
        inputScrollPane = new JScrollPane(inputDataTextArea);

        noiseTextArea = new JTextArea(20,20);
        noiseTextArea.setLineWrap(true);
        noiseScrollPane = new JScrollPane(noiseTextArea);

        inputDataPanel = new JPanel(new GridBagLayout());
        noisePanel = new JPanel(new GridBagLayout());

        numberSlider = new JSlider(JSlider.HORIZONTAL,0,100, 20);
        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        normalParity = new JRadioButton("normalna", true);
        negativeParity = new JRadioButton("negatywna");
        group = new ButtonGroup();
        group.add(normalParity);
        group.add(negativeParity);
    }

    private void addInputDataComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        inputDataPanel.add(inputScrollPane,c);
        inputDataPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Dane wejściowe"));

        c.gridx = 0;
        c.gridy = 1;

        JButton button = Components.buttonList.get(Components.BUTTONS.GENERATEINPUT.getId());
        inputDataPanel.add(button, c);
        button.addActionListener(actionListener);
    }

    private void addNoiseComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        noisePanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "Zakłócenia"));
        noisePanel.add(noiseScrollPane, c);

        c.gridy = 1;

        JButton button = Components.buttonList.get(Components.BUTTONS.GENERATENOISE.getId());
        noisePanel.add(button,c);
        button.addActionListener(actionListener);
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        add(inputDataPanel, c);
        c.gridx = 1;
        add(noisePanel, c);


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

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "Kontrola parzystości"));
        c.gridy = 0;
        panel.add(normalParity,c);
        c.gridy = 1;
        panel.add(negativeParity,c);

        c.gridy = 2;
        converterPanel.add(panel,c);

        button.setEnabled(false);
        button.addActionListener(actionListener);

        c.gridx= 2;
        c.gridy = 0;
        add(converterPanel, c);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        numberSlider.setMajorTickSpacing(5);
        numberSlider.setPaintLabels(true);
        add(numberSlider,c);


        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        button = Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        add(button, c);
        button.addActionListener(actionListener);
    }

    public void generateInput(){
        Random generator = new Random();
        String input = "";
        for(int i =0; i < numberSlider.getValue(); i++){
            input = input.concat(Character.toString((char)(Math.abs(generator.nextInt())%256)));
        }

        Components.inputData = input;
        inputDataTextArea.setText(input);
    }

    public void generateNoise(){
        Random generator = new Random();
        String noise = "";
        for(int i =0; i < numberSlider.getValue(); i++){
            int a = ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8)) |
                    ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8));
            noise = noise.concat(Character.toString((char)(a)));
        }
        Components.noise = noise;
        noiseTextArea.setText(noise);
    }
}
