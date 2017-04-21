import panels.Components;
import panels.GeneratePanel;
import panels.ParityPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-12.
 */

public class MyFrame extends JFrame implements ActionListener{

    private GeneratePanel generatePanel;
    private ParityPanel parityPanel;

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
        setSize((int)Components.FRAME_SIZE.getWidth(),(int)Components.FRAME_SIZE.getHeight());
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
            generatePanel.noiseTextArea.setText(MyConverter.stringToBinaryString(Components.noise,8));
            generatePanel.inputDataTextArea.setText(MyConverter.stringToBinaryString(Components.inputData,8));
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(false);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()))){
            Components.noise = MyConverter.binaryToString(generatePanel.noiseTextArea.getText());
            Components.inputData = MyConverter.binaryToString(generatePanel.inputDataTextArea.getText());
            generatePanel.noiseTextArea.setText(Components.noise);
            generatePanel.inputDataTextArea.setText(Components.inputData);
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(true);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(false);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
            generatePanel.setVisible(false);
            Components.parity = generatePanel.normalParity.isSelected();
            System.out.println(Components.parity);
            if(Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).isEnabled()){
                Components.inputData = generatePanel.inputDataTextArea.getText();
                Components.noise = generatePanel.noiseTextArea.getText();
            }
            Components.intInputData = MyConverter.stringToArrayInt(Components.inputData);
            Components.intNoise = MyConverter.stringToArrayInt(Components.noise);

            ArrayList<Integer> withParity = Parity.addParityBit(Components.intInputData, Components.parity);
            ArrayList<Integer> withParityDisturbed = MyConverter.disturbe(withParity);

            ArrayList<Boolean> parityChecked = new ArrayList<>();
            for (int i = 0; i < withParityDisturbed.size(); i++) {
                Integer aWithParityDisturbed = withParityDisturbed.get(i);
                parityChecked.add(!Parity.isParity(aWithParityDisturbed, 0xffff, Components.parity));
            }

            parityPanel.colorText(MyConverter.arrayIntToBinaryString(withParity,9),
                    MyConverter.arrayIntToBinaryString(withParityDisturbed,9),
                    MyConverter.arrayIntToBinaryString(Components.intNoise, 9), parityChecked);
            parityPanel.setVisible(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACK.getId()))){
            parityPanel.setVisible(false);
            generatePanel.setVisible(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()))){
            parityPanel.setVisible(false);

        }
    }
}
