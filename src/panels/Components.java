package panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Marcin on 2017-04-14.
 */
public class Components {
    private static Integer buttonCounter = 0;

    public static Dimension FRAME_SIZE = new Dimension(750,500);
    public static ArrayList<JButton> buttonList = new ArrayList<>();

    public static String inputData="";
    public static String noise="";
    public static ArrayList<Integer> intInputData = new ArrayList<>();
    public static ArrayList<Integer> intNoise = new ArrayList<>();
    public static Integer characterDisturbed = 0;
    public static Integer characterDisturbedDetected = 0;
    public static boolean parity = true;

    public enum BUTTONS{
        GENERATEINPUT("GENERUJ"),
        SENDINPUT("PRZESLIJ"),
        GENERATENOISE("GENERUJ"),
        TOBINARY("BINARNIE"),
        FROMBINARY("TEKSTOWO"),
        BACK("COFNIJ"),
        SENDTOHAM("DALEJ");

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
