import java.util.*;

public class MintermCategory extends ArrayList<Minterm> {

    /**
     * It computes the list of minterms m, such that :
     * - either m results from  merging a minterm from the category "this" with a minterm from the other category ;
     * - either m belongs to the current category (this) and could not be unified with a minterm of the other category
     * @param otherCategory
     * @return  the list of merged minterms
     */
    public List<Minterm> merge(MintermCategory otherCategory) {
        List<Minterm> merged = new ArrayList<Minterm>();
        for (Minterm m : this) {
            if (!m.isMarked()) {
                boolean mergedWithOther = false;
                for (Minterm other : otherCategory) {
                    if (!other.isMarked()) {
                        Minterm mergedMinterm = m.merge(other);
                        if (mergedMinterm != null) {
                            merged.add(mergedMinterm);
                            mergedWithOther = true;
                        }
                    }
                }
                if (!mergedWithOther) {
                    merged.add(m);
                }
            }
        }
        return merged;
    }
}