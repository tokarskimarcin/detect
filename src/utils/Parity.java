package utils;

import utils.MyConverter;

import java.util.ArrayList;

/**
 * Created by Marcin on 2017-04-18.
 */
public class Parity {
    public static ArrayList<Integer> addParityBit(ArrayList<Integer> array, boolean even){
        ArrayList<Integer> withParity = new ArrayList<>();

        for(int character : array){
                if(isParity(character, 0xff, even))
                    withParity.add(character << 1);
                else
                    withParity.add((character << 1) | 1 );
        }
        return withParity;
    }

    public static boolean isParity(int character, int bits_to_check, boolean even){
        int counter = 0;
        int howManyBits = 16;
        for(int i = 0 ; i < howManyBits; i++){
            if(((1<<i) & bits_to_check )> 0){
                if(((1<<i) & character)> 0 ){
                    counter++;
                }
            }
        }

        return (counter % 2 == 0) == even ;
    }

}
