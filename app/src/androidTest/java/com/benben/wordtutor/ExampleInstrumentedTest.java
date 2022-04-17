package com.benben.wordtutor;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.benben.wordtutor.utils.ImportJsonUtils;

import java.io.IOException;
import java.io.InputStream;

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

        try {
            InputStream open = appContext.getApplicationContext().getAssets().open("format_PEPXiaoXue3_1.json");
            ImportJsonUtils.importData(open,"test11",appContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
