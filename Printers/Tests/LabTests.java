package test.java;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import main.java.*;

public class LabTests {

    // TEST METHODS

    @Test
    // simple one printer, one job
    public void testFirstPrinterToFinish1() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input1.txt", sb);
        Job job = new Job("", 10, 1, 0, Priority.NORMAL);
        lab.processJob(job);
        int expected = 0;
        int actual = lab.firstPrinterToFinish();
        assertEquals(expected, actual);
    }
    
    @Test
    // finishes the one with the less time
    public void testFirstPrinterToFinish2() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 20, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 10, 1, 0, Priority.NORMAL);
        lab.processJob(job1);
        lab.processJob(job2);
        int expected = 1;
        int actual = lab.firstPrinterToFinish();
        assertEquals(expected, actual);
    }
    
    @Test
    // finishes the one with no job
    public void testFirstPrinterToFinish3() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 20, 1, 0, Priority.NORMAL);
        lab.processJob(job1);
        int expected = 1;
        int actual = lab.firstPrinterToFinish();
        assertEquals(expected, actual);
    }
    
    @Test
    // number of copies matter
    public void testFirstPrinterToFinish4() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 10, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 1, 1, 0, Priority.NORMAL);
        Job job3 = new Job("", 1, 10, 0, Priority.NORMAL);
        lab.processJob(job1);
        lab.processJob(job2);
        lab.processJob(job3);
        int expected = 0;
        int actual = lab.firstPrinterToFinish();
        assertEquals(expected, actual);
    }
    
    @Test
    // when tied, the lesser id wins
    public void testFirstPrinterToFinish5() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 10, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 10, 1, 0, Priority.NORMAL);
        lab.processJob(job1);
        lab.processJob(job2);
        int expected = 0;
        int actual = lab.firstPrinterToFinish();
        assertEquals(expected, actual);
    }
    
    @Test
    // high priority first
    public void testFirstPrinterToFinishHigh1() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 5, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 10, 1, 0, Priority.HIGH);
        lab.processJob(job1);
        lab.processJob(job2);
        int expected = 0;
        int actual = lab.firstPrinterToFinishHigh();
        assertEquals(expected, actual);
    }
    
    @Test
    // normal don't matter
    public void testFirstPrinterToFinishHigh2() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 5, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 10, 1, 0, Priority.HIGH);
        Job job3 = new Job("", 9, 1, 0, Priority.HIGH);
        lab.processJob(job1);
        lab.processJob(job2);
        lab.processJob(job3);
        int expected = 0;
        int actual = lab.firstPrinterToFinishHigh();
        assertEquals(expected, actual);
    }
    
    @Test
    // well distributed
    public void testFirstPrinterToFinishHigh3() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 10, 1, 0, Priority.HIGH);
        Job job2 = new Job("", 10, 1, 0, Priority.HIGH);
        Job job3 = new Job("", 1, 1, 0, Priority.NORMAL);
        Job job4 = new Job("", 10, 1, 0, Priority.HIGH);
        lab.processJob(job1);
        lab.processJob(job2);
        lab.processJob(job3);
        lab.processJob(job4);
        int expected = 0;
        int actual = lab.firstPrinterToFinishHigh();
        assertEquals(expected, actual);
    }
    
    @Test
    // one printer, one job
    public void testFirstPrinterToFinishJob1() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input1.txt", sb);
        Job job = new Job("", 10, 1, 0, Priority.NORMAL);
        lab.processJob(job);
        int expected = 0;
        int actual = lab.firstPrinterToFinishJob();
        assertEquals(expected, actual);
    }
    
    @Test
    // two printers, the one with the smaller job finishes first
    public void testFirstPrinterToFinishJob2() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 10, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 1, 1, 0, Priority.NORMAL);
        lab.processJob(job1);
        lab.processJob(job2);
        int expected = 1;
        int actual = lab.firstPrinterToFinishJob();
        assertEquals(expected, actual);
    }
    
    @Test
    // priority matters, even if the first job allocated was bigger
    public void testFirstPrinterToFinishJob3() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input2.txt", sb);
        Job job1 = new Job("", 10, 1, 0, Priority.NORMAL);
        Job job2 = new Job("", 6, 1, 0, Priority.NORMAL);
        Job job3 = new Job("", 6, 1, 0, Priority.NORMAL);
        Job job4 = new Job("", 2, 1, 0, Priority.HIGH);
        lab.processJob(job1);
        lab.processJob(job2);
        lab.processJob(job3);
        lab.processJob(job4);
        int expected = 0;
        int actual = lab.firstPrinterToFinishJob();
        assertEquals(expected, actual);
    }

    @Test
    // simple empty printers
    public void testIsPrintingFinished1() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        Lab lab = new Lab("./src/test/resources/input/printers/input1.txt", sb);
        boolean expected = true;
        boolean actual = lab.isPrintingFinished();
        assertEquals(expected, actual);
    }

}
