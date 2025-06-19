package testes;

import types.Disk;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDisk {

    @Test
    void testConstructor1() {
        Disk mockDisk = new Disk(1);
        assertEquals(1, mockDisk.getSize());
    }

    @Test
    void testIsLarger1() {
        Disk mockDisk = new Disk(2);
        assertTrue(mockDisk.isLargerThan(new Disk(1)));
    }

    @Test
    void testIsEqual() {
        Disk mockDisk = new Disk(1);
        assertFalse(mockDisk.isLargerThan(new Disk(1)));
    }

    static Stream<Arguments> diskData() {
        return IntStream.rangeClosed(1, 26).mapToObj(size -> Arguments.of(size, (char) ('A' + size - 1)));
    }

    @ParameterizedTest
    @MethodSource("diskData")
    void testToStringValidSizes(int size, char expected) {
        Disk d = new Disk(size);
        assertEquals(expected, d.toString().charAt(0), "Failed for size = " + size);
    }

}
