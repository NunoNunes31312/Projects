package test.java;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.junit.Test;

import main.java.Main;

public class MainTests {

    private static void testRun(int exec) throws FileNotFoundException {
        Main main = new Main();
        main.execute(exec);
        Scanner expectedScanner = new Scanner(new File("./src/test/resources/expected/output" + exec + ".txt")).useDelimiter("\\A");
        String expected = expectedScanner.next();
        Scanner actualScanner = new Scanner(new File("./src/test/resources/output/output" + exec + ".txt")).useDelimiter("\\A");;
        String actual = actualScanner.next();
        expectedScanner.close();
        actualScanner.close();
        assertEquals(expected, actual);
    }

    @Test
    // test if priorities are taken into consideration
    public void testRun1() throws FileNotFoundException {
        testRun(1);
    }

    @Test
    // test if jobs are distributed among printers
    public void testRun2() throws FileNotFoundException {
        testRun(2);
    }
    
    @Test
    // test if jobs catch up before a new one is added
    public void testRun3() throws FileNotFoundException {
        testRun(3);
    }
    
    @Test
    // test if jobs are finished by the correct order
    public void testRun4() throws FileNotFoundException {
        testRun(4);
    }
    
    @Test
    // test if jobs are finished by the correct order
    public void testRun5() throws FileNotFoundException {
        testRun(5);
    }

}