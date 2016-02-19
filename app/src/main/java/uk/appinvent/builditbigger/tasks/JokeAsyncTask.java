package uk.appinvent.builditbigger.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import uk.appinvent.buiditbigger.backend.myApi.MyApi;
import uk.appinvent.builditbigger.IAysncTaskCompleted;
import uk.appinvent.jokedisplaylibrary.JokeActivity;

/**
 * Created by mudasar on 18/02/16.
 */
public class JokeAsyncTask extends AsyncTask<Boolean, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private ProgressBar progressBar;


    public JokeAsyncTask(Context context, ProgressBar spinner) {
        this.context = context;
        progressBar = spinner;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(progressBar != null){
            progressBar.setVisibility(View.VISIBLE);
        }


    }



    @Override
    protected String doInBackground(Boolean... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://builditbigger-1225.appspot.com/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver



            myApiService = builder.build();
        }

        Boolean isPaid = params[0];

        try {
            return myApiService.getAJoke(isPaid).execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        Intent myIntent = new Intent(context, JokeActivity.class);
        myIntent.putExtra(JokeActivity.SHARED_MESSAGE, result);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }
}
