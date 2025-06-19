package testes;

import types.Disk;
import types.Tower;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestTower {

    private static final String EOL = System.lineSeparator();

    private Disk smallDisk = new Disk(1);
    private Disk mediumDisk = new Disk(3);
    private Disk largeDisk = new Disk(5);

    @Test
    void test1() {
        Tower tower = new Tower();
        assertTrue(tower.isEmpty());
    }

    @Test
    void test2() {
        Tower tower = new Tower();
        tower.addToTower(mediumDisk);
        tower.addToTower(smallDisk);
        assertEquals(2, tower.numberOfDisks());
    }

    @Test
    void testDiskOrder() {
        Tower tower = new Tower();
        tower.addToTower(mediumDisk);
        tower.addToTower(smallDisk);
        assertEquals(smallDisk, tower.viewTopDisk());
    }

    @Test
    void testRemoveSingleDisk() {
        Tower tower = new Tower();
        tower.addToTower(mediumDisk);
        tower.removeFromTower();
        assertTrue(tower.isEmpty());
    }

    @Test
    void testRemoveMultipleDisks() {
        Tower tower = new Tower();
        tower.addToTower(mediumDisk);
        tower.addToTower(smallDisk);
        tower.removeFromTower();
        assertEquals(1,tower.numberOfDisks());
    }

    @Test
    void testIsValidOnEmptyTower1() {
        Tower tower = new Tower();
        assertTrue(tower.isValidMove(mediumDisk));
    }

    @Test
    void testIsValidWithSmallerDiskOnTop1() {
        Tower tower = new Tower();
        tower.addToTower(largeDisk);
        assertTrue(tower.isValidMove(smallDisk));
    }

    @Test
    void testViewTopDiskWithOneDisk() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        assertEquals(smallDisk, tower.viewTopDisk());
    }

    @Test
    void testViewTopDiskDoesNotRemoveDisk() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        Disk topBefore = tower.viewTopDisk();
        Disk topAfter = tower.viewTopDisk();
        assertEquals(topBefore, topAfter);
    }

    @Test
    void testViewTopDiskDoesNotChangeHeight() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        tower.viewTopDisk();
        assertEquals(8, tower.height());
    }

    @Test
    void testIsEmptyAfterAddingDisk() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        assertFalse(tower.isEmpty());
    }

    @Test
    void testIsEmptyAfterRemovingAllDisks() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        tower.removeFromTower();
        assertTrue(tower.isEmpty());
    }

    @Test
    void testToStringOnEmptyTower() {
        Tower tower = new Tower();
        assertEquals("|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"_", tower.toString());
    }

    @Test
    void testToStringWithSingleDisk() {
        Tower tower = new Tower();
        tower.addToTower(smallDisk);
        assertEquals("|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"A"+EOL+"_", tower.toString());
    }

    @Test
    void testToStringWithMultipleDisks() {
        Tower tower = new Tower();
        tower.addToTower(largeDisk);
        tower.addToTower(mediumDisk);
        tower.addToTower(smallDisk);
        assertEquals("|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"|"+EOL+"A" + EOL + "C" + EOL + "E" + EOL+ "_", tower.toString());
    }
}
