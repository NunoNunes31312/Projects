package test.java;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.java.Printer;
import main.java.Priority;
import main.java.Job;

public class PrinterTests {

    @Test
    // simple check printer id
    public void testGetPrinterId() { 
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(10, sb);
        int expected = 10;
        int actual = printer.getPrinterId();
        assertEquals(expected, actual);
    }

    @Test
    // simple check current time
    public void testCurrentTime() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        int expected = 0;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // simple check time to finish normal
    public void testTimeToFinishNormal() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        int expected = 0;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    @Test
    // simple check time to finish high
    public void testTimeToFinishHigh() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        int expected = 0;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    // TESTS ADDJOB

    @Test
    // job taking 10, so time to finish is 10
    public void testAddJob1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        int expected = 10;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    @Test
    // wrong priority
    public void testAddJob2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        int expected = 0;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    @Test
    // wrong priority
    public void testAddJob3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        int expected = 0;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }


    @Test
    // job taking 10, so time to finish is 10
    public void testAddJob4() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        int expected = 10;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    @Test
    // two jobs
    public void testAddJob5() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        int expected = 20;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    @Test
    // two jobs, one different priority
    public void testAddJob6() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        int expected = 10;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }


    // TEST CURRENTJOB PRIORITY

    @Test
    // single job, priority is that of job
    public void testCurrentJobPriority1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        Priority expected = Priority.NORMAL;
        Priority actual = printer.currentJob().getPriority();
        assertEquals(expected, actual);

    }

    @Test
    // single job, priority is that of job
    public void testCurrentJobPriority2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        Priority expected = Priority.HIGH;
        Priority actual = printer.currentJob().getPriority();
        assertEquals(expected, actual);

    }

    @Test
    // two jobs at the same time, priority goes to high
    public void testCurrentJobPriority3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        Priority expected = Priority.HIGH;
        Priority actual = printer.currentJob().getPriority();
        assertEquals(expected, actual);

    }

    // TEST PRINTCURRENTJOB

    @Test
    // printing for 0
    public void testPrintCurrentJob1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.printCurrentJob(0);
        int expected = 0;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // printing advances time
    public void testPrintCurrentJob2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.printCurrentJob(5);
        int expected = 5;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // start printing after a delay
    public void testPrintCurrentJob3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.NORMAL));
        printer.printCurrentJob(5);
        int expected = 15;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // decreasing time to finish
    public void testPrintCurrentJob4() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        printer.printCurrentJob(5);
        int expected = 5;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    @Test
    // decreasing time to finish
    public void testPrintCurrentJob5() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.printCurrentJob(10);
        int expected = 0;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    // TEST FINISHCURRENTJOB

    @Test
    // finishing only job leaves printer empty
    public void testFinishCurrentJob1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.finishCurrentJob();
        boolean expected = true;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    // finishing only job leaves printer empty
    public void testFinishCurrentJob2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        printer.finishCurrentJob();
        boolean expected = true;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    // finishing one of two job leaves printer not empty
    public void testFinishCurrentJob3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.finishCurrentJob();
        boolean expected = false;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    // finishing both jobs leaves printer empty
    public void testFinishCurrentJob4() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        printer.finishCurrentJob();
        printer.finishCurrentJob();
        boolean expected = true;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    // TESTS ISEMPTY

    @Test
    // empty printer is empty
    public void testIsEmpty1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        boolean expected = true;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    // non empty printer is not empty
    public void testIsEmpty2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.NORMAL));
        boolean expected = false;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    @Test
    // non empty printer is not empty
    public void testIsEmpty3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.addJob(new Job("test", 10, 1, 0, Priority.HIGH));
        boolean expected = false;
        boolean actual = printer.isEmpty();
        assertEquals(expected, actual);
    }

    // TESTS PRINTFORDURATION

    @Test
    // printing for 0 doesn't advance time
    public void testPrintForDuration1() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(0);
        int expected = 0;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // printing advances time
    public void testPrintForDuration2() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        int expected = 10;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // printing two times is the same as one x2
    public void testPrintForDuration3() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.printForDuration(10);
        int expected = 20;
        int actual = printer.currentTime();
        assertEquals(expected, actual);
    }

    @Test
    // printing updates time left
    public void testPrintForDuration4() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.NORMAL));
        printer.printForDuration(5);
        printer.addJob(new Job("test", 10, 1, 15, Priority.NORMAL));
        int expected = 15;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    @Test
    // printing updates time left
    public void testPrintForDuration5() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.HIGH));
        printer.printForDuration(5);
        printer.addJob(new Job("test", 10, 1, 15, Priority.HIGH));
        int expected = 15;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    @Test
    // printing updates time left
    public void testPrintForDuration6() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.NORMAL));
        printer.addJob(new Job("test", 20, 1, 10, Priority.HIGH));
        printer.printForDuration(5);
        int expected = 10;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

    @Test
    // printing updates time left
    public void testPrintForDuration7() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.NORMAL));
        printer.addJob(new Job("test", 20, 1, 10, Priority.HIGH));
        printer.printForDuration(5);
        int expected = 15;
        int actual = printer.timeToFinishHigh();
        assertEquals(expected, actual);
    }

    @Test
    // printing updates time left
    public void testPrintForDuration8() {
        StringBuilder sb = new StringBuilder();
        Printer printer = new Printer(0, sb);
        printer.printForDuration(10);
        printer.addJob(new Job("test", 10, 1, 10, Priority.NORMAL));
        printer.addJob(new Job("test", 20, 1, 10, Priority.HIGH));
        printer.printForDuration(25);
        int expected = 5;
        int actual = printer.timeToFinishNormal();
        assertEquals(expected, actual);
    }

}
