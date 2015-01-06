package com.wada811.notebook;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalendarActivityTest extends ActivityInstrumentationTestCase2<CalendarActivity>{

    private CalendarActivity activity;

    public CalendarActivityTest(){
        super(CalendarActivity.class);
    }

    @Before
    public void setUp() throws Exception{
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
    }

    @After
    public void tearDown() throws Exception{
        super.tearDown();
    }

    @Test
    public void checkPreconditions(){
        assertThat(activity, notNullValue());
        assertThat(getInstrumentation(), notNullValue());
    }

}
