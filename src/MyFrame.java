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
    JPanel generateData;
    JPanel panelInputData;
    JPanel panelNoise;
    Border grayLineBorder;

    String inputData;
    String noise;

    public MyFrame(){
        super("Wykrywanie");

        addButtonToList(Components.BUTTONS.GENERATEINPUT);
        addButtonToList(Components.BUTTONS.SENDINPUT);
        addButtonToList(Components.BUTTONS.GENERATENOISE);
        addButtonToList(Components.BUTTONS.TOBINARY);

        createComponents();
        addComponents();

//        panelNoise.setVisible(false);

        frameConfiguration();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonList.get(Components.BUTTONS.GENERATEINPUT.getId()))){
            Random generator = new Random();
            String input = "";
            for(int i =0; i < 20; i++){
                input = input.concat(Character.toString((char)(Math.abs(generator.nextInt())%256)));
            }

            this.inputData = input;
            inputDataTextArea.setText(input);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.GENERATENOISE.getId()))){
            Random generator = new Random();
            String noise = "";
            for(int i =0; i < 20; i++){
                int a = ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8)) |
                        ((Math.abs(generator.nextInt())%2) << (Math.abs(generator.nextInt())%8));
//                System.out.print(a+"\n");
                noise = noise.concat(Character.toString((char)(a)));
            }
            this.noise = noise;
            noiseTextArea.setText(noise);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.TOBINARY.getId()))){
            noiseTextArea.setText(Converter.stringToBinary(noise));
            inputDataTextArea.setText(Converter.stringToBinary(inputData));

        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
//            panelNoise.setVisible(true);
//            panelInputData.setVisible(false);
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

        generateData = new JPanel(new GridBagLayout());
        panelInputData = new JPanel(new GridBagLayout());
        panelNoise = new JPanel(new GridBagLayout());
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
        panelInputData.add(scrollPaneList.get(0),c);
        panelInputData.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Dane wejściowe"));

        c.gridx = 0;
        c.gridy = 1;

        JButton button = buttonList.get(Components.BUTTONS.GENERATEINPUT.getId());
        panelInputData.add(button, c);
        button.addActionListener(this);
    }

    private void addNoiseComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        panelNoise.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "Zakłócenia"));
        panelNoise.add(scrollPaneList.get(1), c);

        c.gridy = 1;

        JButton button = buttonList.get(Components.BUTTONS.GENERATENOISE.getId());
        panelNoise.add(button,c);
        button.addActionListener(this);
    }

    private void addGenerateDataComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        generateData.add(panelInputData,c );
        c.gridx = 1;
        generateData.add(panelNoise, c);


        c.gridx = 2;
        JButton button = buttonList.get(Components.BUTTONS.TOBINARY.getId());
        generateData.add(button, c);
        button.addActionListener(this);

        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        button = buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        generateData.add(button, c);
        button.addActionListener(this);

        add(generateData);
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
