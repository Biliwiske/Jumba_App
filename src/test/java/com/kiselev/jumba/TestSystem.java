package com.kiselev.jumba;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestSystem {
    @Test
    public void FragmentOpening() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void LayoutOpening() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void ClassNotCrashing() {
        assertEquals(6, 4 + 2);
    }
    @Test
    public void TransitionSuccess() {
        assertEquals(4, 2 + 2);
    }

}