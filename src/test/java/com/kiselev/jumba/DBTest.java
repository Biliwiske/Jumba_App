package com.kiselev.jumba;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DBTest {
    @Test
    public void ReadFromDB() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void WriteToDB() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void WriteWrongData() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void ReadFromEmptyDB() {
        assertEquals(4, 2 + 2);
    }
}