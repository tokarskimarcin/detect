import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-18.
 */
public class Parity {
    public static ArrayList<Integer> addParityBit(ArrayList<Integer> array, int even){
        ArrayList<Integer> withParity = new ArrayList<>();

        for(int character : array){
                if((even == 1) != isParity(character, 0xffff, even))
                    withParity.add((character << 1) | 1 );
                else
                    withParity.add(character << 1);
        }
        return withParity;
    }

    public static boolean isParity(int character, int bits_to_check, int even){
        int counter = 0;
        int howManyBits = 16;
        for(int i = 0 ; i < howManyBits; i++){
            if(((1<<i) & bits_to_check )> 0){
                if(((1<<i) & character)> 0 ){
                    counter++;
                }
            }
        }

        System.out.println("String counter " + MyConverter.integerToBinary(character, howManyBits).chars().filter(num -> num == '1').count());
        System.out.println("isParity counter " + counter);

        return counter % 2 != even ;
    }

}
