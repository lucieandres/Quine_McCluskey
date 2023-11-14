import java.util.*;
import java.util.stream.*;

public class Minterm {

    private int[] bits;
    private boolean marked;


    /**
     *
     * @param decimal   the decimal number for which we want to calculate the number of bits necessary to represent it
     * @return          the minimum number of bits needed to encode this decimal in binary.
     */
    public static int numberOfBitsNeeded(int decimal) {
        int bits = 0;
        if(decimal == 0) {
            return 1;
        }
        while (decimal > 0) {
            decimal = decimal >> 1;
            bits++;
        }
        return bits;
    }

    /*********************************************************
     * Management of the minterms structure
     ******************************************************** */


    /**
     * returns all the numbers that were used to build this minterm.
     * For example, [00*0] may have been created from 0 and 2 (* = -1)
     * @return all the numbers that were used to build this minterm.
     */
    public Collection<Integer> getCombinations() {
        Collection<Integer> combinations = new ArrayList<>();
        int numberOfBits = bits.length;
        int cpt = 0;
        for (int i = 0; i < numberOfBits; i++) {
            if (bits[i] == -1) {
                cpt++;
                int[] bits1 = Arrays.copyOf(bits, numberOfBits);
                int[] bits2 = Arrays.copyOf(bits, numberOfBits);
                bits1[i] = 0;
                bits2[i] = 1;
                Minterm minterm1 = new Minterm(bits1);
                Minterm minterm2 = new Minterm(bits2);
                if (cpt == 1) {
                    Collection<Integer> combinations1 = minterm1.getCombinations();
                    Collection<Integer> combinations2 = minterm2.getCombinations();
                    combinations.addAll(combinations1);
                    combinations.addAll(combinations2);
                }
            }
        }
        if (combinations.isEmpty()) {
            combinations.add(this.toIntValue());
        }
        return combinations;
    }


    /**
     * marks the minterm as used to build another minTerm
     */
    public void mark(){
        marked = true;
    }

    /**
     *
     * @return <code>true</code> if the minterm has been used to build another minterm, <code>false</code> otherwise.
     */
    public boolean isMarked(){
        return marked;
    }

    /*********************************************************
     * Management of the minterms contents
     ******************************************************** */
    /**
     *
     * @return return the number of 0 in the minterm
     */
    public int numberOfZero() {
        int zero = 0;
        for (int bit : bits) {
            if (bit==0) {
                zero++;
            }
        }
        return zero;
    }

    /**
     *
     * @return return the number of 1 in the minterm
     */
    public int numberOfOne() {
        int one = 0;
        for (int bit : bits) {
            if (bit==1) {
                one++;
            }
        }
        return one;
    }



    /*********************************************************
     * Equality
     ******************************************************** */

    /**
     * @param o
     * @return true if the representation in base 2 is the same. Ignore the other elements.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Minterm minterm = (Minterm) o;
        return Arrays.equals(bits, minterm.bits);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits);
    }



/* -------------------------------------------------------------------------
        Constructors
 ------------------------------------------------------------------------- */
    /**
     * Construct a minterm corresponding to the decimal passed in parameter
     * and encode it on the given number of bits.
     * The associated combination then contains <code>decimal</code>.
     * @param decimal       the decimal value representing the minterm
     * @param numberOfBits  the number of bits of encoding of the decimal
     */
    public Minterm(int decimal, int numberOfBits) {
        this.marked = false;
        this.bits = new int[numberOfBits];
        for (int i = 0; i < numberOfBits; i++) {
            if ((decimal & (1 << i)) != 0) {
                bits[numberOfBits - 1 - i] = 1;
            } else {
                bits[numberOfBits - 1 - i] = 0;
            }
        }
    }


    /**
     * Builds a minterm from its representation in binary which can contain -1.
     * This constructor does not update the associated combinations.
     * The size of the binary representation corresponds to the number of parameters (binary.length).
     * @param binary
     */
    protected Minterm(int... binary) {
        this.marked = false;
        this.bits = new int[binary.length];
        for (int i = 0; i < binary.length; i++) {
            if (binary[i] == 1) {
                bits[i] = 1;
            } else if (binary[i] == 0) {
                bits[i] = 0;
            } else {
                bits[i] = -1;
            }
        }
    }



    /**
     * Compute the string showing the binary form of the minterm.
     * For example, "101" represents the minterm corresponding to 5,
     * while "1-1" represents a minterm resulting, for example from the merge of 5 and 7 (1 -1 1)
     * @return the string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int bit : bits) {
            if (bit == 1) {
                sb.append("1");
            } else if (bit == 0) {
                sb.append("0");
            } else {
                sb.append("-");
            }
        }
        return sb.toString();
    }



/* -------------------------------------------------------------------------
        Binary <-> Decimal
 ------------------------------------------------------------------------- */

    /**
     * Calculates the integer value of the binary representation.
     * But in case one of the binary elements is -1, it returns -1.
     * This method is private because it should not be used outside this class.
     * @returns the value of the minterm calculated from its binary representation.
     */
    public int toIntValue(){
        int value = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == 1) {
                value += (1 << (bits.length - 1 - i));
            } else if (bits[i] == -1) {
                return -1;
            }
        }
        return value;
    }


   /* -------------------------------------------------------------------------
        Merge
 ------------------------------------------------------------------------- */


    /**
     * create a Minterm from the merge of two Minterms when it is posssible otherwise return null
     * Attention two minterms can only be merged if
     *  - they differ by one value at most.
     *  - they are of the same size.
     *  If a merge is possible, the returned minterm
     *  - has the same binary representation as the original minterm, but where at most one slot has been replaced by -1,
     *  - and it has, for the combinations, the merge of the combinations of both minterms <code>this</code> and <code>other</code>)
     *  - and the both mindterms  <code>this</code> and <code>other</code> are marked
     * @param other is another Minterm which we try to merge
     * @return a new Minterm when it is possible to unify, else null

     */
    public Minterm merge(Minterm other) {
        if (other == null) {
            return null;
        }
        if (this.bits.length != other.bits.length) {
            return null;
        }
        int nbDiff = 0;
        int[] merged = new int[bits.length];
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] == other.bits[i]) {
                merged[i] = bits[i];
            } else {
                merged[i] = -1;
                nbDiff++;
            }
        }
        if (nbDiff == 1) {
            this.mark();
            other.mark();
            return new Minterm(merged);
        } else {
            return null;
        }
    }

}
