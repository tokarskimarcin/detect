package utils;

/**
 * Created by Marcin on 2017-04-21.
 */
public class Hamming {

    public static int hamming(int what, int length) {
        int result = 0, neededBits;

        for (neededBits = 0; (length & neededBits) != length; neededBits = ((neededBits << 1) | 0x1)) ;
        for (int i = 1; i <= length; i++)

            if (((1 << (i - 1)) & what) > 0) {
                result = result ^ i;
            }
        return result & neededBits;
    }

    public static int insertControlBits(int what, int control) {
        int result = what;

        for (int i = 0; i < 4; i++)
            result = result | ((control >> i) & 1) << ((1 << i) - 1);
        return result;
    }

    public static int removeControlBits(int what) {
        int result = 0;
        int length = 16;
        int power = 0, shift = 0;

        for (int i = 0; i < length; i++)
            if (((1 << power) & (i + 1)) > 0) {
                length--;
                power++;
                shift++;
            } else {
                result = result | (what & (1 << i)) >> shift;

            }
        return result;
    }

    public static int hammingifyBlock(int what, int length) {
        int power = 0, result = 0;
        for (int i = 0; i < length; i++)
            if (((1 << power) & (i + 1)) > 0) {
                what = (what << 1) & (~(1 << i));
                length++;
                power++;
            } else
                result = result | (what & (1 << i));
        return result;
    }

    public static int getHammingifiedLength(int length) {
        int power = 0;
        for (int i = 0; i < length; i++)
            if (((1 << power) & (i + 1)) > 0) {
                power++;
                length++;
            }
        return length;
    }

    public static int getMask(int length) {
        int counter =0;
        int mask =0;
        for (int i = 1; i < length; i = 1 << counter){
            mask = mask | (1 << i-1);
            counter++;
        }
        return mask;
    }

}
