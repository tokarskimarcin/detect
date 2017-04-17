import panels.Components;
import panels.GeneratePanel;
import panels.ParityPanel;

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
    ParityPanel parityPanel;

    public MyFrame(){
        super("Wykrywanie");

        createComponents();
        addComponents();

        frameConfiguration();
    }

    private void createComponents() {

        generatePanel = new GeneratePanel(this);
        parityPanel = new ParityPanel(this);
        parityPanel.setVisible(false);
    }

    private void addComponents(){
        add(generatePanel);
        add(parityPanel);
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
            generatePanel.generateInput();
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.GENERATENOISE.getId()))){
            generatePanel.generateNoise();
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()))){
            Components.noise = generatePanel.noiseTextArea.getText();
            Components.inputData = generatePanel.inputDataTextArea.getText();
            generatePanel.noiseTextArea.setText(Converter.stringToBinary(Components.noise));
            generatePanel.inputDataTextArea.setText(Converter.stringToBinary(Components.inputData));
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(false);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()))){
            Components.noise = Converter.binaryToString(generatePanel.noiseTextArea.getText());
            Components.inputData = Converter.binaryToString(generatePanel.inputDataTextArea.getText());
            generatePanel.noiseTextArea.setText(Components.noise);
            generatePanel.inputDataTextArea.setText(Components.inputData);
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(true);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(false);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
            generatePanel.setVisible(false);
            Converter.stringToArrayInt(Components.inputData,Components.intInputData);
            Converter.stringToArrayInt(Components.noise,Components.intNoise);
            parityPanel.setVisible(true);

        }
    }
}
