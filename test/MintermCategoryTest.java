import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MintermCategoryTest {

    @Test
    public void testMerge() {
        //merge of categories
        MintermCategory m0Class = new MintermCategory();
        Minterm m0 = new Minterm(0,4);
        m0Class.add(m0);

        MintermCategory m1Class = new MintermCategory();
        Minterm m1 = new Minterm(1,4);
        m1Class.add(m1);
        Minterm m2 =new Minterm(2,4);
        m1Class.add(m2);
        Minterm m4 = new Minterm(4,4);
        m1Class.add(m4);
        Minterm m8 = new Minterm(8,4);
        m1Class.add(m8);

        List<Minterm> res = m0Class.merge(m1Class);

        assertTrue(m0.isMarked());
        System.out.println("\n\n\n result :");
        Iterator<Minterm> iterator = res.iterator();
        while (iterator.hasNext()) {
            Minterm result = iterator.next();
            System.out.println(result.toString());
        }
        assertEquals(4,res.size());
        assertTrue(res.contains(new Minterm(-1,0,0,0)));
        assertTrue(res.contains(new Minterm(0,0,0,-1)));

    }
}
