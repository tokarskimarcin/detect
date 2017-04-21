package panels;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    public static Integer characterDisrupted = 0;
    public static Integer characterDisruptedDetectedParity = 0;
    public static Integer characterDisruptedDetectedHamming = 0;
    public static Integer characterDisruptedCorrectedHamming = 0;
    public static boolean parity = true;

    public enum BUTTONS{
        GENERATEINPUT("GENERUJ"),
        SENDINPUT("PRZESLIJ"),
        GENERATENOISE("GENERUJ"),
        TOBINARY("BINARNIE"),
        FROMBINARY("TEKSTOWO"),
        BACKFROMPARITY("COFNIJ"),
        SENDTOHAM("DALEJ"),
        BACKFROMHAMMING("COFNIJ");

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
