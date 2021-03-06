package utilities;

import panels.Components;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-14.
 */
public class MyConverter {
    public static String integerToBinary(int c, int how_many_bits) {
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(c));
        int length = how_many_bits - binaryString.length();
        if(length>=0)
            for (int i = 0; i < length; i++) {
                binaryString.insert(0, "0");
            }
        else{
            binaryString.delete(0,-length);
        }
        return binaryString.toString();
    }

    public static String stringToBinaryString(String str, int how_many_bits) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            result.append("0b").append(integerToBinary((int) str.charAt(i), how_many_bits)).append("\n");
        }
        return result.toString();
    }

    public static String binaryToString(String str) {
        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n' && temp.length() == 8) {
                try {
                    result.append((char) (int) (Integer.parseInt(temp.toString(), 2)));
                } catch (NumberFormatException e) {
                    System.out.print("Błąd przy konwersji \n");
                }
                temp = new StringBuilder();
            } else {
                temp.append(str.charAt(i));
                if (temp.toString().equals("0b"))
                    temp = new StringBuilder();
            }
        }
        return result.toString();
    }

    public static ArrayList<Integer> stringToArrayInt(String str) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            array.add((int) str.charAt(i));
        }
        return array;
    }

    public static String arrayIntToBinaryString(ArrayList<Integer> array, int how_many_bits) {
        StringBuilder string = new StringBuilder();
        for (Integer character : array) {
            string.append("0b").append(integerToBinary(character, how_many_bits)).append("\n");
        }
        return string.toString();
    }

    public static ArrayList<Integer> disrupt(ArrayList<Integer> array, int shiftLength, ArrayList<Integer> intNoiseShifted) {
        System.out.println("Disrupt");
        Random generator = new Random();
        ArrayList<Integer> disrupted = new ArrayList<>();
        Components.characterDisrupted = 0;
        int length = array.size() > Components.intNoise.size() ? Components.intNoise.size() : array.size();
        for (int i = 0; i < array.size(); i++) {
            if (length > i) {
                if (Components.intNoise.get(i) > 0)
                    Components.characterDisrupted++;
                int randomShift = Math.abs(generator.nextInt())%shiftLength;
                intNoiseShifted.add(Components.intNoise.get(i) << randomShift);

                System.out.println("shiftLength: "+randomShift+" intNoise: "+MyConverter.integerToBinary(Components.intNoise.get(i),8)+
                        " intShiftedNoise: "+MyConverter.integerToBinary(intNoiseShifted.get(i),randomShift+8));
                disrupted.add(array.get(i) ^ intNoiseShifted.get(i));
            }else
                disrupted.add(array.get(i));
        }
        return disrupted;
    }
    public static ArrayList<Integer> disrupt(ArrayList<Integer> array) {
        ArrayList<Integer> disrupted = new ArrayList<>();
        Components.characterDisrupted = 0;
        int length = array.size() > Components.intNoise.size() ? Components.intNoise.size() : array.size();
        for (int i = 0; i < array.size(); i++) {
            if (length > i) {
                if (Components.intNoise.get(i) > 0)
                    Components.characterDisrupted++;
                disrupted.add(array.get(i) ^ Components.intNoise.get(i));
            }else
                disrupted.add(array.get(i));
        }
        return disrupted;
    }

}
