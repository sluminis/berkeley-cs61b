import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {    
    @Test
    public void testSmall() {
        Object o2 = new ShowDog();
        ShowDog sdx = (ShowDog) o2;
        assertEquals("ShowDog bark~~", sdx.bark());

        Dog dx = (Dog) o2;
        assertEquals("ShowDog bark~~", dx.bark());

        assertEquals("ShowDog bark~~", (((Dog) o2).bark()));

        Object o3 = (Dog) o2;
        System.out.println(o3.toString());
    }

}
