import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void advance() {
        String input = "helloelldwadawdawdawaawdcacasdwa";
        String pattern = "elldwadawdawdawaawdcacas";
        assertEquals(5, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void testPower() {
        assertEquals(27 % RollingString.PRIMEBASE, RollingString.safeModPower(3, 3));
        assertEquals((1 << 14) % RollingString.PRIMEBASE, RollingString.safeModPower(2, 14));
        assertEquals((1 << 30) % RollingString.PRIMEBASE, RollingString.safeModPower(2, 30));
    }
}
