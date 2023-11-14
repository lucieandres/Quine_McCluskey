import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class QMCTest {

    @Test
    public void test() {
        QMC qmc = new QMC(1, 2, 3, 5);
        //afficher qmc.getValues()
        System.out.println(qmc.getValues()[0]);
        System.out.println(qmc.getValues()[1]);
        System.out.println(qmc.getValues()[2]);
        System.out.println(qmc.getValues()[3]);

        List<Minterm> primaryTerms = qmc.computePrimeImplicants();
        //afficher primaryTerms
        System.out.println(primaryTerms.get(0).toString());
        System.out.println(primaryTerms.get(1).toString());
        assertEquals(2, primaryTerms.size());
        assertTrue(primaryTerms.contains(new Minterm(-1, 0, 1)));
        assertTrue(primaryTerms.contains(new Minterm(0, 1, -1)));

    }
}
