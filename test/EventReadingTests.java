/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import tradingsimulation.AnyExtEvt;
import tradingsimulation.ExternalEvent;

/**
 *
 * @author Chris
 */
public class EventReadingTests {

    ExternalEvent testEE;
    Date testDate, testTime;

    public EventReadingTests() {
    }

    @Before
    public void setUp() throws ParseException {
        ArrayList<String> initEvent = new ArrayList<>(Arrays.asList("Jan 2 2017", "10:00", "Q1Q tech announce exciting developments in their smartphone range, anticipating a new model in June 2017", "Random traders buy Q1Q stock over 2 days"));

        testEE = new AnyExtEvt(initEvent);
        DateFormat df = new SimpleDateFormat("MMM d yyyy");
        testDate = df.parse("Jan 2 2017");
        DateFormat tf = new SimpleDateFormat("hh:mm");
        testTime = tf.parse("10:00");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAction() throws FileNotFoundException {
        assertTrue(testEE.getAction().equals("Random traders buy Q1Q stock over 2 days"));
    }

    @Test
    public void testGetFromTick() throws FileNotFoundException {
        assertTrue(testEE.getFromTick() == 32);
    }

    @Test
    public void testGetToTick() throws FileNotFoundException {
        assertTrue(testEE.getToTick() == 88);
    }

    @Test
    public void testGetIsBuy() throws FileNotFoundException {
        assertTrue(testEE.getIsBuys());
    }

    @Test
    public void testGetNature() throws FileNotFoundException {
        assertTrue(testEE.getNature().equals("Q1Q tech announce exciting developments in their smartphone range, anticipating a new model in June 2017"));
    }

    @Test
    public void testGetDays() throws FileNotFoundException {
        assertTrue(testEE.getNumDays() == 2);
    }

    @Test
    public void testGetDate() throws FileNotFoundException {
        assertTrue(testEE.getDate().equals(testDate));
    }

    @Test
    public void testGetTime() throws FileNotFoundException {
        assertTrue(testEE.getTime().equals(testTime));
    }
}
