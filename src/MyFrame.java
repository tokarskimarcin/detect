import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marcin on 2017-04-12.
 */

public class MyFrame extends JFrame implements ActionListener {
    ArrayList<JButton> buttonList = new ArrayList<>();
    ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
    JTextArea inputDataTextArea;
    JTextArea noiseTextArea;
    JPanel generateDataPanel;
    JPanel inputDataPanel;
    JPanel noisePanel;
    Border grayLineBorder;
    JSlider numberSlider;

    String inputData="";
    String noise="";

    public MyFrame(){
        super("Wykrywanie");

        addButtonToList(Components.BUTTONS.GENERATEINPUT);
        addButtonToList(Components.BUTTONS.SENDINPUT);
        addButtonToList(Components.BUTTONS.GENERATENOISE);
        addButtonToList(Components.BUTTONS.TOBINARY);
        addButtonToList(Components.BUTTONS.FROMBINARY);

        createComponents();
        addComponents();

//        noisePanel.setVisible(false);

        frameConfiguration();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonList.get(Components.BUTTONS.GENERATEINPUT.getId()))){
            Random generator = new Random();
            String input = "";
            for(int i =0; i < numberSlider.getValue(); i++){
                input = input.concat(Character.toString((char)(Math.abs(generator.nextInt())%256)));
            }

            this.inputData = input;
            inputDataTextArea.setText(input);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.GENERATENOISE.getId()))){
            Random generator = new Random();
            String noise = "";
            for(int i =0; i < numberSlider.getValue(); i++){
                int a = ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8)) |
                        ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8));
                noise = noise.concat(Character.toString((char)(a)));
            }
            this.noise = noise;
            noiseTextArea.setText(noise);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.TOBINARY.getId()))){
            noiseTextArea.setText(Converter.stringToBinary(noise));
            inputDataTextArea.setText(Converter.stringToBinary(inputData));
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.FROMBINARY.getId()))){
            noise = Converter.binaryToString(noiseTextArea.getText());
            inputData = Converter.binaryToString(inputDataTextArea.getText());
            noiseTextArea.setText(noise);
            inputDataTextArea.setText(inputData);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
            generateDataPanel.setVisible(false);
        }

    }

    private void createComponents(){
        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        inputDataTextArea = new JTextArea(20,20);
        inputDataTextArea.setLineWrap(true);
        scrollPaneList.add(new JScrollPane(inputDataTextArea));

        noiseTextArea = new JTextArea(20,20);
        noiseTextArea.setLineWrap(true);
        scrollPaneList.add(new JScrollPane(noiseTextArea));

        generateDataPanel = new JPanel(new GridBagLayout());
        inputDataPanel = new JPanel(new GridBagLayout());
        noisePanel = new JPanel(new GridBagLayout());

        numberSlider = new JSlider(JSlider.HORIZONTAL,0,100, 20);
    }

    private void addComponents(){
        addInputDataComponents();
        addNoiseComponents();
        addGenerateDataComponents();
    }

    private void addInputDataComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        inputDataPanel.add(scrollPaneList.get(0),c);
        inputDataPanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Dane wejściowe"));

        c.gridx = 0;
        c.gridy = 1;

        JButton button = buttonList.get(Components.BUTTONS.GENERATEINPUT.getId());
        inputDataPanel.add(button, c);
        button.addActionListener(this);
    }

    private void addNoiseComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        noisePanel.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "Zakłócenia"));
        noisePanel.add(scrollPaneList.get(1), c);

        c.gridy = 1;

        JButton button = buttonList.get(Components.BUTTONS.GENERATENOISE.getId());
        noisePanel.add(button,c);
        button.addActionListener(this);
    }

    private void addGenerateDataComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        generateDataPanel.add(inputDataPanel,c );
        c.gridx = 1;
        generateDataPanel.add(noisePanel, c);


        JPanel converterPanel = new JPanel(new GridBagLayout());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JButton button = buttonList.get(Components.BUTTONS.TOBINARY.getId());
        converterPanel.add(button, c);
        button.addActionListener(this);

        c.gridy = 1;
        button = buttonList.get(Components.BUTTONS.FROMBINARY.getId());
        converterPanel.add(button, c);
        button.addActionListener(this);

        c.gridx= 2;
        c.gridy = 0;
        generateDataPanel.add(converterPanel, c);

        c.gridwidth = 2;
        c.gridy = 1;
        c.gridx = 0;
        numberSlider.setMajorTickSpacing(20);
        numberSlider.setPaintLabels(true);
        generateDataPanel.add(numberSlider,c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        button = buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        generateDataPanel.add(button, c);
        button.addActionListener(this);

        add(generateDataPanel);
    }

    private void addButtonToList(Components.BUTTONS button){
        buttonList.add(button.getId(), new JButton(button.getName()));
    }

    private void frameConfiguration(){
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(750,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

}
