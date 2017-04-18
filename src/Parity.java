import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-18.
 */
public class Parity {
    public static ArrayList<Integer> addParityBit(ArrayList<Integer> array, int even){
        ArrayList<Integer> withParity = new ArrayList<>();

        for(Integer character : array){
            if(even == 1 )
                if(!isParity(character, 0xff, even))
                    withParity.add((character << 1) | 1 );
                else
                    withParity.add(character << 1);
        }
        return withParity;
    }

    public static boolean isParity(int character,int bits_to_check, int even){
        int parity = 1;
        for(int i = 0 ; i < 16; i++){
            if(((1<<i) & bits_to_check )> 0){
                if(((1<<i) & character)> 0 ){
                    if(parity == 0)
                        parity = 1;
                    else
                        parity = 0;
                }
            }
        }
        return even == parity ;
    }

}
