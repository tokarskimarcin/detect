import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-14.
 */
public class Converter {
    public static String integerToBinary(int c, int how_much){
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(c));
        int length = binaryString.length();
        for(int i = 0; i<how_much - length; i++){
            binaryString.insert(0, "0");
        }
        return binaryString.toString();
    }

    public static String stringToBinary(String str){
        StringBuilder result= new StringBuilder();
        for(int i =0 ;i < str.length(); i++){
            result.append("0b").append(integerToBinary((int) str.charAt(i), 8)).append("\n");
        }

        return result.toString();
    }

    public static String binaryToString(String str){
        StringBuilder result = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i <str.length(); i++) {
            if(str.charAt(i) == '\n' && temp.length()==8) {
                result.append((char) (int) (Integer.parseInt(temp.toString(),2)));
                temp = new StringBuilder();
            }else {
                temp.append(str.charAt(i));
                if(temp.toString().equals("0b"))
                    temp = new StringBuilder();
            }
        }
        return result.toString();
    }

    public static void stringToArrayInt(String str, ArrayList<Integer> list){
        for(int i=0;i < str.length();i++){
            list.add((int)str.charAt(i));
        }
    }
}
