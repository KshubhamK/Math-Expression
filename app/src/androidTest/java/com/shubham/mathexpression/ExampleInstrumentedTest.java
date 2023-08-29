package com.shubham.mathexpression;

import static com.shubham.mathexpression.utils.AppConstants.COMICS_DATE;
import static com.shubham.mathexpression.utils.AppConstants.SIMPLE_DATE;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.shubham.mathexpression", appContext.getPackageName());
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void convertDateFormat() {
        String newTime;
        SimpleDateFormat actual = new SimpleDateFormat(SIMPLE_DATE);
        SimpleDateFormat target = new SimpleDateFormat(COMICS_DATE);
        Date date;
        try {
            date = actual.parse("2022-03-03");
            newTime = target.format(date);
            System.out.println("newTime " + newTime);
        } catch (ParseException e) {
            System.out.println("exception caught exception");
            e.printStackTrace();
        }
    }
}