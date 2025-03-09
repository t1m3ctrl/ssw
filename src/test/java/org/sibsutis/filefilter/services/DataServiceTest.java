package org.sibsutis.filefilter.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DataServiceTest {
    private DataService dataService;

    @BeforeEach
    void setUp() {
        dataService = new DataService(new ArrayList<>(), Paths.get("."), "", false, 0);
    }

    @Test
    void testClassifyInteger() {
        dataService.classify("123");
        assertEquals(1, dataService.integers.size());
        assertEquals(123, dataService.integers.getFirst());
    }

    @Test
    void testClassifyFloat() {
        dataService.classify("3.14");
        assertEquals(1, dataService.floats.size());
        assertEquals(3.14, dataService.floats.getFirst());
    }
}