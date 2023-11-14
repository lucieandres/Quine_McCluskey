import java.util.*;

public class QMC {
    private int[] values;

    /**
     * Initialize the algorithm
     * @param values    decimals
     */
    public QMC(int... values) {
        this.values = values;
    }

    /**
     * Calculates and returns the necessary and sufficient minterms.
     */
    public List<Minterm> computePrimeImplicants(){
        int numberOfBitsNeeded = 0;
        for (int value : values) {
            if(Minterm.numberOfBitsNeeded(value) > numberOfBitsNeeded)
                numberOfBitsNeeded = Minterm.numberOfBitsNeeded(value);
        }

        List<Minterm> minterms = new ArrayList<>();
        for (int value : values) {
            minterms.add(new Minterm(value, numberOfBitsNeeded));
        }

        CategoryManager categoryManager = new CategoryManager(minterms, numberOfBitsNeeded);
        List<Minterm> mergedMinterms = categoryManager.mergeCategories();
        while (!categoryManager.isLastTurn()) {
            categoryManager = new CategoryManager(mergedMinterms, numberOfBitsNeeded);
            mergedMinterms = categoryManager.mergeCategories();
        }

        PrimeImplicantChart primeImplicantChart = new PrimeImplicantChart(values, mergedMinterms);
        List<Minterm> primeImplicants = primeImplicantChart.extractEssentialPrimeImplicants();
        primeImplicants.addAll(primeImplicantChart.extractRemainingImplicants());

        return primeImplicants;
    }

    public int[] getValues() {
        return values;
    }
}
