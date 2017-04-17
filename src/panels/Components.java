package panels;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-14.
 */
public class Components {
    private static Integer buttonCounter = 0;

    public static ArrayList<JButton> buttonList = new ArrayList<>();
    public static ArrayList<JScrollPane> scrollPaneList = new ArrayList<>();
    public static JTextArea inputDataTextArea;
    public static JTextArea noiseTextArea;
    public static JPanel generateDataPanel;
    public static JPanel inputDataPanel;
    public static JPanel noisePanel;
    public static Border grayLineBorder;
    public static JSlider numberSlider;
    public static String inputData="";
    public static String noise="";

    public enum BUTTONS{
        GENERATEINPUT("GENERUJ"),
        SENDINPUT("PRZESLIJ"),
        GENERATENOISE("GENERUJ"),
        TOBINARY("BINARNIE"),
        FROMBINARY("NIEBINARNIE");

        private Integer id;
        private String name;

        BUTTONS( String name){
            id = buttonCounter;
            buttonCounter ++;
            this.name = name;
        }

        public Integer getId(){
            return id;
        }
        public String getName(){
            return name;
        }
    }


}
