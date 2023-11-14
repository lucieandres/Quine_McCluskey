import java.util.*;

public class CategoryManager {

    private List<MintermCategory> categories;

    /**
     * CategoryManager : compute the categories from a list of minterms according to the number of 11
     * @param mintermList
     * @return
     */
    public CategoryManager(List<Minterm> mintermList, int nbBits) {
        this.categories = new ArrayList<>();
        for (int i = 0; i <= nbBits; i++) {
            MintermCategory categoryMinterms = new MintermCategory();
            for (Minterm m : mintermList) {
                if (m.numberOfOne() == i) {
                    categoryMinterms.add(m);
                }
            }
            categories.add(categoryMinterms);
        }
    }



    public int numberOfCategories(){
        return categories.size();
    }


    /**
     *
     * @param numberOfOne
     * @return the Category Of Minterms containing  numberOfOne
     */
    public MintermCategory getCategory(int numberOfOne){
        return categories.get(numberOfOne);
    }


    /**
     *  isLastTurn()
     * @return true is it's the last turn.
     */
    public boolean isLastTurn() {
        return categories.size() == 1;
    }

    /**
     * Merge the categories two by two if they have only one "one" between them.
     * The minterms are the result of the merging of the categories.
     * Be careful for a category of n "one", if the category of "n+1" has no minterms,
     *    you must recover the minterms of the category of n "one" which were not marked.
     * This is the last round if no terms could be merged.
     * @return the merged terms
     */
    public List<Minterm> mergeCategories() {
        List<Minterm> res = new ArrayList<>();
        for (int i = 0; i < categories.size() - 1; i++) {
            MintermCategory category1 = categories.get(i);
            MintermCategory category2 = categories.get(i + 1);
            List<Minterm> mergedCategory = category1.merge(category2);
            if (mergedCategory.size() > 0) {
                res.addAll(mergedCategory);
            }
        }
        return res;
    }

}
