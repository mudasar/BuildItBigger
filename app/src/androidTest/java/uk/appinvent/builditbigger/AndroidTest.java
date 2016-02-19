package uk.appinvent.builditbigger;



import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.util.Log;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import uk.appinvent.builditbigger.tasks.JokeAsyncTask;
import uk.appinvent.jokedisplaylibrary.JokeActivity;

/**
 * Created by mudasar on 19/02/16.
 */
public class AndroidTest  extends AndroidTestCase {

    private static final String TAG = "AndroidTest";
    Context context;


    protected void setUp() throws Exception {
        super.setUp();


        context =  this.getContext().getApplicationContext();
        assertNotNull(context);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testSuccessfulFetch() {

            Log.d(TAG, "Running Successfull fetch");
            String result = null;
            JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(context, null);
            jokeAsyncTask.execute(false);
            try {
                result = jokeAsyncTask.get();
                Log.d(TAG, "result retreived " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertNotNull(result);
    }

    @Test
    public void simpleTest(){
        Log.d(TAG, "THis is an other test");
    }


}
