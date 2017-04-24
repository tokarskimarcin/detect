import panels.*;
import utilities.CRC;
import utilities.Hamming;
import utilities.MyConverter;
import utilities.Parity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Marcin on 2017-04-12.
 */

public class MyFrame extends JFrame implements ActionListener {

    private GeneratePanel generatePanel;
    private ParityPanel parityPanel;
    private HammingPanel hammingPanel;
    private CRCsPanel crcsPanel;

    public MyFrame() {
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

        crcsPanel = new CRCsPanel(this);
        crcsPanel.setVisible(false);
    }

    private void addComponents() {
        generatePanel.titleLabel.setText("<html><h3>GENERATOR</h3>");
        add(generatePanel);
        parityPanel.titleLabel.setText("<html><h3>KONTROLA PARZYSTOŚCI</h3>");
        add(parityPanel);
        hammingPanel.titleLabel.setText("<html><h3>KODOWANIE HAMMINGA</h3>");
        add(hammingPanel);
        crcsPanel.titleLabel.setText("<html><h3>CYKLICZNA KONTROLA REDUNDANCJI</h3>");
        add(crcsPanel);
    }


    private void frameConfiguration() {
        setLayout(new FlowLayout());
        setSize((int) Components.FRAME_SIZE.getWidth(), (int) Components.FRAME_SIZE.getHeight());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.GENERATEINPUT.getId()))) {
            generatePanel.generateInput();
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.GENERATENOISE.getId()))) {
            generatePanel.generateNoise();
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()))) {
            Components.noise = generatePanel.noiseTextArea.getText();
            Components.inputData = generatePanel.inputDataTextArea.getText();
            generatePanel.noiseTextArea.setText(MyConverter.stringToBinaryString(Components.noise, 8));
            generatePanel.inputDataTextArea.setText(MyConverter.stringToBinaryString(Components.inputData, 8));
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(false);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(true);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()))) {
            Components.noise = MyConverter.binaryToString(generatePanel.noiseTextArea.getText());
            Components.inputData = MyConverter.binaryToString(generatePanel.inputDataTextArea.getText());
            generatePanel.noiseTextArea.setText(Components.noise);
            generatePanel.inputDataTextArea.setText(Components.inputData);
            Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).setEnabled(true);
            Components.buttonList.get(Components.BUTTONS.FROMBINARY.getId()).setEnabled(false);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDINPUT.getId()))) {
            generatePanel.setVisible(false);
            Components.parity = generatePanel.normalParity.isSelected();
            System.out.println(Components.parity);
            if (Components.buttonList.get(Components.BUTTONS.TOBINARY.getId()).isEnabled()) {
                Components.inputData = generatePanel.inputDataTextArea.getText();
                Components.noise = generatePanel.noiseTextArea.getText();
            }
            Components.intInputData = MyConverter.stringToArrayInt(Components.inputData);
            Components.intNoise = MyConverter.stringToArrayInt(Components.noise);

            ArrayList<Integer> withParity = Parity.addParityBit(Components.intInputData, Components.parity);


            ArrayList<Integer> intNoiseWithShift = new ArrayList<>();
            ArrayList<Integer> withParityDisrupted = MyConverter.disrupt(withParity, 2, intNoiseWithShift);

            ArrayList<Boolean> parityChecked = new ArrayList<>();

            for (Integer aWithParityDisrupted : withParityDisrupted) {
                parityChecked.add(!Parity.isParity(aWithParityDisrupted, 0xff << 1 | 1, Components.parity));
            }

            parityPanel.setLabelsText(MyConverter.arrayIntToBinaryString(withParity, 9),
                    MyConverter.arrayIntToBinaryString(withParityDisrupted, 9), parityChecked, intNoiseWithShift);
            parityPanel.setVisible(true);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDTOHAM.getId()))) {
            parityPanel.setVisible(false);

            ArrayList<Integer> withHamming = new ArrayList<>();
            int hammingLength = Hamming.getHammingifiedLength(8);
            for (int i = 0; i < Components.inputData.length(); i++) {
                int hammingfyBlock = Hamming.hammingifyBlock(Components.inputData.charAt(i), 8);
                int hammingCtrl = Hamming.hamming(hammingfyBlock, hammingLength);
                withHamming.add(Hamming.insertControlBits(hammingfyBlock, hammingCtrl));
            }

            ArrayList<Integer> intNoiseWithShift = new ArrayList<>();
            ArrayList<Integer> withHammingDisrupted = MyConverter.disrupt(withHamming, 8-Hamming.getHammingifiedLength(8), intNoiseWithShift);

            ArrayList<Integer> disruptionPosition = new ArrayList<>();
            for (Integer aWithHammingDisrupted : withHammingDisrupted) {
                disruptionPosition.add(Hamming.hamming(aWithHammingDisrupted, hammingLength));
            }

            ArrayList<Integer> correctedDisruption = new ArrayList<>();
            ArrayList<Integer> outputDataHamming = new ArrayList<>();
            for (int i = 0; i < withHammingDisrupted.size(); i++) {
                correctedDisruption.add(withHammingDisrupted.get(i) ^ (disruptionPosition.get(i) != 0 ? 1 << disruptionPosition.get(i) - 1 : 0));
                outputDataHamming.add(Hamming.removeControlBits(correctedDisruption.get(i)));
            }

            hammingPanel.setLabelsText(withHamming,
                    MyConverter.arrayIntToBinaryString(withHammingDisrupted, hammingLength),
                    disruptionPosition, correctedDisruption, outputDataHamming, intNoiseWithShift);
            hammingPanel.setVisible(true);
        }


        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.SENDTOCRC.getId()))) {
            hammingPanel.setVisible(false);
            setLabelTexts(crcsPanel.crc12, CRC.POLYNOMIAL.CRC_12);
            setLabelTexts(crcsPanel.crc16, CRC.POLYNOMIAL.CRC_16);
            setLabelTexts(crcsPanel.crcItu, CRC.POLYNOMIAL.CRC_ITU);
            setLabelTexts(crcsPanel.atm, CRC.POLYNOMIAL.ATM);

            ArrayList<Integer> checksum = new ArrayList<>();
            checksum.add(CRC.getCrc(Components.inputData,CRC.POLYNOMIAL.CRC_12));
            checksum.add(CRC.getCrc(Components.inputData,CRC.POLYNOMIAL.CRC_16));
            checksum.add(CRC.getCrc(Components.inputData,CRC.POLYNOMIAL.CRC_ITU));
            checksum.add(CRC.getCrc(Components.inputData,CRC.POLYNOMIAL.ATM));

            ArrayList<Integer> checksumDisrupted = new ArrayList<>();

            String inputDataDisrupted = MyConverter.binaryToString(
                    MyConverter.arrayIntToBinaryString(MyConverter.disrupt(Components.intInputData), 8));
 

            checksumDisrupted.add(CRC.getCrc(inputDataDisrupted,CRC.POLYNOMIAL.CRC_12));
            checksumDisrupted.add(CRC.getCrc(inputDataDisrupted,CRC.POLYNOMIAL.CRC_16));
            checksumDisrupted.add(CRC.getCrc(inputDataDisrupted,CRC.POLYNOMIAL.CRC_ITU));
            checksumDisrupted.add(CRC.getCrc(inputDataDisrupted,CRC.POLYNOMIAL.ATM));

            ArrayList<Boolean> checksumChecked = new ArrayList<>();
            for(int i =0 ; i < checksum.size(); i++)
            checksumChecked.add(Objects.equals(checksum.get(i), checksumDisrupted.get(i)));

            crcsPanel.crcTablePanel.setLabelsTextT(checksum,checksumDisrupted,checksumChecked );
            crcsPanel.setVisible(true);
        }

        if (e.getSource().equals(crcsPanel.changeVisibilityButton)) {
            crcsPanel.changeVisibility();
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACKFROMPARITY.getId()))) {
            parityPanel.setVisible(false);
            generatePanel.setVisible(true);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACKFROMHAMMING.getId()))) {
            hammingPanel.setVisible(false);
            parityPanel.setVisible(true);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.BACKFROMCRC.getId()))) {
            crcsPanel.setVisible(false);
            hammingPanel.setVisible(true);
        }

        if (e.getSource().equals(Components.buttonList.get(Components.BUTTONS.TOGENERATOR.getId()))) {
            crcsPanel.setVisible(false);
            generatePanel.setVisible(true);
        }
    }

    private void setLabelTexts(CRCPanel panel, CRC.POLYNOMIAL p) {
        CRC.myInitCrcTab(p);
        ArrayList<Integer> withChecksum = new ArrayList<>();
        for (int i = 0; i < Components.inputData.length(); i++) {
            withChecksum.add((Components.inputData.charAt(i) << CRC.getDegreeOfPolynomial(p)) | CRC.crc_tab[Components.inputData.charAt(i)]);
        }

        ArrayList<Integer> intNoiseWithShift = new ArrayList<>();
        ArrayList<Integer> withChecksumDisrupted = MyConverter.disrupt(withChecksum, CRC.getDegreeOfPolynomial(p)+1, intNoiseWithShift);

        ArrayList<Integer> checksumChecked = new ArrayList<>();
        for (Integer aWithChecksumDisrupted : withChecksumDisrupted) {
            checksumChecked.add(CRC.countCrc(aWithChecksumDisrupted, p));
        }
        panel.setLabelsText(withChecksum, withChecksumDisrupted, checksumChecked, p, intNoiseWithShift);
    }
}
