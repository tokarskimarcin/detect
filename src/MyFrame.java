import panels.Components;
import panels.GeneratePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created by Marcin on 2017-04-12.
 */

public class MyFrame extends JFrame implements ActionListener{

    GeneratePanel generatePanel;

    public MyFrame(){
        super("Wykrywanie");

        createComponents();
        addComponents();

        frameConfiguration();
    }

    private void createComponents() {
        Components.grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);

        generatePanel = new GeneratePanel(this);
    }

    private void addComponents(){
        add(generatePanel);
    }


    private void frameConfiguration(){
        setLayout(new FlowLayout());
        setVisible(true);
        setSize(750,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.GENERATEINPUT.getId()))){
            Random generator = new Random();
            String input = "";
            for(int i =0; i < Components.numberSlider.getValue(); i++){
                input = input.concat(Character.toString((char)(Math.abs(generator.nextInt())%256)));
            }

            Components.inputData = input;
            Components.inputDataTextArea.setText(input);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.GENERATENOISE.getId()))){
            Random generator = new Random();
            String noise = "";
            for(int i =0; i < Components.numberSlider.getValue(); i++){
                int a = ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8)) |
                        ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8));
                noise = noise.concat(Character.toString((char)(a)));
            }
            Components.noise = noise;
            Components.noiseTextArea.setText(noise);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()))){
            Components.noiseTextArea.setText(Converter.stringToBinary(Components.noise));
            Components.inputDataTextArea.setText(Converter.stringToBinary(Components.inputData));
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(false);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()))){
            Components.noise = Converter.binaryToString(Components.noiseTextArea.getText());
            Components.inputData = Converter.binaryToString(Components.inputDataTextArea.getText());
            Components.noiseTextArea.setText(Components.noise);
            Components.inputDataTextArea.setText(Components.inputData);
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(true);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(false);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
            generatePanel.setVisible(false);
        }
    }
}
