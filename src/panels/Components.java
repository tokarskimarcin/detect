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

    public static String inputData="";
    public static String noise="";
    public static ArrayList<Integer> intInputData = new ArrayList<>();
    public static ArrayList<Integer> intNoise = new ArrayList<>();
    public static Integer characterDisturbed = 0;
    public static Integer characterDisturbedDetected = 0;

    public enum BUTTONS{
        GENERATEINPUT("GENERUJ"),
        SENDINPUT("PRZESLIJ"),
        GENERATENOISE("GENERUJ"),
        TOBINARY("BINARNIE"),
        FROMBINARY("NIEBINARNIE"),
        BACK("COFNIJ");

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
