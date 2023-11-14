import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PrimeImplicantChartTest {

    @Test
    public void testBuildGrid() {
        //extract Essential PrimeImplicants
        List<Minterm> list = new ArrayList<>();
        Minterm m0 = new Minterm(1, -1, 1);
        list.add(m0);
        Minterm m1 = new Minterm(1, 1, -1);
        list.add(m1);
        PrimeImplicantChart pmc = new PrimeImplicantChart(new int[]{5, 6, 7}, list);

        List<Minterm> essential = pmc.extractEssentialPrimeImplicants();
        System.out.println("essential: " + essential);
        assertEquals(2, essential.size());

        //Not other

        List<Minterm> implicants = pmc.extractRemainingImplicants();
        System.out.println("implicants: " + implicants);
        assertEquals(0, implicants.size());
    }

    @Test
    public void test2() {
        // extract Essential And Other Prime Implicants

        List<Minterm> list = new ArrayList<>();
        Minterm m0 = new Minterm(-1,0,0);
        list.add(m0);
        Minterm m1= new Minterm(-1,1,1);
        list.add(m1);
        Minterm m2= new Minterm(1,0,-1);
        list.add(m2);
        Minterm m3= new Minterm(1,-1,1);
        list.add(m3);

        PrimeImplicantChart pmc = new PrimeImplicantChart(new int[]{0,3,4,5,7},list);

        List<Minterm> essential = pmc.extractEssentialPrimeImplicants();
        System.out.println("essential: " + essential);
        List<Minterm> implicants = pmc.extractRemainingImplicants();
        System.out.println("implicants: " + implicants);

        assertEquals(2,essential.size());
        assertEquals(1,implicants.size());
        assertTrue(list.containsAll(implicants));
    }

}
