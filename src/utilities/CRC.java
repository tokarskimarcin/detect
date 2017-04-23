package utilities;


/**
 * Created by Marcin on 2017-04-22.
 */
public class CRC {
    public enum POLYNOMIAL {
        CRC_12(0b1100000001111),
        CRC_16(0b11000000000000101),
        CRC_ITU(0b10001000000100001),
        ATM(0b100000111);

        private int binaryP;

        POLYNOMIAL(int binaryP) {
            this.binaryP = binaryP;
        }

        public int getBinaryP() {
            return binaryP;
        }
    }

    public static Integer[] crc_tab = new Integer[256];

    public static void initCrcTab(POLYNOMIAL p) {
        int c, crc;
        for (int i = 0; i < 256; i++) {
            crc = 0;
            c = i;
            for (int j = 0; j < 8; j++) {
                if (((crc ^ c) & 0x0001) > 0)
                    crc = (crc >> 1) ^ p.getBinaryP();
                else crc = crc >> 1;

                c = c >> 1;
            }
            crc_tab[i] = crc;
            System.out.println(" char: " + MyConverter.integerToBinary(i, 8) + " checksum: " +
                    MyConverter.integerToBinary(crc, getDegreeOfPolynomial(p) + 1));
        }
    }

    public static void myInitCrcTab(POLYNOMIAL p) {
        int c;
        for (int i = 0; i < 256; i++) {
            c = i << getDegreeOfPolynomial(p);

            crc_tab[i] = countCrc(c, p) & getPolynomialMask(p);
            //System.out.println("char: "+Integer.toHexString(i) + " checksum: " +MyConverter.integerToBinary(crc_tab[i], getDegreeOfPolynomial(p)) +"\n");
        }
    }

    public static int countCrc(int mc, POLYNOMIAL p){
        int c = mc;
        for (int j = 0; j < 8; j++) {
            if (c >> (7 - j + getDegreeOfPolynomial(p)) != 0) {
                //System.out.println(j+"char: \t"+ MyConverter.integerToBinary(c,8+getDegreeOfPolynomial(p)));
                c ^= p.getBinaryP() << 7-j;
                //System.out.println(j+"^: \t"+MyConverter.integerToBinary(p.getBinaryP() << 7-j, 8+getDegreeOfPolynomial(p)));
                //System.out.println(j+"=: \t"+ MyConverter.integerToBinary(c,8+getDegreeOfPolynomial(p)));
                //System.out.println("----------------------------");
            }
        }
        return c;
    }

    public static int getCrc(String data, POLYNOMIAL p) {
        int currentCrcValue = getPolynomialMask(p);
        for (int i = 0; i < data.length(); i++) {
            currentCrcValue ^= data.charAt(i) & 0xFF;
            for (int j = 0; j < 8; j++) {
                if ((currentCrcValue & 1) != 0) {
                    currentCrcValue = (currentCrcValue >>> 1) ^ p.getBinaryP();
                } else
                    currentCrcValue = currentCrcValue >>> 1;
            }
        }
        currentCrcValue = ~currentCrcValue;

        return currentCrcValue & getPolynomialMask(p);
    }


    public static int getDegreeOfPolynomial(POLYNOMIAL p) {
        int polynomial = p.getBinaryP();
        for (int i = 0; i < 20; i++) {
            if (polynomial <= 0)
                return i - 1;
            polynomial = polynomial >> 1;
        }
        return 0;
    }

    public static int getPolynomialMask(POLYNOMIAL p) {
        int mask = 0;
        for (int i = 0; i < getDegreeOfPolynomial(p); i++) {
            mask = (mask << 1) | 1;
        }
        return mask;
    }
}