import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-12.
 */

public class MyFrame extends JFrame implements ActionListener {

    ArrayList<JButton> buttonList = new ArrayList<>();
    ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
    JTextArea inputDataTextArea;
    JTextArea noiseTextArea;
    JPanel panelInputData;
    JPanel panelNoise;
    Border grayLineBorder;

    public MyFrame(){
        super("Wykrywanie");

        addButtonToList(Components.BUTTONS.GENERATEINPUT);
        addButtonToList(Components.BUTTONS.SENDINPUT);
        addButtonToList(Components.BUTTONS.GENERATENOISE);
        addButtonToList(Components.BUTTONS.SENDNOISE);
        addButtonToList(Components.BUTTONS.BACKTOINPUT);

        createComponents();
        addComponents();
        panelNoise.setVisible(false);

        frameConfiguration();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(buttonList.get(Components.BUTTONS.GENERATEINPUT.getId()))){
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.SENDINPUT.getId()))){
            panelNoise.setVisible(true);
            panelInputData.setVisible(false);
        }

        if(e.getSource().equals(buttonList.get(Components.BUTTONS.BACKTOINPUT.getId()))){
            panelInputData.setVisible(true);
            panelNoise.setVisible(false);
        }
    }

    private void createComponents(){
        grayLineBorder = BorderFactory.createLineBorder(Color.GRAY);
        inputDataTextArea = new JTextArea(20,20);
        inputDataTextArea.setLineWrap(true);
        scrollPaneList.add(new JScrollPane(inputDataTextArea));
        panelInputData = new JPanel();
        panelNoise = new JPanel();
    }

    private void addComponents(){
        addInputDataComponents();
        addNoiseComponents();
    }
    private void addInputDataComponents(){
        panelInputData.add(scrollPaneList.get(0));
        panelInputData.setBorder(BorderFactory.createTitledBorder(grayLineBorder,"Dane wejściowe"));

        JButton button = buttonList.get(Components.BUTTONS.GENERATEINPUT.getId());
        panelInputData.add(button);
        button.addActionListener(this);

        button = buttonList.get(Components.BUTTONS.SENDINPUT.getId());
        panelInputData.add(button);
        button.addActionListener(this);

        add(panelInputData);
    }

    private void addNoiseComponents(){
        panelNoise.setBorder(BorderFactory.createTitledBorder(grayLineBorder, "Zakłócenia"));
        noiseTextArea = new JTextArea(20,20);
        noiseTextArea.setLineWrap(true);

        JButton button = buttonList.get(Components.BUTTONS.GENERATENOISE.getId());
        panelNoise.add(button);
        button.addActionListener(this);

        button = buttonList.get(Components.BUTTONS.SENDNOISE.getId());
        panelNoise.add(button);
        button.addActionListener(this);

        button = buttonList.get(Components.BUTTONS.BACKTOINPUT.getId());
        panelNoise.add(button);
        button.addActionListener(this);

        add(panelNoise);
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
