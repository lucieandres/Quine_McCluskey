import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class MinTermTest {

    @Test
    public void test1() {
        //Test la construction d'un Minterm à partir d'un tableau via le toString
        Minterm minterm = new Minterm(1, 1, 0, 0, 1);
        assertEquals("11001", minterm.toString());
    }

    @Test
    public void test2() {
        //Test la construction d'un minterm
        //à partir d'un décimal et la taille de la représentation en base 2
        Minterm minterm1 = new Minterm(25, 5);
        assertEquals("11001", minterm1.toString());
        minterm1 = new Minterm(8, 4);
        assertEquals("1000", minterm1.toString());
        minterm1 = new Minterm(11, 4);
        assertEquals("1011", minterm1.toString());

    }

    @Test
    public void test3() {
        //Test la construction d'un minterm
        //à partir d'un décimal et vérifie la combinaison associée
        Minterm minterm2 = new Minterm(26, 5);

        System.out.println(minterm2.toString());

        Collection<Integer> combinations = minterm2.getCombinations();
        Iterator<Integer> iterator = combinations.iterator();
        while (iterator.hasNext()) {
            Integer combination = iterator.next();
            System.out.println(combination);
        }


        assertEquals("11010", minterm2.toString());
        assertTrue(minterm2.getCombinations().contains(26));

    }

    @Test
    public void test4() {
        //Test la construction d'un minterm
        //à partir d'un décimal et vérifie la combinaison associée
        Minterm minterm = new Minterm(26, 5);
        assertEquals("11010", minterm.toString());
        assertTrue(minterm.getCombinations().contains(26));
    }

    @Test
    public void test5() {
        //Test merge , combinations and marks
        Minterm minterm1 = new Minterm(5, 3);
        Minterm minterm2 = new Minterm(7, 3);
        Minterm res = minterm1.merge(minterm2);
        System.out.println("merge of 5 and 7 : " + res);
        Minterm minterm6 = new Minterm(6, 3);
        Minterm minterm4 = new Minterm(4, 3);
        Minterm resBis = minterm6.merge(minterm4);
        System.out.println("merge of 4 and 6 : " + resBis);
        Minterm resTer = res.merge(resBis);
        System.out.println("merge of 4 and 7 and 5: " + resTer);

        Collection<Integer> combinations = resTer.getCombinations();
        System.out.println("\n\n\n result :");
        Iterator<Integer> iterator = combinations.iterator();
        while (iterator.hasNext()) {
            Integer combination = iterator.next();
            System.out.println(combination);
        }

        assertEquals(4, resTer.getCombinations().size());
        assertTrue(resTer.getCombinations().contains(5));
        assertTrue(resTer.getCombinations().contains(6));
        assertTrue(resTer.getCombinations().contains(7));
        assertTrue(resTer.getCombinations().contains(4));
    }
}
