import java.util.*;

public class PrimeImplicantChart {

    private final int[] values;
    private final List<Minterm> minterms;

    /**
     * Initializes the grid with the original minterms and values.
     * @param values        Initial decimal values (they are also included in the combinations of minterms).
     * @param mintermList   The list of minterms reduced by merging the categories
     */
    public PrimeImplicantChart(int [] values, List<Minterm> mintermList) {
        this.minterms = mintermList;
        this.values = values;
    }



    /**
     * extracts only the essential minterms; they correspond to the minterms that are the only ones to represent one of the initial values.
     * @return essential minterm list
     */
    public  List<Minterm> extractEssentialPrimeImplicants() {
        List<Minterm> res = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            int count = 0;
            Minterm minterm = null;
            for (int j = 0; j < minterms.size(); j++) {
                if (minterms.get(j).getCombinations().contains(values[i])) {
                    count++;
                    minterm = minterms.get(j);
                }
            }
            if (count == 1) {
                res.add(minterm);
            }
        }
        return res;
    }


    /**
     * After removing the initial values covered by the essential minterms,
     * choose a minterm for each remaining value not covered by an essential minterm.
     */
    public  List<Minterm> extractRemainingImplicants() {
        List<Minterm> remainingImplicants = new ArrayList<>();
        List<Integer> coveredValues = new ArrayList<>();
        for (Minterm m : extractEssentialPrimeImplicants()) {
            coveredValues.addAll(m.getCombinations());
        }
        for (int i = 0; i < values.length; i++) {
            if (!coveredValues.contains(values[i])) {
                for (int j = 0; j < minterms.size(); j++) {
                    if (minterms.get(j).getCombinations().contains(values[i])) {
                        remainingImplicants.add(minterms.get(j));
                        break;
                    }
                }
            }
        }
        return remainingImplicants;
    }

}