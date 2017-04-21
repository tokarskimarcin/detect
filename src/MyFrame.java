import panels.Components;
import panels.GeneratePanel;
import panels.HammingPanel;
import panels.ParityPanel;
import utils.Hamming;
import utils.MyConverter;
import utils.Parity;

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
    private HammingPanel hammingPanel;

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

        hammingPanel = new HammingPanel(this);
        hammingPanel.setVisible(false);
        char a = 'a';
        System.out.println("Character: "+MyConverter.integerToBinary(a,8));
        int hammingLength = Hamming.getHammingifiedLength(8);
        System.out.println("Hamming length:" + hammingLength);
        System.out.println("Block: "+MyConverter.integerToBinary(Hamming.hammingifyBlock(a,8),12));
    }

    private void addComponents(){
        add(generatePanel);
        add(parityPanel);
        add(hammingPanel);
    }


    private void frameConfiguration(){
        setLayout(new FlowLayout());
        setSize((int)Components.FRAME_SIZE.getWidth(),(int)Components.FRAME_SIZE.getHeight());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
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
            ArrayList<Integer> withParityDisrupted = MyConverter.disturbe(withParity);

            ArrayList<Boolean> parityChecked = new ArrayList<>();

            for (int i = 0; i < withParityDisrupted.size(); i++) {
                Integer aWithParityDisrupted = withParityDisrupted.get(i);
                parityChecked.add(!Parity.isParity(aWithParityDisrupted, 0xff<<1 | 1, Components.parity));
            }

            parityPanel.setLabelsText(MyConverter.arrayIntToBinaryString(withParity,9),
                    MyConverter.arrayIntToBinaryString(withParityDisrupted,9),parityChecked );
            parityPanel.setVisible(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACKFROMPARITY.getId()))){
            parityPanel.setVisible(false);
            generatePanel.setVisible(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()))){
            parityPanel.setVisible(false);

            ArrayList<Integer> withHamming = new ArrayList<>();
            int hammingLength = Hamming.getHammingifiedLength(8);
            for(int i = 0; i < Components.inputData.length();i++ ){
                int hammingfyBlock = Hamming.hammingifyBlock(Components.inputData.charAt(i), 8);
                int hammingCtrl= Hamming.hamming(hammingfyBlock, hammingLength);
                withHamming.add(Hamming.insertControlBits(hammingfyBlock, hammingCtrl));
            }

            ArrayList<Integer> withHammingDisrupted = MyConverter.disturbe(withHamming);

            ArrayList<Integer> disruptionPosition = new ArrayList<>();
            for(int i =0; i < withHammingDisrupted.size(); i++){
                disruptionPosition.add(Hamming.hamming(withHammingDisrupted.get(i), hammingLength));
            }
            hammingPanel.setLabelsText(MyConverter.arrayIntToBinaryString(withHamming, hammingLength),
                    MyConverter.arrayIntToBinaryString(withHammingDisrupted, hammingLength),
                    disruptionPosition);

            hammingPanel.setVisible(true);
        }

        if(e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACKFROMHAMMING.getId()))){
            hammingPanel.setVisible(false);
            parityPanel.setVisible(true);
        }
    }
}
