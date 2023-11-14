import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryManagerTest {

    @Test
    public void testMergeCategories(){
        //Merge categories as given in blog
        List<Minterm> list = new ArrayList<>();
        list.add(new Minterm(-1, 1, 0));
        list.add(new Minterm(1, -1, 0));
        list.add(new Minterm(0, 0, -1));
        list.add(new Minterm(-1, 0, 0));
        int nbBits = 3;
        CategoryManager manager = new CategoryManager(list, nbBits);

        System.out.println("\n\n\n result :");
        System.out.println(manager.getCategory(0));
        System.out.println(manager.getCategory(1));

        assertEquals(2,manager.getCategory(1).size());
        assertEquals(2,manager.getCategory(0).size());
        List<Minterm> res = manager.mergeCategories();
        System.out.println("\n\n\n result :");
        System.out.println(res.get(0));
        System.out.println(res.get(1));
        System.out.println(res.get(2));


        assertEquals(3,res.size());
        assertTrue(res.contains(new Minterm(1, -1, 0)));
        assertTrue(res.contains(new Minterm(-1, -1, 0)));
        assertTrue(res.contains(new Minterm(0, 0, -1)));
    }

}
