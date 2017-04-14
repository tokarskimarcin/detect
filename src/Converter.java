/**
 * Created by Marcin on 2017-04-14.
 */
public class Converter {
    public static String charToBinary(int c){
        String binaryString = Integer.toBinaryString(c);
        int length = binaryString.length();
        for(int i = 0; i<8 - length; i++){
            binaryString = "0"+binaryString;
        }
        return binaryString;
    }

    public static String stringToBinary(String str){
        String result="";
        for(int i =0 ;i < str.length(); i++){
            result += charToBinary((int)str.charAt(i)) + "\n";
        }
        return result;
    }
}
