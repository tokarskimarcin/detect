package utilities;

/**
 * Created by Marcin on 2017-04-21.
 */
public class TextFormat {
    public static String colorTextHtml(String text, int mask, String color){
        StringBuilder temp = new StringBuilder();
        for(int i = 0; i<text.length(); i++ ){
            if( ((1 << (text.length()- 1 - i)) & mask )> 0){
                temp.append("<font color=").append(color).append(">").append(text.charAt(i)).append("</font>");
            }else
                temp.append(text.charAt(i));
        }
        return temp.toString();
    }
}
