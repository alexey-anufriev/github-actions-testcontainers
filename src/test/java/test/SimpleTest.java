package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleTest {

    @Test
    public void simpleTest() {
        Assertions.assertEquals(2, 1+1);
    }

}
